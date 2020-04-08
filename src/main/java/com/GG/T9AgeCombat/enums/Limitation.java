package com.GG.T9AgeCombat.enums;

public enum Limitation {
    FIRST_ROUND("first round");

    private final String value;

    Limitation(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
