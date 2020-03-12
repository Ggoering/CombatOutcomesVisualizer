package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.models.Unit;
import org.springframework.stereotype.Service;

@Service
public class AttackQuantityService {
    public AttackQuantityService(){}

    Integer determineAttackQuantity(Unit attacker, Unit primary, Unit secondary) {

        Unit defender = attacker.getName().equals(primary.getName()) ? secondary : primary;
        Integer attackerActualWidth = this.determineActualWidth(attacker);
        Integer defenderActualWidth = this.determineActualWidth(defender);
        Integer modelsNotInB2B = this.determineModelsNotInBaseContact(attackerActualWidth, defender, defenderActualWidth);
        Integer supportingAttacks = this.determineSupportingAttacks(attacker, modelsNotInB2B);
        Integer frontRankAttacks = this.determineFrontRankAttacks(attacker, modelsNotInB2B);

        return supportingAttacks + frontRankAttacks;
    }

    Integer determineModelsNotInBaseContact(Integer attackerActualWidth, Unit attacker, Integer defenderActualWidth) {
        Integer difference = defenderActualWidth - attackerActualWidth;
        if(difference >= 0) {
                return 0;
            }
        return Math.abs(Integer.valueOf(Integer.valueOf((int) Math.ceil(difference / attacker.getBaseSize())))) - 2;
    }

    Integer determineSupportingAttacks(Unit attacker, Integer modelsNotInB2B) {
        if(attacker.getName().equals("mount")) {
            return 0;
        }

        Integer supportingRanks = 1;
        if (attacker.getWidth() >= 10) {
            supportingRanks++;
        }
        Integer potentialSupports = attacker.getCount() - attacker.getWidth() - supportingRanks * modelsNotInB2B;
        return potentialSupports >= supportingRanks * (attacker.getWidth() - modelsNotInB2B) ?
                supportingRanks * (attacker.getWidth() - modelsNotInB2B) : potentialSupports;
    }

    Integer determineFrontRankAttacks(Unit attacker, Integer modelsNotInB2B) {
        return attacker.getWidth() - modelsNotInB2B <= attacker.getCount() ? (attacker.getWidth()-modelsNotInB2B)*attacker.getA() : attacker.getCount() * attacker.getA();
    }

    Integer determineActualWidth(Unit unit) {
        Integer unitActualWidth = unit.getCount() >= unit.getWidth() ? unit.getWidth() * unit.getBaseSize() : unit.getCount() * unit.getBaseSize();

        return unitActualWidth;
    }

}
