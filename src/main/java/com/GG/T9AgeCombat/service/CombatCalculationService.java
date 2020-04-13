package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.enums.Identification;
import com.GG.T9AgeCombat.models.Result;
import com.GG.T9AgeCombat.models.Round;
import com.GG.T9AgeCombat.models.Unit;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CombatCalculationService {
    AttackQuantityService attackQuantityService;
    ToHitService toHitService;
    ToWoundService toWoundService;
    ArmorSaveService armorSaveService;
    CombatResolutionService combatResolutionService;

    public CombatCalculationService(AttackQuantityService attackQuantityService, ToHitService toHitService, ToWoundService toWoundService,
            ArmorSaveService armorSaveService, CombatResolutionService combatResolutionService) {
        this.attackQuantityService = attackQuantityService;
        this.toHitService = toHitService;
        this.toWoundService = toWoundService;
        this.armorSaveService = armorSaveService;
        this.combatResolutionService = combatResolutionService;
    }

    Result combat(Unit primary, Unit secondary) {
        List<Round> rounds = this.fight(primary, secondary, false, new ArrayList<>());
        int endingRound = rounds.size() - 1;
        Identification winner = rounds.get(endingRound).getWinner();

        return Result.builder().roundResults(rounds).winner(winner).endingRound(endingRound).build();
    }

    List<Round> fight(Unit primary, Unit secondary, boolean brokenOrWipedOut, List<Round> rounds) {
        if (brokenOrWipedOut) {
            return rounds;
        }

        Unit primaryCopy = copyUnit(primary);
        Unit secondaryCopy = copyUnit(secondary);

        Integer primaryWoundsDealt = 0;
        Integer secondaryWoundsDealt = 0;
        List<Unit> attackOrder = this.orderAttackers(primaryCopy, secondaryCopy);

        for (int i = 0; i < attackOrder.size(); i++) {
            Unit attacker = attackOrder.get(i);
            Unit defender = attackOrder.stream().filter(d -> !d.getName().equals(Identification.MOUNT) && !d.getName().equals(attacker.getName())).findFirst().orElse(null);

            if (defender != null) {
                Integer attackQuantity = attackQuantityService.determineAttackQuantity(attacker, defender);
                Integer successfulToHitRolls = toHitService.rollToHit(attacker, defender, attackQuantity);
                Integer successfulToWoundRolls = toWoundService.rollToWound(attacker, defender, successfulToHitRolls);

                if (successfulToHitRolls == 0 || successfulToWoundRolls == 0) {
                    continue;
                }

                Integer failedArmorSaves = armorSaveService.rollArmorSaves(attacker, defender, successfulToWoundRolls);
                defender.updateCount(failedArmorSaves);

                if (primary.getSelection().equals(attacker.getSelection())) {
                    primaryWoundsDealt = failedArmorSaves + primaryWoundsDealt;
                } else {
                    secondaryWoundsDealt = failedArmorSaves + secondaryWoundsDealt;
                }
            } else {
                System.out.println("Null defender found when fighting.");
            }
        }

        boolean isFirstRound = rounds.isEmpty();
        Round round = combatResolutionService.determineResult(primary, secondary, primaryWoundsDealt, secondaryWoundsDealt, isFirstRound);
        rounds.add(round);
        brokenOrWipedOut = (round.getFlee() || round.getWipedOut());
        primary.updateCount(secondaryWoundsDealt);
        secondary.updateCount(primaryWoundsDealt);

        return this.fight(primary, secondary, brokenOrWipedOut, rounds);
    }

    List<Unit> orderAttackers(Unit primary, Unit secondary) {
        List<Unit> unitList = new ArrayList<>();
        unitList.add(primary);
        unitList.add(secondary);

        if (primary.getMountAttacks() != null) {
            unitList.add(Unit.builder().attacks(primary.getMountAttacks()).initiative(primary.getMountInitiative()).strength(primary.getMountStrength()).wardSave(primary.getMountWeaponSkill()).name(Identification.MOUNT).baseSize(primary.getBaseSize()).selection(1).build());
        }

        if (secondary.getMountAttacks() != null) {
            unitList.add(Unit.builder().attacks(secondary.getMountAttacks()).initiative(secondary.getMountInitiative()).strength(secondary.getMountStrength()).wardSave(secondary.getMountWeaponSkill()).name(Identification.MOUNT).baseSize(secondary.getBaseSize()).selection(2).build());
        }

        return unitList.stream().sorted(Comparator.comparing(Unit::getInitiative).reversed()).collect(toList());
    }

    Unit copyUnit(Unit unit) {
        return Unit.builder().modelCount(unit.getModelCount()).
                toughness(unit.getToughness()).
                attacks(unit.getAttacks()).
                armorSave(unit.getArmorSave()).
                baseSize(unit.getBaseSize()).
                defensiveWeaponSkill(unit.getDefensiveWeaponSkill()).
                initiative(unit.getInitiative()).
                leadership(unit.getLeadership()).
                movement(unit.getMovement()).
                mountAttacks(unit.getMountAttacks()).
                mountInitiative(unit.getMountInitiative()).
                mountMovement(unit.getMountMovement()).
                mountStrength(unit.getMountStrength()).
                mountWounds(unit.getMountWounds()).
                mountWeaponSkill(unit.getMountWeaponSkill()).
                musician(unit.getMusician()).
                offensiveWeaponSkill(unit.getOffensiveWeaponSkill()).
                strength(unit.getStrength()).
                selection(unit.getSelection()).
                standardBearer(unit.getStandardBearer()).
                wounds(unit.getWounds()).
                width(unit.getWidth()).
                wardSave(unit.getWardSave()).
                equipmentList(unit.getEquipmentList()).
                name(unit.getName()).
                specialRulesList(unit.getSpecialRulesList()).
                build();
    }
}
