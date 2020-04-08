package com.GG.T9AgeCombat.enums;

public enum Timing {
    TO_WOUND("to wound"),
    TO_HIT("to hit"),
    ARMOR_SAVE("armor save"),
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
