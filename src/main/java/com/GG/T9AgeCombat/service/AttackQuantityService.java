package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.enums.UnitHeightEnum;
import com.GG.T9AgeCombat.models.Unit;
import com.GG.T9AgeCombat.models.UnitHeight;
import org.springframework.stereotype.Service;

import static com.GG.T9AgeCombat.common.Constants.HORDE_MODELS_PER_RANK;

@Service
public class AttackQuantityService {
    private static final int STANDARD_HEIGHT_SUPPORTING_ATTACKS = 1;
    private static final int LARGE_HEIGHT_SUPPORTING_ATTACKS = 3;
    private static final int GIGANTIC_HEIGHT_SUPPORTING_ATTACKS = 5;
    private static final int DEFAULT_SUPPORTING_RANKS = 1;

    Integer determineAttackQuantity(Unit attacker, Unit defender) {
        Integer modelsNotInB2B = determineModelsNotInBaseContact(attacker.getWidth(), attacker, defender.getWidth());
        Integer supportingAttacks = determineSupportingAttacks(attacker, modelsNotInB2B);
        Integer frontRankAttacks = determineFrontRankAttacks(attacker, modelsNotInB2B);

        return supportingAttacks + frontRankAttacks;
    }

    Integer determineModelsNotInBaseContact(Integer attackerActualWidth, Unit attacker, Integer defenderActualWidth) {
        int widthDifference = defenderActualWidth - attackerActualWidth;

        if (widthDifference >= 0) {
            return 0;
        }

        return Math.abs(widthDifference / attacker.getBasesize()) - 2;
    }

    Integer determineSupportingAttacks(Unit attacker, Integer modelsNotInB2B) {
        int modelCount = attacker.getModelCount();
        int unitWidth = attacker.getModelsPerRank();

        if (attacker.isMount() || (modelCount - unitWidth) <= 0) {
            return 0;
        }

        int supportingRanks = DEFAULT_SUPPORTING_RANKS;

        if (unitWidth >= HORDE_MODELS_PER_RANK) {
            supportingRanks++;
        }

        int backRankSupports = determineBackRankSupports(modelsNotInB2B, supportingRanks, modelCount, unitWidth);
        int midRankSupports = determineMidRankSupports(modelsNotInB2B, supportingRanks - 1, backRankSupports, modelCount, unitWidth);
        int supportingAttackByModel = getSupportingAttacksPerModel(attacker.getUnitHeightByUnitHeightId(), attacker.getAttacks());

        return (midRankSupports + backRankSupports) * supportingAttackByModel;
    }

    Integer determineFrontRankAttacks(Unit attacker, Integer modelsNotInB2B) {
        return attacker.getModelsPerRank() - modelsNotInB2B <= attacker.getModelCount() ? (attacker.getModelsPerRank() - modelsNotInB2B) * attacker.getAttacks() : attacker.getModelCount() * attacker.getAttacks();
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

    int getSupportingAttacksPerModel(UnitHeight unitHeight, int numberOfAttacks) {
        if (unitHeight.getValue().equals(UnitHeightEnum.STANDARD.toString())) {
            return (Math.min(numberOfAttacks, STANDARD_HEIGHT_SUPPORTING_ATTACKS));
        } else if (unitHeight.getValue().equals(UnitHeightEnum.LARGE.toString())) {
            return (Math.min(numberOfAttacks, LARGE_HEIGHT_SUPPORTING_ATTACKS));
        } else if (unitHeight.getValue().equals(UnitHeightEnum.GIGANTIC.toString())) {
            return (Math.min(numberOfAttacks, GIGANTIC_HEIGHT_SUPPORTING_ATTACKS));
        } else {
            // TODO: Log and throw an exception if a model doesn't have a valid height
            return 0;
        }
    }
}
