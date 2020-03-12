package com.GG.T9AgeCombat.service;
import com.GG.T9AgeCombat.models.Result;
import com.GG.T9AgeCombat.models.Round;
import com.GG.T9AgeCombat.models.Unit;
import org.springframework.stereotype.Service;
import com.GG.T9AgeCombat.enums.Identification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CombatCalculationService {
    AttackQuantityService attackQuantityService;
    public CombatCalculationService(AttackQuantityService attackQuantityService) {
        this.attackQuantityService = attackQuantityService;
    }

    Result combat(Unit primary, Unit secondary) {

        List<Round> round = this.fight(primary, secondary, false, Collections.emptyList());

        Result result = Result.builder().build();
        return result;
    }

    List<Round> fight(Unit primary, Unit secondary, Boolean brokenOrWipedOut, List<Round> rounds) {
        if (brokenOrWipedOut == true) {
            return rounds;
        }

        List<Unit> attackOrder = this.orderAttackers(primary, secondary);

        for (int i = 0; i < attackOrder.size(); i++) {
            Integer woundsDealt = attackQuantityService.determineAttackQuantity(attackOrder.get(i), primary, secondary);
        }

//        this.rollToHit(attacker, defender);
        return Collections.emptyList();
    }

    List<Unit> orderAttackers(Unit primary, Unit secondary) {

      List<Unit> list = new ArrayList<Unit>();
      list.add(primary);
      list.add(secondary);

      if(primary.getMountA() != null) {
          list.add(Unit.builder().A(primary.getMountA()).I(primary.getMountI()).S(primary.getMountS()).WS(primary.getMountWS()).name("mount").baseSize(primary.getBaseSize()).build());
      }
      if(secondary.getMountA() != null) {
          list.add(Unit.builder().A(secondary.getMountA()).I(secondary.getMountI()).S(secondary.getMountS()).WS(secondary.getMountWS()).name("mount").baseSize(secondary.getBaseSize()).build());
      }
       List<Unit> sortedCombatants = list.stream().sorted(Comparator.comparing(Unit::getI).reversed())
              .collect(toList());
        return sortedCombatants;
    }

}
