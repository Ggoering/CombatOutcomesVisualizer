package com.GG.T9AgeCombat.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
        Integer toHitThreshold1 = subject.determineToHitThreshold(8, 4);
        Integer toHitThreshold2 = subject.determineToHitThreshold(5, 4);
        Integer toHitThreshold3 = subject.determineToHitThreshold(8, 8);
        Integer toHitThreshold4 = subject.determineToHitThreshold(2, 5);
        Integer toHitThreshold5 = subject.determineToHitThreshold(2, 6);

        assertEquals(2, toHitThreshold1);
        assertEquals(3, toHitThreshold2);
        assertEquals(4, toHitThreshold3);
        assertEquals(4, toHitThreshold4);
        assertEquals(5, toHitThreshold5);
    }
}
