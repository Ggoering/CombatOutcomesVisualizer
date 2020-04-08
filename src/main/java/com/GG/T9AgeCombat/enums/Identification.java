package com.GG.T9AgeCombat.enums;

public enum Identification {
    MOUNT("mount"),
    SWORD_MASTER("sword master"),
    BLACK_ORC("black orc");

    private final String value;

    Identification(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
