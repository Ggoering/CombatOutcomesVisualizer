package com.GG.T9AgeCombat.service;

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
    private static final Logger logger = LoggerFactory.getLogger(CombatCalculationService.class);

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

        applySpecialRules(primary, isFirstRound);
        applySpecialRules(secondary, isFirstRound);
        List<Unit> unitsByInitiative = orderUnitsByInitiative(primary, secondary);

        for (Unit attacker : unitsByInitiative) {
            Unit defender = unitsByInitiative.stream().filter(d -> !d.isMount()
                    && !d.getSelection().equals(attacker.getSelection())).findFirst().orElse(null);

            if (defender != null) {
                int numberOfAttacks = attackQuantityService.determineAttackQuantity(attacker, defender);
                int numberOfHits = toHitService.rollToHit(attacker, defender, numberOfAttacks);
                int numberOfWounds = toWoundService.rollToWound(attacker, defender, numberOfHits);

                if (numberOfHits == 0 || numberOfWounds == 0) {
                    continue;
                }

                int failedSaves = armorSaveService.rollArmorSaves(attacker, defender, numberOfWounds);

                if (defender.getWardSave() != null) {
                    failedSaves = wardSaveService.rollWardSaves(defender, failedSaves);
                }

                defender.setPendingWounds(failedSaves);

                if (primary.getSelection().equals(attacker.getSelection())) {
                    primaryUnitWoundsDealt += failedSaves;
                } else {
                    secondaryUnitWoundsDealt += failedSaves;
                }

                // If the attacker is the last in the list then apply wounds
                // If the next unit has the same initiative as the attacker then do not apply wounds yet
                if (unitsByInitiative.indexOf(attacker) + 1 == unitsByInitiative.size()
                        || attacker.getInitiative() != unitsByInitiative.get(unitsByInitiative.indexOf(attacker) + 1).getInitiative()) {
                    for (Unit unit : unitsByInitiative) {
                        if (!unit.isMount()) {
                            unit.applyPendingWounds();
                        }
                    }
                }
            } else {
                logger.error("Null defender found when fighting.");
            }
        }

        Round round = combatResolutionService.calculateCombatResult(primary, secondary, primaryUnitWoundsDealt, secondaryUnitWoundsDealt, isFirstRound);
        rounds.add(round);
        brokenOrWipedOut = (round.getFlee() || round.getWipedOut());

        return fight(primary, secondary, brokenOrWipedOut, rounds);
    }

    List<Unit> orderUnitsByInitiative(Unit primary, Unit secondary) {
        List<Unit> unitList = new ArrayList<>();
        unitList.add(primary);
        unitList.add(secondary);

        if (primary.isMounted()) {
            unitList.add(Unit.builder().attacks(primary.getMountAttacks()).initiative(primary.getMountInitiative())
                    .strength(primary.getMountStrength()).offensiveWeaponSkill(primary.getMountOffensiveWeaponSkill())
                    .isMount(true).basesize(primary.getBasesize()).selection(1).modelsPerRank(primary.getModelsPerRank())
                    .modelCount(primary.getModelCount()).build());
        }

        if (secondary.isMounted()) {
            unitList.add(Unit.builder().attacks(secondary.getMountAttacks()).initiative(secondary.getMountInitiative())
                    .strength(secondary.getMountStrength()).offensiveWeaponSkill(secondary.getMountOffensiveWeaponSkill())
                    .isMount(true).basesize(secondary.getBasesize()).selection(2).modelsPerRank(secondary.getModelsPerRank())
                    .modelCount(secondary.getModelCount()).build());
        }

        return unitList.stream().sorted(Comparator.comparing(Unit::getInitiative).reversed()).collect(toList());
    }

    void applySpecialRules(Unit unit, boolean isFirstRound) {
        if (unit.getUnitSpecialRulesById() != null) {
            for (UnitSpecialRule unitSpecialRule : unit.getUnitSpecialRulesById()) {
                SpecialRule specialRule = unitSpecialRule.getSpecialRuleBySpecialRuleId();

                if (specialRuleRoutingService.routeLimitationToPredicate(specialRule.getLimitationByLimitationId().getValue(), unit, isFirstRound)) {
                    DetermineModificationPredicate.applyBonus(unit, specialRule.getModificationByModificationId().getValue(), specialRule.getValue());
                }
            }
        }
    }
}
