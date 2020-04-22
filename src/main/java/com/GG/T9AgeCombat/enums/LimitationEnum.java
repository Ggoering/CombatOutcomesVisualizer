package com.GG.T9AgeCombat.enums;

public enum LimitationEnum {
    NONE("none"),
    TEN_WIDE("ten wide"),
    FIRST_ROUND("first round");

    private final String value;

    LimitationEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
