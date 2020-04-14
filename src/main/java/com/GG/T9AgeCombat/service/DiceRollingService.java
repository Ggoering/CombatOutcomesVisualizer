package com.GG.T9AgeCombat.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class DiceRollingService {
    private static final int SIX_SIDED_DIE = 6;
    private final Random randomInt = new Random();

    List<Integer> roll(Integer quantity) {
        List<Integer> resultList = new ArrayList<>();

        for (int i = 0; i < quantity; i++) {
            // nextInt(range) == 0 to range, so we add 1
            resultList.add(randomInt.nextInt(SIX_SIDED_DIE) + 1);
        }

        return resultList;
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
}

