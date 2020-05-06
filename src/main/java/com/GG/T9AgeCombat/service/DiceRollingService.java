package com.GG.T9AgeCombat.service;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DiceRollingService {
    private static final int SIX_SIDED_DIE = 6;
    private static final int REROLL_GREATER_THAN_MINIMUM = 6;
    private static final int REROLL_LESS_THAN_MINIMUM = 1;
    private final Random randomInt = new Random();

    List<Integer> roll(Integer quantity) {
        List<Integer> resultList = new ArrayList<>();

        for (int i = 0; i < quantity; i++) {
            // nextInt(range) == 0 to range, so we add 1
            resultList.add(randomInt.nextInt(SIX_SIDED_DIE) + 1);
        }

        return resultList.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    }

    int rollWithSum(Integer quantity) {
        int total = 0;

        for (int i = 0; i < quantity; i++) {
            // nextInt(range) == 0 to range, so we add 1
            total += randomInt.nextInt(SIX_SIDED_DIE) + 1;
        }

        return total;
    }

    int rollWithSumTakeHighest(int quantity, int takeHighestCount) {
        int total = 0;
        List<Integer> dieRollList = roll(quantity);
        Collections.sort(dieRollList, Collections.reverseOrder());

        for (int i = 0; i < takeHighestCount; i++) {
            total += dieRollList.get(i);
        }

        return total;
    }

    Integer getFinalWithReRolls(List<Integer> resultList, Integer successThreshold, Integer reRollLessThan, Integer reRollGreaterThan) {
        List<Integer> initialSuccesses = resultList.stream().filter(r -> r >= successThreshold).collect(Collectors.toList());
        Integer successCount = initialSuccesses.size();

        if (reRollGreaterThan < REROLL_GREATER_THAN_MINIMUM) {
            Integer quantityToReRoll = (int) initialSuccesses.stream().filter(r -> r > reRollGreaterThan).count();
            Integer failuresAfterReRoll = (int) this.roll(quantityToReRoll).stream().filter(r -> r < successThreshold).count();
            successCount = successCount - failuresAfterReRoll;
        }

        if (reRollLessThan > REROLL_LESS_THAN_MINIMUM) {
            List<Integer> initialFailures = resultList.stream().filter(r -> r < successThreshold).collect(Collectors.toList());
            Integer quantityToReRoll = (int) initialFailures.stream().filter(r -> r < reRollLessThan).count();
            Integer successAfterReroll = (int) this.roll(quantityToReRoll).stream().filter(r -> r >= successThreshold).count();
            successCount = successCount + successAfterReroll;
        }

        return successCount;
    }


}

