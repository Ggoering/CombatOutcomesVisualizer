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
@Table(name = "unit_mount", schema = "public", catalog = "T9AgeCombat")
public class UnitMount {
    @Id
    long id;
    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "id", nullable = false)
    Unit unitByUnitId;
    @ManyToOne
    @JoinColumn(name = "mount_id", referencedColumnName = "id", nullable = false)
    Mount mountByMountId;
}
