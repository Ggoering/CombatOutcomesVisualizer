package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.enums.Identification;
import com.GG.T9AgeCombat.models.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArmorSaveServiceTest {
    ArmorSaveService subject;
    DiceRollingService diceRollingService;
    @BeforeEach
    void setUp() {
        subject = new ArmorSaveService(diceRollingService);
    }

    @Test
    void determineAP() {
        Unit swordmaster = Unit.builder().name(Identification.SWORD_MASTER).movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6).strength(5).toughness(3).initiative(6).wounds(1).attacks(2).leadership(8).baseSize(20).modelCount(30).armorSave(5).width(5).build();
        Unit BT = Unit.builder().name(Identification.SWORD_MASTER).movement(5).offensiveWeaponSkill(8).defensiveWeaponSkill(8).strength(8).toughness(6).initiative(6).wounds(1).attacks(2).leadership(8).baseSize(20).modelCount(30).armorSave(5).width(5).build();
        Unit skink = Unit.builder().name(Identification.SWORD_MASTER).movement(5).offensiveWeaponSkill(2).defensiveWeaponSkill(2).strength(2).toughness(3).initiative(6).wounds(1).attacks(1).leadership(5).baseSize(20).modelCount(30).armorSave(0).width(5).build();

        Integer smTest = subject.calculateArmorPenetration(swordmaster);
        Integer skinkTest = subject.calculateArmorPenetration(skink);
        Integer BTtest = subject.calculateArmorPenetration(BT);

        assertEquals(smTest, 2);
        assertEquals(skinkTest, 0);
        assertEquals(BTtest, 5);

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

        assertEquals(filters1s1, 10);
        assertEquals(filters1s2, 10);
        assertEquals(filters2s, 8);
        assertEquals(filters3s, 6);
        assertEquals(filters4s, 4);
        assertEquals(filters5s1, 2);
        assertEquals(filters6s1, 0);
    }
}