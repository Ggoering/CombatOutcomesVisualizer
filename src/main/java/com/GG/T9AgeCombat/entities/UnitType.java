//package com.GG.T9AgeCombat.entities;
//
//import com.GG.T9AgeCombat.entities.CharacterMount;
//import com.GG.T9AgeCombat.models.Unit;
//import com.GG.T9AgeCombat.models.UnitEntity;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.NoArgsConstructor;
//import lombok.Value;
//
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//import java.util.Collection;
//
//@Value
//@Builder
//@Entity
//@NoArgsConstructor(force = true)
//@AllArgsConstructor
//@Table(name = "unit_type", schema = "public", catalog = "T9AgeCombat")
//public class UnitType {
//    @Id
//    long id;
//    String type;
//    @OneToMany(mappedBy = "unitTypeByUnitTypeId")
//    Collection<CharacterMount> mountsById;
//    @OneToMany(mappedBy = "unitTypeByUnitTypeId")
//    Collection<UnitEntity> unitsById;
//}
