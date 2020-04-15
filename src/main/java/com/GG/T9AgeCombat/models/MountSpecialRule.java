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
@Table(name = "mount_special_rule", schema = "public", catalog = "T9AgeCombat")
public class MountSpecialRule {
    @Id
    long id;
    @ManyToOne
    @JoinColumn(name = "mount_id", referencedColumnName = "id", nullable = false)
    Mount mountByMountId;
    @ManyToOne
    @JoinColumn(name = "special_rule_id", referencedColumnName = "id", nullable = false)
    SpecialRule specialRuleBySpecialRuleId;
}
