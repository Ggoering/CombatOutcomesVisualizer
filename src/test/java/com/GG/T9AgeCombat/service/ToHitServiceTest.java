package com.GG.T9AgeCombat.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ToHitServiceTest {
    private ToHitService subject;

    @BeforeEach
    void setUp() {
        subject = new ToHitService(new DiceRollingService());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void determineToHitThreshold() {
        // Standard to hit ranges
        Integer toHitThreshold1 = subject.determineToHitThreshold(8, 4, 0);
        Integer toHitThreshold2 = subject.determineToHitThreshold(5, 4, 0);
        Integer toHitThreshold3 = subject.determineToHitThreshold(8, 8, 0);
        Integer toHitThreshold4 = subject.determineToHitThreshold(2, 5, 0);
        Integer toHitThreshold5 = subject.determineToHitThreshold(2, 6, 0);

        // Min/max thresholds
        Integer toHitThreshold6 = subject.determineToHitThreshold(8, 1, 0);
        Integer toHitThreshold7 = subject.determineToHitThreshold(1, 9, 0);
        Integer toHitThreshold8 = subject.determineToHitThreshold(8, 1, 2);
        Integer toHitThreshold9 = subject.determineToHitThreshold(1, 9, -2);

        // To hit bonuses
        Integer toHitThreshold10 = subject.determineToHitThreshold(4, 4, 1);
        Integer toHitThreshold11 = subject.determineToHitThreshold(4, 4, -1);

        assertEquals(2, toHitThreshold1);
        assertEquals(3, toHitThreshold2);
        assertEquals(4, toHitThreshold3);
        assertEquals(4, toHitThreshold4);
        assertEquals(5, toHitThreshold5);

        assertEquals(2, toHitThreshold6);
        assertEquals(6, toHitThreshold7);
        assertEquals(2, toHitThreshold8);
        assertEquals(6, toHitThreshold9);

        assertEquals(3, toHitThreshold10);
        assertEquals(5, toHitThreshold11);
    }
}
