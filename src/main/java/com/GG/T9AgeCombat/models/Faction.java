package com.GG.T9AgeCombat.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;

@Value
@Builder
@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Faction {
    @Id
    long id;
    String name;
    @OneToMany(mappedBy = "factionByFactionId")
    Collection<Unit> unitsById;
}
