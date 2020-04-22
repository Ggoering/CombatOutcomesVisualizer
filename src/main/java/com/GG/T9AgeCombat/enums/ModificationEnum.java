package com.GG.T9AgeCombat.enums;

public enum ModificationEnum {
    STRENGTH("strength"),
    TO_HIT("to hit"),
    EXTRA_RANKS("extra ranks"),
    RE_ROLL_TO_HIT("re-roll to hit");

    private final String value;

    ModificationEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {

        return value;
    }
}
