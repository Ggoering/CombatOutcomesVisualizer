package com.GG.T9AgeCombat.enums;

public enum Equipment {
    GREAT_WEAPON("great weapon"),
    EXTRA_HAND_WEAPON("extra hand weapon"),
    HEAVY_ARMOR("heavy armor"),
    PLATE_ARMOR("plate armor"),
    SHIELD("shield");

    private final String value;

    Equipment(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
