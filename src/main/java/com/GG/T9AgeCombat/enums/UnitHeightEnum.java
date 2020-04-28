package com.GG.T9AgeCombat.enums;

public enum UnitHeightEnum {
    STANDARD("Standard"),
    LARGE("Large"),
    GIGANTIC("Gigantic");

    private final String value;

    UnitHeightEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
