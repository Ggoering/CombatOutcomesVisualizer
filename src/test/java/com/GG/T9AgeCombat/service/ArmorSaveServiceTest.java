package com.GG.T9AgeCombat.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ArmorSaveServiceTest {
    ArmorSaveService subject;

    @BeforeEach
    void setUp() {
        subject = new ArmorSaveService(new DiceRollingService());
    }

    @Test
    @DisplayName("Calculate Armor Save Threshold")
    void calculateArmorSaveThreshold() {
        int armorSaveThreshold1 = subject.calculateArmorSaveThreshold(0, 0);
        int armorSaveThreshold2 = subject.calculateArmorSaveThreshold(2, 0);
        int armorSaveThreshold3 = subject.calculateArmorSaveThreshold(4, 0);
        int armorSaveThreshold4 = subject.calculateArmorSaveThreshold(6, 0);
        int armorSaveThreshold5 = subject.calculateArmorSaveThreshold(0, 1);
        int armorSaveThreshold6 = subject.calculateArmorSaveThreshold(3, 1);
        int armorSaveThreshold7 = subject.calculateArmorSaveThreshold(5, 2);
        int armorSaveThreshold8 = subject.calculateArmorSaveThreshold(5, 4);
        int armorSaveThreshold9 = subject.calculateArmorSaveThreshold(2, 5);
        int armorSaveThreshold10 = subject.calculateArmorSaveThreshold(4, 6);

        assertThat(armorSaveThreshold1).isEqualTo(7);
        assertThat(armorSaveThreshold2).isEqualTo(5);
        assertThat(armorSaveThreshold3).isEqualTo(3);
        assertThat(armorSaveThreshold4).isEqualTo(2);
        assertThat(armorSaveThreshold5).isEqualTo(8);
        assertThat(armorSaveThreshold6).isEqualTo(5);
        assertThat(armorSaveThreshold7).isEqualTo(4);
        assertThat(armorSaveThreshold8).isEqualTo(6);
        assertThat(armorSaveThreshold9).isEqualTo(10);
        assertThat(armorSaveThreshold10).isEqualTo(9);
    }
}