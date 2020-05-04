package com.GG.T9AgeCombat.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Value
@Builder
@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Table(name = "unit_height", schema = "public", catalog = "T9AgeCombat")
public class UnitHeight {
    @Id
    long id;
    String value;
}
