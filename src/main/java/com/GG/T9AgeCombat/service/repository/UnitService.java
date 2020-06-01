package com.GG.T9AgeCombat.service.repository;

import com.GG.T9AgeCombat.dto.UnitEquipmentSpecialRuleDTO;
import com.GG.T9AgeCombat.entities.PropertyEntity;
import com.GG.T9AgeCombat.entities.UnitDefensiveProfileEntity;
import com.GG.T9AgeCombat.entities.UnitEntity;
import com.GG.T9AgeCombat.entities.UnitOffensiveProfileEntity;
import com.GG.T9AgeCombat.enums.UnitHeightEnum;
import com.GG.T9AgeCombat.enums.UnitTypeEnum;
import com.GG.T9AgeCombat.models.Equipment;
import com.GG.T9AgeCombat.models.OffensiveProfile;
import com.GG.T9AgeCombat.models.SpecialRuleProperty;
import com.GG.T9AgeCombat.models.Unit;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
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
        // Get the unit
        UnitEntity unitAttributes = getUnitByUnitId(unitId);

        // Get the unit's defensive profile
        UnitDefensiveProfileEntity unitDefensiveProfileEntity = getUnitDefensiveProfileByUnitId(unitId);

        // Build a list of special rules inherent to the unit
        List<PropertyEntity> unitSpecialRuleProperties = getUnitProfileSpecialRules(unitId, true);
        List<PropertyEntity> unitHeightSpecialRuleProperties = getUnitHeightSpecialRules(unitId);
        List<PropertyEntity> unitTypeSpecialRuleProperties = getUnitTypeSpecialRules(unitId);
        List<SpecialRuleProperty> unitSpecialRulePropertyList = specialRulePropertyService.convertToSpecialRuleProperty(unitSpecialRuleProperties);
        List<SpecialRuleProperty> unitHeightSpecialRulePropertyList = specialRulePropertyService.convertToSpecialRuleProperty(unitHeightSpecialRuleProperties);
        List<SpecialRuleProperty> unitTypeSpecialRulePropertyList = specialRulePropertyService.convertToSpecialRuleProperty(unitTypeSpecialRuleProperties);
        List<SpecialRuleProperty> specialRulePropertyList = Stream.of(unitSpecialRulePropertyList, unitHeightSpecialRulePropertyList,
                unitTypeSpecialRulePropertyList).flatMap(Collection::stream).collect(Collectors.toList());

        List<Equipment> equipmentList = retrieveEquipment(unitId, true);

        // Build the unit and get a list of the unit's offensive profiles
        return Unit.builder()
                .id(unitAttributes.getId())
                .faction(unitAttributes.getFaction())
                .height(UnitHeightEnum.valueOf(unitAttributes.getUnitHeight()))
                .type(UnitTypeEnum.valueOf(unitAttributes.getUnitType()))
                .name(unitAttributes.getName())
                .advance(unitAttributes.getAdvance())
                .march(unitAttributes.getMarch())
                .leadership(unitAttributes.getLeadership())
                .wounds(unitDefensiveProfileEntity.getWounds())
                .defensiveWeaponSkill(unitDefensiveProfileEntity.getDefensiveWeaponSkill())
                .toughness(unitDefensiveProfileEntity.getToughness())
                .armor(unitDefensiveProfileEntity.getArmor())
                .basesize(unitAttributes.getBasesize())
                .canHaveMusician(unitAttributes.isCanHaveMusician())
                .canHaveStandard(unitAttributes.isCanHaveStandard())
                .specialRulePropertyList(specialRulePropertyList)
                .equipmentList(equipmentList)
                .offensiveProfileList(retrieveOffensiveProfiles(unitId))
                .pointCost(unitAttributes.getPointCost())
                .extraModelPointCost(unitAttributes.getExtraModelPointCost())
                .defaultModelCount(unitAttributes.getDefaultModelCount())
                .maximumModelCount(unitAttributes.getMaximumModelCount())
                .modelCount(unitAttributes.getDefaultModelCount())
                .build();
    }

    private List<OffensiveProfile> retrieveOffensiveProfiles(int unitId) {
        // Get a list of the offensive profiles for the unit
        List<OffensiveProfile> offensiveProfileList = new ArrayList<>();
        List<UnitOffensiveProfileEntity> unitOffensiveProfileEntityList = getUnitOffensiveProfileByUnitId(unitId);

        for (UnitOffensiveProfileEntity unitOffensiveProfileEntity : unitOffensiveProfileEntityList) {
            List<Equipment> equipmentList = retrieveEquipment(unitId, false);

            // Get a list of the special rules for the offensive profile
            List<PropertyEntity> unitSpecialRuleProperties = getUnitProfileSpecialRules((int) unitOffensiveProfileEntity.getId(), false);
            List<SpecialRuleProperty> specialRulePropertyList = specialRulePropertyService.convertToSpecialRuleProperty(unitSpecialRuleProperties);

            // Build the offensive profile
            offensiveProfileList.add(OffensiveProfile.builder()
                    .name(unitOffensiveProfileEntity.getName())
                    .attacks(unitOffensiveProfileEntity.getAttacks())
                    .offensiveWeaponSkill(unitOffensiveProfileEntity.getOffensiveWeaponSkill())
                    .strength(unitOffensiveProfileEntity.getStrength())
                    .armorPenetration(unitOffensiveProfileEntity.getArmorPenetration())
                    .initiative(unitOffensiveProfileEntity.getInitiative())
                    .specialRulePropertyList(specialRulePropertyList)
                    .equipmentList(equipmentList)
                    .build());
        }

        return offensiveProfileList;
    }

    private List<Equipment> retrieveEquipment(int profileId, boolean isUnitProfile) {
        // Get the unit's defensive equipment
        List<UnitEquipmentSpecialRuleDTO> unitEquipmentSpecialRuleDTOList = getEquipmentByUnitProfileId(profileId, isUnitProfile);
        List<Equipment> equipmentList = equipmentService.buildEquipmentFromDTO(unitEquipmentSpecialRuleDTOList);

        for (Equipment equipment : equipmentList) {
            List<SpecialRuleProperty> equipmentSpecialRulePropertyList = equipment.getSpecialRuleProperties();
            equipmentSpecialRulePropertyList.addAll(
                    specialRulePropertyService.convertToSpecialRuleProperty(getEquipmentCategorySpecialRules(equipment.getId())));
            equipment.setSpecialRuleProperties(equipmentSpecialRulePropertyList);
        }

        return equipmentList;
    }

    private UnitEntity getUnitByUnitId(int unitId) {
        Query q = entityManager.createNativeQuery("SELECT\n" +
                        "u.id \n" +
                        ",f.value AS faction \n" +
                        ",uh.value AS unit_height \n" +
                        ",ut.value AS unit_type \n" +
                        ",u.name \n" +
                        ",u.advance \n" +
                        ",u.march \n" +
                        ",u.leadership \n" +
                        ",u.basesize \n" +
                        ",u.can_have_musician \n" +
                        ",u.can_have_standard \n" +
                        ",u.point_cost \n" +
                        ",u.extra_model_point_cost \n" +
                        ",u.default_model_count \n" +
                        ",u.maximum_model_count \n" +
                        "FROM unit u \n" +
                        "INNER JOIN unit_height uh ON uh.id = u.unit_height_id \n" +
                        "INNER JOIN unit_type ut ON u.unit_type_id = ut.id \n" +
                        "INNER JOIN faction f ON f.id = u.faction_id \n" +
                        "WHERE u.id = " + unitId
                , UnitEntity.class);
        return (UnitEntity) q.getSingleResult();
    }

    private UnitDefensiveProfileEntity getUnitDefensiveProfileByUnitId(int unitId) {
        Query q = entityManager.createNativeQuery("SELECT\n" +
                        "id \n" +
                        ",wounds \n" +
                        ",defensive_weapon_skill \n" +
                        ",toughness \n" +
                        ",armor \n" +
                        "FROM unit_defensive_profile \n" +
                        "WHERE unit_defensive_profile.unit_id = " + unitId
                , UnitDefensiveProfileEntity.class);
        return (UnitDefensiveProfileEntity) q.getSingleResult();
    }

    private List<UnitOffensiveProfileEntity> getUnitOffensiveProfileByUnitId(int unitId) {
        Query q = entityManager.createNativeQuery("SELECT\n" +
                        "id \n" +
                        ",name \n" +
                        ",attacks \n" +
                        ",offensive_weapon_skill \n" +
                        ",strength \n" +
                        ",armor_penetration \n" +
                        ",initiative \n" +
                        "FROM unit_offensive_profile \n" +
                        "WHERE unit_id = " + unitId
                , UnitOffensiveProfileEntity.class);
        return q.getResultList();
    }

    private List<PropertyEntity> getUnitProfileSpecialRules(int unitProfileId, boolean isUnitProfile) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT\n" +
                "p.id \n" +
                ",p.name \n" +
                ",m.value AS modification_value \n" +
                ",l.value AS limitation_value \n" +
                ",t.value AS timing_value \n" +
                ",p.value \n" +
                "FROM property p \n" +
                "INNER JOIN special_rule_property srp ON p.id = srp.property_id\n" +
                "INNER JOIN special_rule sr ON sr.id = srp.special_rule_id\n" +
                "INNER JOIN unit_profile_special_rule upsr ON upsr.special_rule_id = sr.id\n" +
                "INNER JOIN modification m ON m.id = p.modification_id\n" +
                "INNER JOIN limitation l ON l.id = p.limitation_id\n" +
                "INNER JOIN timing t ON t.id = p.timing_id\n");

        if (isUnitProfile) {
            query.append("WHERE upsr.unit_id = ");
        } else {
            query.append("WHERE upsr.unit_offensive_profile_id = ");
        }

        query.append(unitProfileId);

        Query q = entityManager.createNativeQuery(query.toString(), PropertyEntity.class);

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

    private List<UnitEquipmentSpecialRuleDTO> getEquipmentByUnitProfileId(int unitProfileId, boolean isUnitProfile) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT eq.id \n" +
                ",eq.name \n" +
                ",eqcl.classification AS equipment_classification \n" +
                ",eqt.type AS equipment_type \n" +
                ",eqct.category AS equipment_category \n" +
                ",upe.is_default \n" +
                ",p.name AS special_rule_name \n" +
                ",m.value AS modification_value \n" +
                ",l.value AS limitation_value \n" +
                ",t.value AS timing_value \n" +
                ",p.value AS special_rule_value \n" +
                "FROM equipment eq \n" +
                "INNER JOIN unit_profile_equipment upe ON upe.equipment_id = eq.id \n" +
                "INNER JOIN equipment_classification eqcl ON eqcl.id = eq.equipment_classification_id \n" +
                "INNER JOIN equipment_type eqt ON eqt.id = eq.equipment_type_id \n" +
                "INNER JOIN equipment_category eqct ON eqct.id = eq.equipment_category_id \n" +
                "LEFT JOIN equipment_special_rule eqsr ON eqsr.equipment_id = eq.id \n" +
                "LEFT JOIN special_rule sr ON sr.id = eqsr.special_rule_id \n" +
                "LEFT JOIN special_rule_property srp ON srp.special_rule_id = sr.id \n" +
                "LEFT JOIN property p ON p.id = srp.property_id \n" +
                "LEFT JOIN modification m ON m.id = p.modification_id \n" +
                "LEFT JOIN limitation l ON l.id = p.limitation_id \n" +
                "LEFT JOIN timing t ON t.id = p.timing_id \n");

        if (isUnitProfile) {
            query.append("WHERE upe.unit_id = ");
        } else {
            query.append("WHERE upe.unit_offensive_profile_id = ");
        }

        query.append(unitProfileId);

        Query q = entityManager.createNativeQuery(query.toString(), UnitEquipmentSpecialRuleDTO.class);

        return q.getResultList();
    }

    private List<PropertyEntity> getEquipmentCategorySpecialRules(int equipmentId) {
        Query q = entityManager.createNativeQuery("SELECT\n" +
                        "p.id as id\n" +
                        ",p.name as name\n" +
                        ",m.value as modification_value\n" +
                        ",l.value as limitation_value\n" +
                        ",t.value as timing_value\n" +
                        ",p.value as value\n" +
                        "FROM equipment eq\n" +
                        "inner join equipment_category eqc on eqc.id = eq.equipment_category_id \n" +
                        "inner join equipment_special_rule eqsr on eqsr.equipment_category_id = eqc.id\n" +
                        "inner join special_rule sr on sr.id = eqsr.special_rule_id \n" +
                        "INNER JOIN special_rule_property srp ON sr.id = srp.special_rule_id \n" +
                        "inner join property p on p.id = srp.property_id \n" +
                        "INNER JOIN modification m ON m.id = p.modification_id\n" +
                        "INNER JOIN limitation l ON l.id = p.limitation_id\n" +
                        "INNER JOIN timing t ON t.id = p.timing_id\n" +
                        "WHERE eq.id = " + equipmentId
                , PropertyEntity.class);

        return q.getResultList();
    }
}
