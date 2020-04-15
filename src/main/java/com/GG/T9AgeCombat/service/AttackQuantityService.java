package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.enums.Identification;
import com.GG.T9AgeCombat.models.Unit;
import org.springframework.stereotype.Service;

@Service
public class AttackQuantityService {
    Integer determineAttackQuantity(Unit attacker, Unit defender) {
        Integer modelsNotInB2B = determineModelsNotInBaseContact(attacker.getActualWidth(), attacker, defender.getActualWidth());
        Integer supportingAttacks = determineSupportingAttacks(attacker, modelsNotInB2B);
        Integer frontRankAttacks = determineFrontRankAttacks(attacker, modelsNotInB2B);

        return supportingAttacks + frontRankAttacks;
    }

    Integer determineModelsNotInBaseContact(Integer attackerActualWidth, Unit attacker, Integer defenderActualWidth) {
        int widthDifference = defenderActualWidth - attackerActualWidth;

        if (widthDifference >= 0) {
            return 0;
        }

        return Math.abs(widthDifference / attacker.getBaseSize()) - 2;
    }

    Integer determineSupportingAttacks(Unit attacker, Integer modelsNotInB2B) {
        Integer modelCount = attacker.getModelCount();
        Integer unitWidth = attacker.getWidth();
        if (attacker.getName().equals(Identification.MOUNT) || (modelCount - unitWidth) <= 0) {
            return 0;
        }

        int supportingRanks = 1;

        if (unitWidth >= 10) {
            supportingRanks++;
        }

        Integer backRankSupports = determineBackRankSupports(modelsNotInB2B, supportingRanks, modelCount, unitWidth);
        Integer midRankSupports = determineMidRankSupports(modelsNotInB2B, supportingRanks - 1, backRankSupports, modelCount, unitWidth);

        return midRankSupports + backRankSupports;
    }

    Integer determineFrontRankAttacks(Unit attacker, Integer modelsNotInB2B) {
        return attacker.getWidth() - modelsNotInB2B <= attacker.getModelCount() ? (attacker.getWidth() - modelsNotInB2B) * attacker.getAttacks() : attacker.getModelCount() * attacker.getAttacks();
    }

    Integer determineBackRankSupports(Integer modelsNotInB2b, Integer supportingRanks, Integer count, Integer width) {
        int defaultSupports = count > width ? width - modelsNotInB2b : 0;
        boolean backRankSupporting = ((count / width) <= supportingRanks);

        if (!backRankSupporting) {
            return defaultSupports;
        }

        int modelsInBackRank = count % width;
        return Math.min(defaultSupports, modelsInBackRank);
    }

    Integer determineMidRankSupports(Integer modelsNotInB2b, Integer supportingRanks, Integer backRankSupports, Integer count, Integer width) {
        int midRankCount = (count - width - backRankSupports) / width;

        if (midRankCount < 1) {
            return 0;
        }

        Integer supportingRankCount = midRankCount >= supportingRanks ? supportingRanks : midRankCount;
        return supportingRankCount * width - supportingRankCount * modelsNotInB2b;
    }
}
