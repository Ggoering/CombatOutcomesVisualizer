package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.models.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArmorSaveServiceTest {
    ArmorSaveService subject;

    @BeforeEach
    void setUp() {
        subject = new ArmorSaveService(new DiceRollingService());
    }

    @Test
    void determineAP() {
        Unit swordmaster = Unit.builder().name("Swordmaster").movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6).strength(5).toughness(3).initiative(6).wounds(1).attacks(2).leadership(8).basesize(20).modelCount(30).armorSave(5).width(5).build();
        Unit BT = Unit.builder().name("Swordmaster").movement(5).offensiveWeaponSkill(8).defensiveWeaponSkill(8).strength(8).toughness(6).initiative(6).wounds(1).attacks(2).leadership(8).basesize(20).modelCount(30).armorSave(5).width(5).build();
        Unit skink = Unit.builder().name("Swordmaster").movement(5).offensiveWeaponSkill(2).defensiveWeaponSkill(2).strength(2).toughness(3).initiative(6).wounds(1).attacks(1).leadership(5).basesize(20).modelCount(30).armorSave(0).width(5).build();

        Integer smTest = subject.calculateArmorPenetration(swordmaster);
        Integer skinkTest = subject.calculateArmorPenetration(skink);
        Integer BTtest = subject.calculateArmorPenetration(BT);

        assertEquals(2, smTest);
        assertEquals(0, skinkTest);
        assertEquals(5, BTtest);

    }
}