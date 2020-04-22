package com.GG.T9AgeCombat.model;

import com.GG.T9AgeCombat.models.Unit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UnitTest {
    @Test
    @DisplayName("Apply Pending Wounds - Basic")
    void applyPendingWoundsBasic() {
        Unit singleWoundUnit = Unit.builder().wounds(1).modelCount(15).build();
        Unit multiWoundUnit = Unit.builder().wounds(3).modelCount(15).build();

        Unit singleWoundUnitExpected = Unit.builder().wounds(1).modelCount(10).build();
        Unit multiWoundUnitExpected = Unit.builder().wounds(3).modelCount(13).build();

        singleWoundUnit.setPendingWounds(5);
        singleWoundUnit.applyPendingWounds();
        multiWoundUnit.setPendingWounds(6);
        multiWoundUnit.applyPendingWounds();

        assertThat(singleWoundUnit).isEqualTo(singleWoundUnitExpected);
        assertThat(multiWoundUnit).isEqualTo(multiWoundUnitExpected);
    }

    @Test
    @DisplayName("Apply Pending Wounds - Intermediate")
    void applyPendingWoundsIntermediate() {
        Unit singleWoundUnit = Unit.builder().wounds(1).modelCount(15).build();
        Unit multiWoundUnit = Unit.builder().wounds(3).modelCount(15).build();

        Unit singleWoundUnitExpected = Unit.builder().wounds(1).modelCount(10).build();
        Unit multiWoundUnitExpected = Unit.builder().wounds(3).modelCount(14).woundTracker(2).build();

        singleWoundUnit.setPendingWounds(5);
        singleWoundUnit.applyPendingWounds();
        multiWoundUnit.setPendingWounds(5);
        multiWoundUnit.applyPendingWounds();

        assertThat(singleWoundUnit).isEqualTo(singleWoundUnitExpected);
        assertThat(multiWoundUnit).isEqualTo(multiWoundUnitExpected);
    }

    @Test
    @DisplayName("Apply Pending Wounds - Advanced")
    void applyPendingWoundsAdvanced() {
        Unit singleWoundUnit = Unit.builder().wounds(1).modelCount(15).build();
        Unit multiWoundUnit1 = Unit.builder().wounds(3).modelCount(15).build();
        Unit multiWoundUnit2 = Unit.builder().wounds(3).modelCount(15).build();
        Unit multiWoundUnit3 = Unit.builder().wounds(6).modelCount(1).build();

        Unit singleWoundUnitExpected = Unit.builder().wounds(1).modelCount(10).build();
        Unit multiWoundUnit1Expected = Unit.builder().wounds(3).modelCount(14).woundTracker(1).build();
        Unit multiWoundUnit2Expected = Unit.builder().wounds(3).modelCount(14).woundTracker(2).build();
        Unit multiWoundUnit3Expected = Unit.builder().wounds(6).modelCount(1).woundTracker(4).build();

        singleWoundUnit.setPendingWounds(5);
        singleWoundUnit.applyPendingWounds();
        multiWoundUnit1.setPendingWounds(4);
        multiWoundUnit1.applyPendingWounds();
        multiWoundUnit2.setPendingWounds(5);
        multiWoundUnit2.applyPendingWounds();
        multiWoundUnit3.setPendingWounds(4);
        multiWoundUnit3.applyPendingWounds();

        assertThat(singleWoundUnit).isEqualTo(singleWoundUnitExpected);
        assertThat(multiWoundUnit1).isEqualTo(multiWoundUnit1Expected);
        assertThat(multiWoundUnit2).isEqualTo(multiWoundUnit2Expected);
        assertThat(multiWoundUnit3).isEqualTo(multiWoundUnit3Expected);
    }

    @Test
    @DisplayName("Apply Pending Wounds - Master")
    void applyPendingWoundsMaster() {
        Unit singleWoundUnit = Unit.builder().wounds(1).modelCount(15).build();
        Unit multiWoundUnit1 = Unit.builder().wounds(3).modelCount(15).woundTracker(1).build();
        Unit multiWoundUnit2 = Unit.builder().wounds(3).modelCount(15).woundTracker(2).build();
        Unit multiWoundUnit3 = Unit.builder().wounds(6).modelCount(1).woundTracker(4).build();

        Unit singleWoundUnitExpected = Unit.builder().wounds(1).modelCount(0).build();
        Unit multiWoundUnit1Expected = Unit.builder().wounds(3).modelCount(15).woundTracker(2).build();
        Unit multiWoundUnit2Expected = Unit.builder().wounds(3).modelCount(14).woundTracker(1).build();
        Unit multiWoundUnit3Expected = Unit.builder().wounds(6).modelCount(0).woundTracker(0).build();

        singleWoundUnit.setPendingWounds(15);
        singleWoundUnit.applyPendingWounds();
        multiWoundUnit1.setPendingWounds(1);
        multiWoundUnit1.applyPendingWounds();
        multiWoundUnit2.setPendingWounds(2);
        multiWoundUnit2.applyPendingWounds();
        multiWoundUnit3.setPendingWounds(2);
        multiWoundUnit3.applyPendingWounds();

        assertThat(singleWoundUnit).isEqualTo(singleWoundUnitExpected);
        assertThat(multiWoundUnit1).isEqualTo(multiWoundUnit1Expected);
        assertThat(multiWoundUnit2).isEqualTo(multiWoundUnit2Expected);
        assertThat(multiWoundUnit3).isEqualTo(multiWoundUnit3Expected);
    }

    @Test
    @DisplayName("Apply Pending Wounds - Over Kill")
    void applyPendingWoundsOverKill() {
        Unit singleWoundUnit = Unit.builder().wounds(1).modelCount(15).build();
        Unit multiWoundUnit1 = Unit.builder().wounds(3).modelCount(5).woundTracker(1).build();
        Unit multiWoundUnit2 = Unit.builder().wounds(3).modelCount(4).woundTracker(2).build();
        Unit multiWoundUnit3 = Unit.builder().wounds(6).modelCount(1).woundTracker(4).build();

        Unit singleWoundUnitExpected = Unit.builder().wounds(1).modelCount(0).build();
        Unit multiWoundUnit1Expected = Unit.builder().wounds(3).modelCount(0).woundTracker(0).build();
        Unit multiWoundUnit2Expected = Unit.builder().wounds(3).modelCount(0).woundTracker(0).build();
        Unit multiWoundUnit3Expected = Unit.builder().wounds(6).modelCount(0).woundTracker(0).build();

        singleWoundUnit.setPendingWounds(20);
        singleWoundUnit.applyPendingWounds();
        multiWoundUnit1.setPendingWounds(18);
        multiWoundUnit1.applyPendingWounds();
        multiWoundUnit2.setPendingWounds(18);
        multiWoundUnit2.applyPendingWounds();
        multiWoundUnit3.setPendingWounds(5);
        multiWoundUnit3.applyPendingWounds();

        assertThat(singleWoundUnit).isEqualTo(singleWoundUnitExpected);
        assertThat(multiWoundUnit1).isEqualTo(multiWoundUnit1Expected);
        assertThat(multiWoundUnit2).isEqualTo(multiWoundUnit2Expected);
        assertThat(multiWoundUnit3).isEqualTo(multiWoundUnit3Expected);
    }
}
