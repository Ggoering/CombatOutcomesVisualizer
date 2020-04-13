package com.GG.T9AgeCombat.enums;

import static com.GG.T9AgeCombat.enums.Limitation.FIRST_ROUND;
import static com.GG.T9AgeCombat.enums.Modification.STRENGTH;

public enum SpecialRules {
    BORN_TO_FIGHT("born to fight", Timing.ALL, STRENGTH, 1, FIRST_ROUND);

    private final String name;
    private final Timing timing;
    private final Modification modification;
    private final Integer value;
    private final Limitation limitation;

    SpecialRules(String name, Timing timing, Modification modification, Integer value, Limitation limitation) {
        this.name = name;
        this.timing = timing;
        this.modification = modification;
        this.value = value;
        this.limitation = limitation;
    }

    @Override
    public String toString() {
        return name;
    }

    public Integer getValue() {
        return value;
    }
}
