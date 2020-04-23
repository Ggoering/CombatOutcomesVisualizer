package com.GG.T9AgeCombat.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;

import javax.persistence.*;
import java.util.Collection;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Value
@Builder
@Entity
@JsonInclude(NON_NULL)
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Unit {
    public static final int SINGLE_WOUND_MODEL = 1;
    public static final Integer DEFAULT_REROLL_LESS_THAN = 0;
    public static final Integer DEFAULT_REROLL_GREATER_THAN = 99;

    @Id
    long id;
    String name;
    Integer movement;
    Integer offensiveWeaponSkill;
    Integer defensiveWeaponSkill;
    @NonFinal
    Integer strength;
    Integer toughness;
    int initiative;
    int wounds;
    Integer attacks;
    Integer leadership;
    Integer armorSave;
    Integer wardSave;
    int basesize;
    Boolean canHaveMusician;
    Boolean canHaveStandard;
    Integer equipmentPointLimit;
    @ManyToOne
    @JoinColumn(name = "faction_id", referencedColumnName = "id", nullable = false)
    Faction factionByFactionId;
    @ManyToOne
    @JoinColumn(name = "unit_type_id", referencedColumnName = "id", nullable = false)
    UnitType unitTypeByUnitTypeId;
    @OneToMany(mappedBy = "unitByUnitId")
    Collection<UnitEquipment> unitEquipmentsById;
    @OneToMany(mappedBy = "unitByUnitId")
    Collection<UnitMount> unitMountsById;
    @OneToMany(mappedBy = "unitByUnitId", fetch = FetchType.EAGER)
    Collection<UnitSpecialRule> unitSpecialRulesById;

    @Transient
    Integer mountWeaponSkill;
    @Transient
    Integer mountStrength;
    @Transient
    Integer mountAttacks;
    @Transient
    Integer mountWounds;
    @Transient
    Integer mountInitiative;
    @Transient
    Integer mountMovement;
    @Transient
    Integer standardBearer;
    @Transient
    boolean hasMusician;
    @Transient
    @NonFinal
    Integer selection;
    @Transient
    @NonFinal
    boolean isMount;
    @Transient
    @NonFinal
    int modelCount;
    @Transient
    @NonFinal
    int width;
    @Transient
    @NonFinal
    int pendingWounds;
    @Transient
    @NonFinal
    int woundTracker;
    @Transient
    @NonFinal
    @Builder.Default
    Integer reRollToHitLessThan = DEFAULT_REROLL_LESS_THAN;
    @Transient
    @NonFinal
    @Builder.Default
    Integer reRollToHitGreaterThan = DEFAULT_REROLL_GREATER_THAN;
    @Transient
    @NonFinal
    @Builder.Default
    Integer reRollToWoundLessThan = DEFAULT_REROLL_LESS_THAN;
    @Transient
    @NonFinal
    @Builder.Default
    Integer reRollToWoundGreaterThan = DEFAULT_REROLL_GREATER_THAN;
    @Transient
    @NonFinal
    @Builder.Default
    Integer reRollArmorSaveLessThan = DEFAULT_REROLL_LESS_THAN;
    @Transient
    @NonFinal
    @Builder.Default
    Integer reRollArmorSaveGreaterThan = DEFAULT_REROLL_GREATER_THAN;
    @Transient
    @NonFinal
    Boolean hasReRollLeadership;
    @Transient
    @NonFinal
    Integer toHitBonus;
    @Transient
    @NonFinal
    Integer extraRanks;

    public int getRankBonus() {
        return modelCount / width;
    }

    public int getActualWidth() {
        return modelCount >= width ? width * basesize : modelCount * basesize;
    }

    public void setPendingWounds(int pendingWounds) {
        this.pendingWounds += pendingWounds;
    }

    public void applyPendingWounds() {
        if (wounds == SINGLE_WOUND_MODEL) {
            modelCount = (pendingWounds < modelCount ? modelCount - pendingWounds : 0);
        } else { // Unit has multiple wounds
            // Have wounds from previous combat
            if (woundTracker > 0) {
                // Check to see if there are enough pending wounds to kill the wounded model
                if (woundTracker + pendingWounds >= wounds) {
                    // Subtract from the pending wounds the minimum number to kill the wounded model
                    pendingWounds -= wounds - woundTracker;
                    modelCount--;
                    adjustModelCountFromPendingWounds();
                } else {
                    woundTracker += pendingWounds;
                }
            } else {
                adjustModelCountFromPendingWounds();
            }
        }

        pendingWounds = 0;
    }

    private void adjustModelCountFromPendingWounds() {
        woundTracker = pendingWounds % wounds;
        int modelsKilled = (pendingWounds - woundTracker) / wounds;
        modelCount = (modelsKilled > modelCount ? 0 : modelCount - modelsKilled);

        if (modelCount == 0) {
            woundTracker = 0;
        }
    }

    public void updateStrength(Integer strength) {
        this.strength += strength;
    }

    public void updateReRollToHit(Integer reRollToHit) {
        this.reRollToHitLessThan = reRollToHit;
    }

    public void updateToHitBonus(Integer toHitBonus) {
        this.toHitBonus = this.toHitBonus + toHitBonus;
    }

    public void updateExtraRank(Integer extraRank) {
        this.extraRanks = this.extraRanks + extraRank;
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }
}
