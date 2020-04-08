package com.GG.T9AgeCombat.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

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
        assertTrue(0 == lessThanOne.size());
        assertTrue(0 == greaterThan6.size());
    }

    @Test
    void rollWithSum() {
        Integer testWith6 = subject.rollWithSum(6);
        Integer rollBig = subject.rollWithSum(1000);

        assertTrue(6 <= testWith6);
        assertTrue(36 >= testWith6);
        assertTrue(1000 <= rollBig);
        assertTrue(6000 >= rollBig);
    }
}