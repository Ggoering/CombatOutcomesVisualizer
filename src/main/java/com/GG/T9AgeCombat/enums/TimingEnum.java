package com.GG.T9AgeCombat.enums;

public enum TimingEnum {
    DETERMINE_ATTACK_QUANTITY("determine attack quantity"),
    ROLL_TO_WOUND("roll to wound"),
    ROLL_TO_HIT("roll to hit"),
    ROLL_ARMOR_SAVE("roll armor save"),
    ALL("all");

    private final String value;

    TimingEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {

        return value;
    }
}
