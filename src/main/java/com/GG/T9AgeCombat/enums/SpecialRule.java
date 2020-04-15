package com.GG.T9AgeCombat.enums;

import static com.GG.T9AgeCombat.enums.Limitation.*;
import static com.GG.T9AgeCombat.enums.Modification.*;
import static com.GG.T9AgeCombat.enums.Timing.*;

public enum SpecialRule {
    BORN_TO_FIGHT("born to fight", ALL, STRENGTH, 1, FIRST_ROUND),
    LIGHTNING_REFLEXES("lightning reflexes", ROLL_TO_HIT, TO_HIT,1, NONE),
    HORDE("horde", DETERMINE_ATTACK_QUANTITY, EXTRA_RANKS, 1, TEN_WIDE),
    HATRED("hatred", ROLL_TO_HIT, RE_ROLL_TO_HIT, 6, FIRST_ROUND);

    private final String name;
    private final Timing timing;
    private final Modification modification;
    private final Integer value;
    private final Limitation limitation;

    SpecialRule(String name, Timing timing, Modification modification, Integer value, Limitation limitation) {
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

    public Limitation getLimitation() {
        return limitation;
    }

    public Modification getModification() {
        return modification;
    }
}
