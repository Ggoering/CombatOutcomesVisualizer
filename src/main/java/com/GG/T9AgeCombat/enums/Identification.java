package com.GG.T9AgeCombat.enums;

public enum Identification {
    PRIMARY_UNITS_MOUNT("primary_mount"),
    SECONDARY_UNITS_MOUNT("secondary_mount");

    private final String value;

    Identification(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
