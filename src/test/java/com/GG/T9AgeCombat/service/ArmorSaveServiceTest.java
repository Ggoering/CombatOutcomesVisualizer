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
        Unit swordmaster = Unit.builder().name(Identification.SWORD_MASTER).M(5).OWS(6).DWS(6).S(5).T(3).I(6).W(1).A(2).Ld(8).baseSize(20).count(30).AS(5).width(5).build();
        Unit BT = Unit.builder().name(Identification.SWORD_MASTER).M(5).OWS(8).DWS(8).S(8).T(6).I(6).W(1).A(2).Ld(8).baseSize(20).count(30).AS(5).width(5).build();
        Unit skink = Unit.builder().name(Identification.SWORD_MASTER).M(5).OWS(2).DWS(2).S(2).T(3).I(6).W(1).A(1).Ld(5).baseSize(20).count(30).AS(0).width(5).build();

        Integer smTest = subject.determineAP(swordmaster);
        Integer skinkTest = subject.determineAP(skink);
        Integer BTtest = subject.determineAP(BT);

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

        Integer filters1s1 = subject.filterOutMissedSaves(list, 2);
        Integer filters1s2 = subject.filterOutMissedSaves(list, -2);
        Integer filters2s = subject.filterOutMissedSaves(list, 3);
        Integer filters3s = subject.filterOutMissedSaves(list, 4);
        Integer filters4s = subject.filterOutMissedSaves(list, 5);
        Integer filters5s1 = subject.filterOutMissedSaves(list, 6);
        Integer filters6s1 = subject.filterOutMissedSaves(list, 10);

        assertEquals(filters1s1, 10);
        assertEquals(filters1s2, 10);
        assertEquals(filters2s, 8);
        assertEquals(filters3s, 6);
        assertEquals(filters4s, 4);
        assertEquals(filters5s1, 2);
        assertEquals(filters6s1, 0);
    }
}