package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.models.Unit;
import org.springframework.stereotype.Service;

@Service
public class AttackQuantityService {
    public AttackQuantityService(){}

    Integer determineAttackQuantity(Unit attacker, Unit defender) {
        Integer attackerActualWidth = this.determineActualWidth(attacker);
        Integer defenderActualWidth = this.determineActualWidth(defender);
        Integer modelsNotInB2B = this.determineModelsNotInBaseContact(attackerActualWidth, attacker, defenderActualWidth);
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
        Integer count = attacker.getCount();
        Integer width = attacker.getWidth();
        if(attacker.getName().equals("mount") || (count - width) <= 0) {
            return 0;
        }

        Integer supportingRanks = 1;
        if (width >= 10) {
            supportingRanks++;
        }

        Integer backRankSupports = determineBackRankSupports(modelsNotInB2B, supportingRanks, count, width);
        Integer midRankSupports =  determineMidRankSupports(modelsNotInB2B, supportingRanks -1, backRankSupports, count, width);

        return midRankSupports + backRankSupports;
    }

    Integer determineFrontRankAttacks(Unit attacker, Integer modelsNotInB2B) {
        return attacker.getWidth() - modelsNotInB2B <= attacker.getCount() ? (attacker.getWidth()-modelsNotInB2B)*attacker.getA() : attacker.getCount() * attacker.getA();
    }

    Integer determineActualWidth(Unit unit) {
        Integer unitActualWidth = unit.getCount() >= unit.getWidth() ? unit.getWidth() * unit.getBaseSize() : unit.getCount() * unit.getBaseSize();

        return unitActualWidth;
    }

    Integer determineBackRankSupports(Integer modelsNotInB2b, Integer supportingRanks, Integer count, Integer width) {
        Integer defaultSupports = count > width ? width - modelsNotInB2b : 0;
        Boolean backRankSupporting = Math.ceil(count / width) <= supportingRanks ? true : false;
        if(backRankSupporting == false){
            return defaultSupports;
        }
        Integer modelsInBackRank = count % width;

        return defaultSupports <= modelsInBackRank ? defaultSupports : modelsInBackRank;
    }

    Integer determineMidRankSupports(Integer modelsNotInB2b, Integer supportingRanks, Integer backRankSupports, Integer count, Integer width) {
        Integer midRankCount = (int)Math.floor((count - width - backRankSupports) / width);
        if(midRankCount < 1) {
            return 0;
        }
        Integer supportingRankCount = midRankCount >= supportingRanks ? supportingRanks : midRankCount;
        return supportingRankCount * width - supportingRankCount * modelsNotInB2b;
    }
}
