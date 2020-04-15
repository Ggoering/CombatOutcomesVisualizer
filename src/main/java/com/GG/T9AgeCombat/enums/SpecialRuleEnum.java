package com.GG.T9AgeCombat.enums;

public enum SpecialRuleEnum {
    BORN_TO_FIGHT("born to fight"),
    LIGHTNING_REFLEXES("lightning reflexes"),
    HORDE("horde"),
    HATRED("hatred");

    private final String value;

    SpecialRuleEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {

        return value;
    }
}
