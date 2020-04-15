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
    Integer wounds;
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
    Integer reRollToHitLessThan;
    @Transient
    @NonFinal
    Boolean hasReRollToWound;
    @Transient
    @NonFinal
    Boolean hasReRollArmorSave;
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
        modelCount = (pendingWounds < modelCount ? modelCount - pendingWounds : 0);
        pendingWounds = 0;
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
