package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.models.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    void filterOutMissedSaves() {
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

        Integer filters1s1 = subject.removeFailedArmorSaveRolls(list, 2);
        Integer filters1s2 = subject.removeFailedArmorSaveRolls(list, -2);
        Integer filters2s = subject.removeFailedArmorSaveRolls(list, 3);
        Integer filters3s = subject.removeFailedArmorSaveRolls(list, 4);
        Integer filters4s = subject.removeFailedArmorSaveRolls(list, 5);
        Integer filters5s1 = subject.removeFailedArmorSaveRolls(list, 6);
        Integer filters6s1 = subject.removeFailedArmorSaveRolls(list, 10);

        assertEquals(10, filters1s1);
        assertEquals(10, filters1s2);
        assertEquals(8, filters2s);
        assertEquals(6, filters3s);
        assertEquals(4, filters4s);
        assertEquals(2, filters5s1);
        assertEquals(0, filters6s1);
    }
}