package com.GG.T9AgeCombat.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DiceRollingServiceTest {
    DiceRollingService subject;

    @BeforeEach
    void setUp() {
        subject = new DiceRollingService();
    }

    @Test
    void roll() {
        List<Integer> testWith6 = subject.roll(6);
        List<Integer> rollBig = subject.roll(1000);

        assertEquals(6, testWith6.size());
        assertTrue(6 <= testWith6.stream().mapToInt(a -> a).sum());
        assertTrue(36 >= testWith6.stream().mapToInt(a -> a).sum());

        assertEquals(1000, rollBig.size());
        List<Integer> ones = rollBig.stream().filter(a -> a == 1).collect(Collectors.toList());
        List<Integer> twos = rollBig.stream().filter(a -> a == 2).collect(Collectors.toList());
        List<Integer> threes = rollBig.stream().filter(a -> a == 3).collect(Collectors.toList());
        List<Integer> fours = rollBig.stream().filter(a -> a == 4).collect(Collectors.toList());
        List<Integer> fives = rollBig.stream().filter(a -> a == 5).collect(Collectors.toList());
        List<Integer> sixes = rollBig.stream().filter(a -> a == 6).collect(Collectors.toList());
        List<Integer> lessThanOne = rollBig.stream().filter(a -> a < 1).collect(Collectors.toList());
        List<Integer> greaterThan6 = rollBig.stream().filter(a -> a > 6).collect(Collectors.toList());

        assertTrue(1 <= ones.size());
        assertTrue(1 <= twos.size());
        assertTrue(1 <= threes.size());
        assertTrue(1 <= fours.size());
        assertTrue(1 <= fives.size());
        assertTrue(1 <= sixes.size());
        assertEquals(0, lessThanOne.size());
        assertEquals(0, greaterThan6.size());
    }

    @Test
    void rollWithSum() {
        int testWith6 = subject.rollWithSum(6);
        int rollBig = subject.rollWithSum(1000);

        assertTrue(6 <= testWith6);
        assertTrue(36 >= testWith6);
        assertTrue(1000 <= rollBig);
        assertTrue(6000 >= rollBig);
    }


    @Test
    void filterOutMissedAttacks() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);

        Integer results1 = subject.getFinalWithReRolls(list, 2, 0, 6);
        Integer results2 = subject.getFinalWithReRolls(list, 3, 0, 6);
        Integer results3 = subject.getFinalWithReRolls(list, 4, 0, 6);
        Integer results4 = subject.getFinalWithReRolls(list, 5, 0, 6);

        assertEquals(5, results1);
        assertEquals(4, results2);
        assertEquals(3, results3);
        assertEquals(2, results4);
    }

    @Test
    void getFinalWithReRolls() {
        //Set up
        DiceRollingService subjectFinal = Mockito.spy(subject);

        List<Integer> initialRoll = new ArrayList<>();
        initialRoll.add(1);
        initialRoll.add(2);
        initialRoll.add(3);
        initialRoll.add(4);
        initialRoll.add(5);
        initialRoll.add(6);

        List<Integer> firstReRoll = new ArrayList<>();
        firstReRoll.add(1);
        firstReRoll.add(6);
        firstReRoll.add(4);

        List<Integer> secondReRoll = new ArrayList<>();
        secondReRoll.add(2);
        secondReRoll.add(6);
        secondReRoll.add(1);
        secondReRoll.add(4);

        List<Integer> thirdReRoll = new ArrayList<>();
        thirdReRoll.add(6);

        List<Integer> fourthReRoll = new ArrayList<>();
        fourthReRoll.add(2);

        //Test 1 re rolls failed only
        Mockito.doReturn(firstReRoll).when(subjectFinal).roll(3);
        Integer results1 = subjectFinal.getFinalWithReRolls(initialRoll, 4, 6, 6);

        assertEquals(5, results1);

        //Test 2 re rolls successes only
        Mockito.doReturn(secondReRoll).when(subjectFinal).roll(4);
        Integer results2 = subjectFinal.getFinalWithReRolls(initialRoll, 3, 1, 1);

        assertEquals(2, results2);

        //Test 3 re rolls a failed specific range
        Mockito.doReturn(thirdReRoll).when(subjectFinal).roll(1);
        Integer results3 = subjectFinal.getFinalWithReRolls(initialRoll, 4, 2, 6);

        assertEquals(4, results3);

        //Test 4 re rolls a successful specific range
        Mockito.doReturn(fourthReRoll).when(subjectFinal).roll(1);
        Integer results4 = subjectFinal.getFinalWithReRolls(initialRoll, 5, 1, 5);

        assertEquals(1, results4);

        //Test 5 re rolls success and failure without rerolling an reroll
        Mockito.doReturn(firstReRoll).when(subjectFinal).roll(3);
        Integer results5 = subjectFinal.getFinalWithReRolls(initialRoll, 4, 6, 1);

        assertEquals(4, results5);
    }
}