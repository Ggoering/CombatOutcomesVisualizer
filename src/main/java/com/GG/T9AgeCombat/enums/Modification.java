package com.GG.T9AgeCombat.enums;

public enum Modification {
    STRENGTH("strength");

    private final String characteristic;

    Modification(String characteristic) {
        this.characteristic = characteristic;
    }

    @Override
    public String toString() {
        return characteristic;
    }
}
