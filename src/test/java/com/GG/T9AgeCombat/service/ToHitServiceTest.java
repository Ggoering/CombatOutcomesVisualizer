package com.GG.T9AgeCombat.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

 class ToHitServiceTest {
    private ToHitService subject;
    private DiceRollingService diceRollingService;

    @BeforeEach
    void setUp() {
        subject = new ToHitService(diceRollingService);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void determineToHitThreshold(){
        Integer toHitThreshold1 = subject.determineToHitThreshold(8,4);
        Integer toHitThreshold2 = subject.determineToHitThreshold(5,4);
        Integer toHitThreshold3 = subject.determineToHitThreshold(8,8);
        Integer toHitThreshold4 = subject.determineToHitThreshold(2,5);
        Integer toHitThreshold5 = subject.determineToHitThreshold(2,6);

        assertEquals(toHitThreshold1, 2);
        assertEquals(toHitThreshold2, 3);
        assertEquals(toHitThreshold3, 4);
        assertEquals(toHitThreshold4, 4);
        assertEquals(toHitThreshold5, 5);
    }
    @Test
    void filterOutMissedAttacks(){
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);

        Integer results1 = subject.removeFailedToHitRolls(list, 2);
        Integer results2 = subject.removeFailedToHitRolls(list, 3);
        Integer results3 = subject.removeFailedToHitRolls(list, 4);
        Integer results4 = subject.removeFailedToHitRolls(list, 5);

        assertEquals(results1, 5);
        assertEquals(results2, 4);
        assertEquals(results3, 3);
        assertEquals(results4, 2);
    }
}
