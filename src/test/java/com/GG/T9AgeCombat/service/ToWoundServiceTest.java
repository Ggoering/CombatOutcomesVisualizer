package com.GG.T9AgeCombat.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ToWoundServiceTest {

    private ToWoundService subject;
    private DiceRollingService diceRollingService;

    @BeforeEach
    void setUp() {
        subject = new ToWoundService(diceRollingService);
    }

    @Test
    void determineToWoundThreshold() {
        Integer need6s1 = subject.determineToWoundThreshold(1, 6);
        Integer need6s2 = subject.determineToWoundThreshold(3, 7);
        Integer need6s3 = subject.determineToWoundThreshold(5, 8);
        Integer need6s4 = subject.determineToWoundThreshold(7, 9);
        Integer need5s1 = subject.determineToWoundThreshold(7, 8);
        Integer need5s2 = subject.determineToWoundThreshold(3, 4);
        Integer need4s1 = subject.determineToWoundThreshold(3, 3);
        Integer need4s2 = subject.determineToWoundThreshold(8, 8);
        Integer need3s1 = subject.determineToWoundThreshold(5, 4);
        Integer need3s2 = subject.determineToWoundThreshold(2, 1);
        Integer need2s1 = subject.determineToWoundThreshold(3, 1);
        Integer need2s2 = subject.determineToWoundThreshold(8, 2);
        Integer need2s3 = subject.determineToWoundThreshold(6, 4);

        assertEquals(need6s1, 9);
        assertEquals(need6s2, 8);
        assertEquals(need6s3, 7);
        assertEquals(need6s4, 6);
        assertEquals(need5s1, 5);
        assertEquals(need5s2, 5);
        assertEquals(need4s1, 4);
        assertEquals(need4s2, 4);
        assertEquals(need3s1, 3);
        assertEquals(need3s2, 3);
        assertEquals(need2s1, 2);
        assertEquals(need2s2, -2);
        assertEquals(need2s3, 2);

    }

    @Test
    void filterOutFailedToWound() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(3);
        list.add(3);
        list.add(4);
        list.add(4);
        list.add(5);
        list.add(5);
        list.add(6);
        list.add(6);

        Integer filters1s1 = subject.removeFailedToWoundRolls(list, 2);
        Integer filters1s2 = subject.removeFailedToWoundRolls(list, -2);
        Integer filters2s = subject.removeFailedToWoundRolls(list, 3);
        Integer filters3s = subject.removeFailedToWoundRolls(list, 4);
        Integer filters4s = subject.removeFailedToWoundRolls(list, 5);
        Integer filters5s1 = subject.removeFailedToWoundRolls(list, 6);
        Integer filters5s2 = subject.removeFailedToWoundRolls(list, 10);

        assertEquals(filters1s1, 10);
        assertEquals(filters1s2, 10);
        assertEquals(filters2s, 8);
        assertEquals(filters3s, 6);
        assertEquals(filters4s, 4);
        assertEquals(filters5s1, 2);
        assertEquals(filters5s2, 2);
    }
}