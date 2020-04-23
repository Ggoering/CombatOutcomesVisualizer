package com.GG.T9AgeCombat.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.*;

@Value
@Builder
@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Table(name = "unit_special_rule", schema = "public", catalog = "T9AgeCombat")
public class UnitSpecialRule {
    @Id
    long id;
    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "id", nullable = false)
    Unit unitByUnitId;
    @ManyToOne
    @JoinColumn(name = "special_rule_id", referencedColumnName = "id", nullable = false)
    SpecialRule specialRuleBySpecialRuleId;
}
