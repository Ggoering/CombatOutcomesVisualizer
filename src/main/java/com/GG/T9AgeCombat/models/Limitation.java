package com.GG.T9AgeCombat.models;

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
public class Limitation {
    @Id
    long id;
    String value;
}
