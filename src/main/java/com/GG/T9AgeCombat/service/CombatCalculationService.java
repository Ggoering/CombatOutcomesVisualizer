package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.enums.LimitationEnum;
import com.GG.T9AgeCombat.enums.TimingEnum;
import com.GG.T9AgeCombat.models.*;
import com.GG.T9AgeCombat.predicates.DetermineModificationPredicate;
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
        applySpecialRules(primary, false, TimingEnum.ALL, LimitationEnum.NONE, true);
        applySpecialRules(secondary, false, TimingEnum.ALL, LimitationEnum.NONE, true);

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

        applySpecialRules(primary, isFirstRound, TimingEnum.ALL, LimitationEnum.NONE, false);
        applySpecialRules(secondary, isFirstRound, TimingEnum.ALL, LimitationEnum.NONE, false);
        List<OffensiveProfile> offensiveProfilesByAgility = orderUnitsByAgility(primary, secondary);

        for (OffensiveProfile offensiveProfile : offensiveProfilesByAgility) {
            Unit attackingUnit = (offensiveProfile.getSelection() == primary.getSelection() ? primary : secondary);
            Unit defendingUnit = (offensiveProfile.getSelection() == primary.getSelection() ? secondary : primary);

            int numberOfAttacks = attackQuantityService.determineAttackQuantity(offensiveProfile, attackingUnit, defendingUnit);
            int numberOfHits = toHitService.rollToHit(offensiveProfile, defendingUnit, numberOfAttacks);
            int numberOfWounds = toWoundService.rollToWound(offensiveProfile, defendingUnit, numberOfHits);

            if (numberOfHits == 0 || numberOfWounds == 0) {
                continue;
            }

            int failedSaves = armorSaveService.rollArmorSaves(offensiveProfile, defendingUnit, numberOfWounds);

            if (defendingUnit.getAegisSave() != 0) {
                failedSaves = wardSaveService.rollWardSaves(defendingUnit, failedSaves);
            }

            defendingUnit.setPendingWounds(failedSaves);

            if (primary.getSelection() == offensiveProfile.getSelection()) {
                primaryUnitWoundsDealt += failedSaves;
            } else {
                secondaryUnitWoundsDealt += failedSaves;
            }

            // If the attacker is the last in the list then apply wounds
            // If the next unit has the same agility as the attacker then do not apply wounds yet
            if (offensiveProfilesByAgility.indexOf(offensiveProfile) + 1 == offensiveProfilesByAgility.size()
                    || offensiveProfile.getActualAgility() != offensiveProfilesByAgility.get(offensiveProfilesByAgility.indexOf(offensiveProfile) + 1).getActualAgility()) {
                primary.applyPendingWounds();
                secondary.applyPendingWounds();
            }
        }

        Round round = combatResolutionService.calculateCombatResult(primary, secondary, primaryUnitWoundsDealt, secondaryUnitWoundsDealt, isFirstRound);
        rounds.add(round);
        brokenOrWipedOut = (round.getFlee() || round.getWipedOut());

        return fight(primary, secondary, brokenOrWipedOut, rounds);
    }

    List<OffensiveProfile> orderUnitsByAgility(Unit primary, Unit secondary) {
        List<OffensiveProfile> offensiveProfileList = new ArrayList<>();
        offensiveProfileList.addAll(primary.getOffensiveProfileList());
        offensiveProfileList.addAll(secondary.getOffensiveProfileList());

        return offensiveProfileList.stream().sorted(Comparator.comparing(OffensiveProfile::getActualAgility).reversed()).collect(toList());
    }

    public void applySpecialRules(Unit unit, boolean isFirstRound, TimingEnum timing, LimitationEnum limitation, boolean isPermanent) {
        if (unit.getSpecialRulePropertyList() != null && !unit.getSpecialRulePropertyList().isEmpty()) {
            for (SpecialRuleProperty specialRuleProperty : unit.getSpecialRulePropertyList()) {
                if (specialRuleRoutingService.checkLimitation(limitation, timing, specialRuleProperty, unit, isFirstRound)) {
                        DetermineModificationPredicate.applyBonus(unit, specialRuleProperty.getModification(), specialRuleProperty.getValue(), isPermanent);
                }
            }
        }

        if (unit.getEquipmentList() != null && !unit.getEquipmentList().isEmpty()) {
            for (Equipment equipment : unit.getEquipmentList()) {
                for (SpecialRuleProperty specialRuleProperty : equipment.getSpecialRuleProperties()) {
                    if (specialRuleRoutingService.checkLimitation(limitation, timing, specialRuleProperty, unit, isFirstRound)) {
                            DetermineModificationPredicate.applyBonus(unit, specialRuleProperty.getModification(), specialRuleProperty.getValue(), isPermanent);
                    }
                }
            }
        }

            for (OffensiveProfile offensiveProfile : unit.getOffensiveProfileList()) {
                if (unit.getSpecialRulePropertyList() != null && !unit.getSpecialRulePropertyList().isEmpty()) {
                    for (SpecialRuleProperty specialRuleProperty : offensiveProfile.getSpecialRulePropertyList()) {
                        if (specialRuleRoutingService.checkLimitation(limitation, timing, specialRuleProperty, offensiveProfile, isFirstRound, unit)) {
                            DetermineModificationPredicate.applyBonus(offensiveProfile, specialRuleProperty.getModification(), specialRuleProperty.getValue(), unit, isPermanent);
                        }
                    }
                }
                if (offensiveProfile.getEquipmentList() != null && !offensiveProfile.getEquipmentList().isEmpty()) {
                    for (Equipment equipment : offensiveProfile.getEquipmentList()) {
                        for (SpecialRuleProperty specialRuleProperty : equipment.getSpecialRuleProperties()) {
                            if (specialRuleRoutingService.checkLimitation(limitation, timing, specialRuleProperty, offensiveProfile, isFirstRound, unit)) {
                                DetermineModificationPredicate.applyBonus(offensiveProfile, specialRuleProperty.getModification(), specialRuleProperty.getValue(), unit, isPermanent);
                            }
                        }
                    }
                }
        }
    }
}
