package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.enums.UnitHeightEnum;
import com.GG.T9AgeCombat.models.OffensiveProfile;
import com.GG.T9AgeCombat.models.Unit;
import org.springframework.stereotype.Service;

import static com.GG.T9AgeCombat.common.Constants.HORDE_MODELS_PER_RANK;

@Service
public class AttackQuantityService {
    private static final int STANDARD_HEIGHT_SUPPORTING_ATTACKS = 1;
    private static final int LARGE_HEIGHT_SUPPORTING_ATTACKS = 3;
    private static final int GIGANTIC_HEIGHT_SUPPORTING_ATTACKS = 5;
    private static final int DEFAULT_SUPPORTING_RANKS = 1;

    int determineAttackQuantity(OffensiveProfile offensiveProfile, Unit attacker, Unit defender) {
        int modelsNotInB2B = determineModelsNotInBaseContact(attacker.getWidth(), attacker, defender.getWidth());
        int supportingAttacks = determineSupportingAttacks(offensiveProfile, attacker, modelsNotInB2B);
        int frontRankAttacks = determineFrontRankAttacks(offensiveProfile, attacker, modelsNotInB2B);

        return supportingAttacks + frontRankAttacks;
    }

    int determineModelsNotInBaseContact(int attackerActualWidth, Unit attacker, int defenderActualWidth) {
        int widthDifference = defenderActualWidth - attackerActualWidth;

        if (widthDifference >= 0) {
            return 0;
        }

        return Math.abs(widthDifference / attacker.getBasesize()) - 2;
    }

    int determineSupportingAttacks(OffensiveProfile offensiveProfile, Unit attacker, int modelsNotInB2B) {
        int modelCount = attacker.getModelCount();
        int unitWidth = attacker.getModelsPerRank();

        if (offensiveProfile.isMount() || (modelCount - unitWidth) <= 0) {
            return 0;
        }

        int supportingRanks = DEFAULT_SUPPORTING_RANKS + attacker.getExtraRanks() + attacker.getExtraRankModifier();

        if (unitWidth >= HORDE_MODELS_PER_RANK) {
            supportingRanks++;
        }

        int backRankSupports = determineBackRankSupports(modelsNotInB2B, supportingRanks, modelCount, unitWidth);
        int midRankSupports = determineMidRankSupports(modelsNotInB2B, supportingRanks - 1, backRankSupports, modelCount, unitWidth);
        int supportingAttackByModel = getSupportingAttacksPerModel(attacker.getHeight(), offensiveProfile.getActualAttacks());

        return (midRankSupports + backRankSupports) * supportingAttackByModel;
    }

    int determineFrontRankAttacks(OffensiveProfile offensiveProfile, Unit attacker, int modelsNotInB2B) {
        return attacker.getModelsPerRank() - modelsNotInB2B <= attacker.getModelCount() ? (attacker.getModelsPerRank() - modelsNotInB2B) * offensiveProfile.getAttacks() : attacker.getModelCount() * offensiveProfile.getAttacks();
    }

    int determineBackRankSupports(int modelsNotInB2b, int supportingRanks, int count, int width) {
        int defaultSupports = count > width ? width - modelsNotInB2b : 0;
        boolean backRankSupporting = ((count / width) <= supportingRanks);

        if (!backRankSupporting) {
            return defaultSupports;
        }

        int modelsInBackRank = count % width;
        return Math.min(defaultSupports, modelsInBackRank);
    }

    int determineMidRankSupports(int modelsNotInB2b, int supportingRanks, int backRankSupports, int count, int width) {
        int midRankCount = (count - width - backRankSupports) / width;

        if (midRankCount < 1) {
            return 0;
        }

        int supportingRankCount = Math.min(midRankCount, supportingRanks);
        return supportingRankCount * width - supportingRankCount * modelsNotInB2b;
    }

    int getSupportingAttacksPerModel(UnitHeightEnum unitHeight, int numberOfAttacks) {
        switch (unitHeight) {
            case STANDARD:
                return (Math.min(numberOfAttacks, STANDARD_HEIGHT_SUPPORTING_ATTACKS));
            case LARGE:
                return (Math.min(numberOfAttacks, LARGE_HEIGHT_SUPPORTING_ATTACKS));
            case GIGANTIC:
                return (Math.min(numberOfAttacks, GIGANTIC_HEIGHT_SUPPORTING_ATTACKS));
            default:
                return 0;
        }
    }
}
