package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.models.Unit;
import org.springframework.stereotype.Service;

@Service
public class BaseContactService {
    public BaseContactService(){}

    Integer determineActualWidth(Unit unit) {
        Integer unitActualWidth = unit.getCount() >= unit.getWidth() ? unit.getWidth() * unit.getBaseSize() : unit.getCount() * unit.getBaseSize();

        return unitActualWidth;
    }

    Integer determineAttackQuantity(Unit attacker, Unit defender, Integer primaryActualWidth, Integer secondaryActualWidth) {
        Integer attacks;

        if (attacker.getCount() >= attacker.getWidth() * 2) {
            attacks = attacker.getWidth() * attacker.getA() + attacker.getWidth();
        } else if (attacker.getCount() >= attacker.getWidth()) {
            attacks = attacker.getWidth() * attacker.getA() + attacker.getCount() - attacker.getWidth();
        } else {
            attacks = attacker.getCount() * attacker.getA();
        }

        return attacks;
    }

    Integer determineModelsNotInBaseContact(Integer primaryActualWidth, Integer secondaryActualWidth) {

    }
}
