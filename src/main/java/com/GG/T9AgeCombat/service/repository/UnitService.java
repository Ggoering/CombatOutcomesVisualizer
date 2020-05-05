package com.GG.T9AgeCombat.service.repository;

import com.GG.T9AgeCombat.entities.EquipmentDTO;
import com.GG.T9AgeCombat.models.Equipment;
import com.GG.T9AgeCombat.models.SpecialRule;
import com.GG.T9AgeCombat.entities.SpecialRuleEntity;
import com.GG.T9AgeCombat.models.Unit;
import com.GG.T9AgeCombat.entities.UnitEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
public class UnitService {
    private final EntityManager entityManager;
    private final SpecialRuleService specialRuleService;
    private final EquipmentService equipmentService;

    public UnitService(EntityManager entityManager
            , SpecialRuleService specialRuleService
            , EquipmentService equipmentService) {
        this.entityManager = entityManager;
        this.specialRuleService = specialRuleService;
        this.equipmentService = equipmentService;
    }


    public Unit retrieveUnit(int unitId) {
        UnitEntity unitAttributes = getUnitByUnitId(unitId);
        List<EquipmentDTO> equipmentDTOList = getEquipmentByUnitId(unitId);
        List<SpecialRuleEntity> specialRuleEntityList = getSpecialRuleByUnitId(unitId);
        List<SpecialRule> specialRuleList = specialRuleService.specialRuleFromDTO(specialRuleEntityList);
        List<Equipment> equipmentList = equipmentService.buildEquipmentFromDTO(equipmentDTOList);

        Unit unit = Unit.builder()
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
                .specialRuleList(specialRuleList)
                .equipmentList(equipmentList)
                .build();
        return unit;
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
                            ",uh.value as unit_height \n" +
                            ",ut.value as unit_type \n" +
                            ",f.value as faction \n" +
                            "FROM unit u \n" +
                            "INNER JOIN unit_height uh on uh.id = u.unit_height_id \n" +
                            "INNER JOIN unit_type ut on u.unit_type_id = ut.id \n" +
                            "INNER JOIN faction f on f.id = u.faction_id \n" +
                            "WHERE u.id = " + unitId + " \n "
                    ,UnitEntity.class);
            return (UnitEntity) q.getSingleResult();
        }


    public List<SpecialRuleEntity> getSpecialRuleByUnitId(int unitId) {
        Query q = entityManager.createNativeQuery("SELECT " +
                        "sr.id as id \n"+
                        ",sr.name as name \n"+
                        ",m.value as modification_value \n"+
                        ",l.value as limitation_value \n"+
                        ",t.value as timing_value \n"+
                        ",sr.value as value \n"+
                        "FROM special_rule sr \n"+
                        "INNER JOIN unit_special_rule usr \n"+
                        "ON usr.special_rule_id = sr.id \n"+
                        "INNER JOIN modification m ON m.id = sr.modification_id \n"+
                        "INNER JOIN limitation l ON l.id = sr.limitation_id \n"+
                        "INNER JOIN timing t ON t.id = sr.timing_id \n"+
                        "WHERE usr.unit_id = " + unitId + "  \n"
                , SpecialRuleEntity.class);

        return q.getResultList();
    }


    public List<EquipmentDTO> getEquipmentByUnitId(int unitId) {
        Query q = entityManager.createNativeQuery("SELECT " +
                        "eq.id as id \n"+
                        ",eq.name as name \n"+
                        ",eqt.type as equipment_type \n"+
                        ",sr.name as special_rule_name \n"+
                        ",m.value as modification_value \n"+
                        ",l.value as limitation_value \n"+
                        ",t.value as timing_value \n"+
                        ",sr.value as special_rule_value \n"+
                        "FROM equipment eq \n"+
                        "INNER JOIN unit_equipment ue \n"+
                        "ON ue.equipment_id = eq.id \n"+
                        "INNER JOIN equipment_type eqt \n"+
                        "ON eqt.id = eq.equipment_type_id \n"+
                        "INNER JOIN equipment_special_rule eqsr \n"+
                        "ON eqsr.equipment_id = eq.id \n"+
                        "INNER JOIN special_rule sr \n"+
                        "ON sr.id = eqsr.special_rule_id \n"+
                        "INNER JOIN modification m ON m.id = sr.modification_id \n"+
                        "INNER JOIN limitation l ON l.id = sr.limitation_id \n"+
                        "INNER JOIN timing t ON t.id = sr.timing_id \n"+
                        "WHERE ue.unit_id = " + unitId + " \n "
                , EquipmentDTO.class);

        return q.getResultList();
    }
}
