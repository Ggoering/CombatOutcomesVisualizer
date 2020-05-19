package com.GG.T9AgeCombat.service.repository;

import com.GG.T9AgeCombat.dto.UnitEquipmentSpecialRuleDTO;
import com.GG.T9AgeCombat.entities.PropertyEntity;
import com.GG.T9AgeCombat.entities.UnitEntity;
import com.GG.T9AgeCombat.models.Equipment;
import com.GG.T9AgeCombat.models.SpecialRuleProperty;
import com.GG.T9AgeCombat.models.Unit;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UnitService {
    private final EntityManager entityManager;
    private final SpecialRulePropertyService specialRulePropertyService;
    private final EquipmentService equipmentService;

    public UnitService(EntityManager entityManager
            , SpecialRulePropertyService specialRulePropertyService
            , EquipmentService equipmentService) {
        this.entityManager = entityManager;
        this.specialRulePropertyService = specialRulePropertyService;
        this.equipmentService = equipmentService;
    }

    public Unit retrieveUnit(int unitId) {
        UnitEntity unitAttributes = getUnitByUnitId(unitId);

        List<UnitEquipmentSpecialRuleDTO> unitEquipmentSpecialRuleDTOList = getEquipmentByUnitId(unitId);
        List<Equipment> equipmentList = equipmentService.buildEquipmentFromDTO(unitEquipmentSpecialRuleDTOList);

        // Build special rules inherent to the unit
        List<PropertyEntity> unitSpecialRuleProperties = getUnitSpecialRules(unitId);
        List<PropertyEntity> unitHeightSpecialRuleProperties = getUnitHeightSpecialRules(unitId);
        List<PropertyEntity> unitTypeSpecialRuleProperties = getUnitTypeSpecialRules(unitId);
        List<SpecialRuleProperty> unitSpecialRulePropertyList = specialRulePropertyService.convertToSpecialRuleProperty(unitSpecialRuleProperties);
        List<SpecialRuleProperty> unitHeightSpecialRulePropertyList = specialRulePropertyService.convertToSpecialRuleProperty(unitHeightSpecialRuleProperties);
        List<SpecialRuleProperty> unitTypeSpecialRulePropertyList = specialRulePropertyService.convertToSpecialRuleProperty(unitTypeSpecialRuleProperties);
        List<SpecialRuleProperty> specialRulePropertyList = Stream.of(unitSpecialRulePropertyList, unitHeightSpecialRulePropertyList,
                unitTypeSpecialRulePropertyList).flatMap(Collection::stream).collect(Collectors.toList());

        return Unit.builder()
                .id(unitAttributes.getId())
                .name(unitAttributes.getName())
                .movement(unitAttributes.getMovement())
                .leadership(unitAttributes.getLeadership())
                .wounds(unitAttributes.getWounds())
                .defensiveWeaponSkill(unitAttributes.getDefensiveWeaponSkill())
                .toughness(unitAttributes.getToughness())
                .armor(unitAttributes.getArmor())
                .initiative(unitAttributes.getInitiative())
                .offensiveWeaponSkill(unitAttributes.getOffensiveWeaponSkill())
                .attacks(unitAttributes.getAttacks())
                .strength(unitAttributes.getStrength())
                .armorPenetration(unitAttributes.getArmorPenetration())
                .mountInitiative(unitAttributes.getMountInitiative())
                .mountOffensiveWeaponSkill(unitAttributes.getMountOffensiveWeaponSkill())
                .mountAttacks(unitAttributes.getMountAttacks())
                .mountStrength(unitAttributes.getMountStrength())
                .mountArmorPenetration(unitAttributes.getMountArmorPenetration())
                .basesize(unitAttributes.getBasesize())
                .canHaveMusician(unitAttributes.getCanHaveMusician())
                .canHaveStandard(unitAttributes.getCanHaveStandard())
                .equipmentPointLimit(unitAttributes.getEquipmentPointLimit())
                .specialRulePropertyList(specialRulePropertyList)
                .equipmentList(equipmentList)
                .build();
    }

    public UnitEntity getUnitByUnitId(int unitId) {
        Query q = entityManager.createNativeQuery("SELECT  " +
                        "u.id \n" +
                        ",name \n" +
                        ",movement \n" +
                        ",leadership \n" +
                        ",wounds \n" +
                        ",defensive_weapon_skill \n" +
                        ",toughness \n" +
                        ",armor \n" +
                        ",initiative \n" +
                        ",offensive_weapon_skill \n" +
                        ",attacks \n" +
                        ",strength \n" +
                        ",armor_penetration \n" +
                        ",mount_initiative \n" +
                        ",mount_offensive_weapon_skill \n" +
                        ",mount_attacks \n" +
                        ",mount_strength \n" +
                        ",mount_armor_penetration \n" +
                        ",basesize \n" +
                        ",is_mounted \n" +
                        ",can_have_musician \n" +
                        ",can_have_standard \n" +
                        ",equipment_point_limit \n" +
                        ",uh.value AS unit_height \n" +
                        ",ut.value AS unit_type \n" +
                        ",f.value AS faction \n" +
                        "FROM unit u \n" +
                        "INNER JOIN unit_height uh ON uh.id = u.unit_height_id \n" +
                        "INNER JOIN unit_type ut ON u.unit_type_id = ut.id \n" +
                        "INNER JOIN faction f ON f.id = u.faction_id \n" +
                        "WHERE u.id = " + unitId
                , UnitEntity.class);
        return (UnitEntity) q.getSingleResult();
    }

    private List<PropertyEntity> getUnitSpecialRules(int unitId) {
        Query q = entityManager.createNativeQuery("SELECT\n" +
                        "p.id \n" +
                        ",p.name \n" +
                        ",m.value AS modification_value \n" +
                        ",l.value AS limitation_value \n" +
                        ",t.value AS timing_value \n" +
                        ",p.value \n" +
                        "FROM property p \n" +
                        "INNER JOIN special_rule_property srp ON p.id = srp.property_id\n" +
                        "INNER JOIN special_rule sr ON sr.id = srp.special_rule_id\n" +
                        "INNER JOIN unit_special_rule usr ON usr.special_rule_id = sr.id\n" +
                        "INNER JOIN modification m ON m.id = p.modification_id\n" +
                        "INNER JOIN limitation l ON l.id = p.limitation_id\n" +
                        "INNER JOIN timing t ON t.id = p.timing_id\n" +
                        "WHERE usr.unit_id = " + unitId
                , PropertyEntity.class);

        return q.getResultList();
    }

    private List<PropertyEntity> getUnitHeightSpecialRules(int unitId) {
        Query q = entityManager.createNativeQuery("SELECT\n" +
                        "p.id as id\n" +
                        ",p.name as name\n" +
                        ",m.value as modification_value\n" +
                        ",l.value as limitation_value\n" +
                        ",t.value as timing_value\n" +
                        ",p.value as value\n" +
                        "FROM property p \n" +
                        "INNER JOIN special_rule_property srp ON p.id = srp.property_id\n" +
                        "INNER JOIN special_rule sr ON sr.id = srp.special_rule_id\n" +
                        "INNER JOIN unit_height_special_rule uhsr ON uhsr.special_rule_id = sr.id\n" +
                        "INNER JOIN modification m ON m.id = p.modification_id\n" +
                        "INNER JOIN limitation l ON l.id = p.limitation_id\n" +
                        "INNER JOIN timing t ON t.id = p.timing_id\n" +
                        "INNER JOIN unit u on u.unit_height_id = uhsr.unit_height_id \n" +
                        "WHERE u.id = " + unitId
                , PropertyEntity.class);

        return q.getResultList();
    }

    private List<PropertyEntity> getUnitTypeSpecialRules(int unitId) {
        Query q = entityManager.createNativeQuery("SELECT\n" +
                        "p.id as id\n" +
                        ",p.name as name\n" +
                        ",m.value as modification_value\n" +
                        ",l.value as limitation_value\n" +
                        ",t.value as timing_value\n" +
                        ",p.value as value\n" +
                        "FROM property p \n" +
                        "INNER JOIN special_rule_property srp ON p.id = srp.property_id\n" +
                        "INNER JOIN special_rule sr ON sr.id = srp.special_rule_id\n" +
                        "INNER JOIN unit_type_special_rule utsr ON utsr.special_rule_id = sr.id\n" +
                        "INNER JOIN modification m ON m.id = p.modification_id\n" +
                        "INNER JOIN limitation l ON l.id = p.limitation_id\n" +
                        "INNER JOIN timing t ON t.id = p.timing_id\n" +
                        "INNER JOIN unit u on u.unit_type_id = utsr.unit_type_id \n" +
                        "WHERE u.id = " + unitId
                , PropertyEntity.class);

        return q.getResultList();
    }

    public List<UnitEquipmentSpecialRuleDTO> getEquipmentByUnitId(int unitId) {
        Query q = entityManager.createNativeQuery("SELECT " +
                        "eq.id \n" +
                        ",eq.name \n" +
                        ",eqt.type AS equipment_type \n" +
                        ",sr.name AS special_rule_name \n" +
                        ",m.value AS modification_value \n" +
                        ",l.value AS limitation_value \n" +
                        ",t.value AS timing_value \n" +
                        ",sr.value AS special_rule_value \n" +
                        "FROM equipment eq \n" +
                        "INNER JOIN unit_equipment ue ON ue.equipment_id = eq.id \n" +
                        "INNER JOIN equipment_type eqt ON eqt.id = eq.equipment_type_id \n" +
                        "INNER JOIN equipment_special_rule eqsr ON eqsr.equipment_id = eq.id \n" +
                        "INNER JOIN special_rule sr ON sr.id = eqsr.special_rule_id \n" +
                        "INNER JOIN modification m ON m.id = sr.modification_id \n" +
                        "INNER JOIN limitation l ON l.id = sr.limitation_id \n" +
                        "INNER JOIN timing t ON t.id = sr.timing_id \n" +
                        "WHERE ue.unit_id = " + unitId
                , UnitEquipmentSpecialRuleDTO.class);

        return q.getResultList();
    }
}
