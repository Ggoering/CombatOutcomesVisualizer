package com.GG.T9AgeCombat.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;

@Value
@Builder
@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Table(name = "faction", schema = "public", catalog = "T9AgeCombat")
public class Faction {
    @Id
    long id;
    String name;
    @OneToMany(mappedBy = "factionByFactionId")
    Collection<Unit> unitsById;
}
