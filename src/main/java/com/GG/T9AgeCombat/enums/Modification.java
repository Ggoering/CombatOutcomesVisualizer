package com.GG.T9AgeCombat.enums;

public enum Modification {
    STRENGTH("strength"),
    TO_HIT("to hit"),
    EXTRA_RANKS("extra ranks"),
    RE_ROLL_TO_HIT("re-roll to hit");


    private final String characteristic;

    Modification(String characteristic) {
        this.characteristic = characteristic;
    }

    @Override
    public String toString() {

        return characteristic;
    }
}
