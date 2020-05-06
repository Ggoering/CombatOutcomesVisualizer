package com.GG.T9AgeCombat.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.Entity;
import javax.persistence.Id;

@Value
@Builder
@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
//@Table(name = "character_mount_special_rule", schema = "public", catalog = "T9AgeCombat")
public class CharacterMountSpecialRule {
    @Id
    long id;
//    @ManyToOne
//    @JoinColumn(name = "character_mount_id", referencedColumnName = "id", nullable = false)
//    CharacterMountEntity characterMountByCharacterMountEntityId;
//    @ManyToOne
//    @JoinColumn(name = "special_rule_id", referencedColumnName = "id", nullable = false)
//    SpecialRuleEntity specialRuleBySpecialRuleId;
}
