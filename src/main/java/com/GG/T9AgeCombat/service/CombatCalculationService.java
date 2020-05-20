package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.enums.LimitationEnum;
import com.GG.T9AgeCombat.models.*;
import com.GG.T9AgeCombat.predicates.DetermineModificationPredicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CombatCalculationService {
    private final AttackQuantityService attackQuantityService;
    private final ToHitService toHitService;
    private final ToWoundService toWoundService;
    private final ArmorSaveService armorSaveService;
    private final WardSaveService wardSaveService;
    private final CombatResolutionService combatResolutionService;
    private final SpecialRuleRoutingService specialRuleRoutingService;

    public CombatCalculationService(AttackQuantityService attackQuantityService, ToHitService toHitService, ToWoundService toWoundService,
                                    ArmorSaveService armorSaveService, WardSaveService wardSaveService, CombatResolutionService combatResolutionService,
                                    SpecialRuleRoutingService specialRuleRoutingService) {
        this.attackQuantityService = attackQuantityService;
        this.toHitService = toHitService;
        this.toWoundService = toWoundService;
        this.armorSaveService = armorSaveService;
        this.wardSaveService = wardSaveService;
        this.combatResolutionService = combatResolutionService;
        this.specialRuleRoutingService = specialRuleRoutingService;
    }

    Result combat(Unit primary, Unit secondary) {
        applyPermanentSpecialRules(primary);
        applyPermanentSpecialRules(secondary);

        List<Round> rounds = fight(primary, secondary, false, new ArrayList<>());
        int endingRound = rounds.size() - 1;
        String winner = rounds.get(endingRound).getWinner();

        return Result.builder().roundResults(rounds).winner(winner).endingRound(endingRound).build();
    }

    List<Round> fight(Unit primary, Unit secondary, boolean brokenOrWipedOut, List<Round> rounds) {
        if (brokenOrWipedOut) {
            return rounds;
        }

        boolean isFirstRound = rounds.isEmpty();
        int primaryUnitWoundsDealt = 0;
        int secondaryUnitWoundsDealt = 0;

        primary.resetStatModifiers();
        secondary.resetStatModifiers();

        applyTemporarySpecialRules(primary, isFirstRound);
        applyTemporarySpecialRules(secondary, isFirstRound);
        List<OffensiveProfile> offensiveProfilesByInitiative = orderUnitsByInitiative(primary, secondary);

        for (OffensiveProfile offensiveProfile : offensiveProfilesByInitiative) {
            Unit attackingUnit = (offensiveProfile.getSelection() == primary.getSelection() ? primary : secondary);
            Unit defendingUnit = (offensiveProfile.getSelection() == primary.getSelection() ? secondary : primary);

            int numberOfAttacks = attackQuantityService.determineAttackQuantity(offensiveProfile, attackingUnit, defendingUnit);
            int numberOfHits = toHitService.rollToHit(offensiveProfile, defendingUnit, numberOfAttacks);
            int numberOfWounds = toWoundService.rollToWound(offensiveProfile, defendingUnit, numberOfHits);

            if (numberOfHits == 0 || numberOfWounds == 0) {
                continue;
            }

            int failedSaves = armorSaveService.rollArmorSaves(offensiveProfile, defendingUnit, numberOfWounds);

            if (defendingUnit.getWardSave() != 0) {
                failedSaves = wardSaveService.rollWardSaves(defendingUnit, failedSaves);
            }

            defendingUnit.setPendingWounds(failedSaves);

            if (primary.getSelection() == offensiveProfile.getSelection()) {
                primaryUnitWoundsDealt += failedSaves;
            } else {
                secondaryUnitWoundsDealt += failedSaves;
            }

            // If the attacker is the last in the list then apply wounds
            // If the next unit has the same initiative as the attacker then do not apply wounds yet
            if (offensiveProfilesByInitiative.indexOf(offensiveProfile) + 1 == offensiveProfilesByInitiative.size()
                    || offensiveProfile.getActualInitiative() != offensiveProfilesByInitiative.get(offensiveProfilesByInitiative.indexOf(offensiveProfile) + 1).getActualInitiative()) {
                primary.applyPendingWounds();
                secondary.applyPendingWounds();
            }
        }

        Round round = combatResolutionService.calculateCombatResult(primary, secondary, primaryUnitWoundsDealt, secondaryUnitWoundsDealt, isFirstRound);
        rounds.add(round);
        brokenOrWipedOut = (round.getFlee() || round.getWipedOut());

        return fight(primary, secondary, brokenOrWipedOut, rounds);
    }

    List<OffensiveProfile> orderUnitsByInitiative(Unit primary, Unit secondary) {
        List<OffensiveProfile> offensiveProfileList = new ArrayList<>();
        offensiveProfileList.addAll(primary.getOffensiveProfileList());
        offensiveProfileList.addAll(secondary.getOffensiveProfileList());

        return offensiveProfileList.stream().sorted(Comparator.comparing(OffensiveProfile::getActualInitiative).reversed()).collect(toList());
    }

    void applyPermanentSpecialRules(Unit unit) {
        if (unit.getSpecialRulePropertyList() != null && !unit.getSpecialRulePropertyList().isEmpty()) {
            for (SpecialRuleProperty specialRuleProperty : unit.getSpecialRulePropertyList()) {
                if (specialRuleProperty.getLimitation() == LimitationEnum.NONE) {
                    DetermineModificationPredicate.applyPermanentBonus(unit, specialRuleProperty.getModification(), specialRuleProperty.getValue());
                }
            }

            for (OffensiveProfile offensiveProfile : unit.getOffensiveProfileList()) {
                for (SpecialRuleProperty specialRuleProperty : offensiveProfile.getSpecialRulePropertyList()) {
                    if (specialRuleProperty.getLimitation() == LimitationEnum.NONE) {
                        DetermineModificationPredicate.applyPermanentBonus(offensiveProfile, specialRuleProperty.getModification(), specialRuleProperty.getValue());
                    }
                }
            }
        }
    }

    void applyTemporarySpecialRules(Unit unit, boolean isFirstRound) {
        if (unit.getSpecialRulePropertyList() != null && !unit.getSpecialRulePropertyList().isEmpty()) {
            for (SpecialRuleProperty specialRuleProperty : unit.getSpecialRulePropertyList()) {
                if (specialRuleRoutingService.routeTemporaryLimitationToPredicate(specialRuleProperty.getLimitation(), unit, isFirstRound)) {
                    DetermineModificationPredicate.applyTemporaryBonus(unit, specialRuleProperty.getModification(), specialRuleProperty.getValue());
                }
            }

            for (OffensiveProfile offensiveProfile : unit.getOffensiveProfileList()) {
                for (SpecialRuleProperty specialRuleProperty : offensiveProfile.getSpecialRulePropertyList()) {
                    if (specialRuleRoutingService.routeTemporaryLimitationToPredicate(specialRuleProperty.getLimitation(), unit, isFirstRound)) {
                        DetermineModificationPredicate.applyTemporaryBonus(offensiveProfile, specialRuleProperty.getModification(), specialRuleProperty.getValue());
                    }
                }
            }
        }
    }
}
