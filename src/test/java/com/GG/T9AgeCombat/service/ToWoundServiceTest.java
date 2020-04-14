package com.GG.T9AgeCombat.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ToWoundServiceTest {

    private ToWoundService subject;

    @BeforeEach
    void setUp() {
        subject = new ToWoundService(new DiceRollingService());
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

        assertEquals(9, need6s1);
        assertEquals(8, need6s2);
        assertEquals(7, need6s3);
        assertEquals(6, need6s4);
        assertEquals(5, need5s1);
        assertEquals(5, need5s2);
        assertEquals(4, need4s1);
        assertEquals(4, need4s2);
        assertEquals(3, need3s1);
        assertEquals(3, need3s2);
        assertEquals(2, need2s1);
        assertEquals(-2, need2s2);
        assertEquals(2, need2s3);

    }

    @Test
    void filterOutFailedToWound() {
        List<Integer> list = new ArrayList<>();
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

        assertEquals(10, filters1s1);
        assertEquals(10, filters1s2);
        assertEquals(8, filters2s);
        assertEquals(6, filters3s);
        assertEquals(4, filters4s);
        assertEquals(2, filters5s1);
        assertEquals(2, filters5s2);
    }
}