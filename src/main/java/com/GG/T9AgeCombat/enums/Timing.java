package com.GG.T9AgeCombat.enums;

public enum Timing {
    DETERMINE_ATTACK_QUANTITY("attack quantity"),
    ROLL_TO_WOUND("to wound"),
    ROLL_TO_HIT("to hit"),
    ROLL_ARMOR_SAVE("armor save"),
    ALL("all");

    private final String value;

    Timing(String value) {
        this.value = value;
    }

    @Override
    public String toString() {

        return value;
    }
}
