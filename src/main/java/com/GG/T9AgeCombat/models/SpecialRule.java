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
@Table(name = "special_rule", schema = "public", catalog = "T9AgeCombat")
public class SpecialRule {
    @Id
    long id;
    String name;
    int value;
    @ManyToOne
    @JoinColumn(name = "limitation_id", referencedColumnName = "id", nullable = false)
    Limitation limitationByLimitationId;
    @ManyToOne
    @JoinColumn(name = "modification_id", referencedColumnName = "id", nullable = false)
    Modification modificationByModificationId;
    @ManyToOne
    @JoinColumn(name = "timing_id", referencedColumnName = "id", nullable = false)
    Timing timingByTimingId;
}
