package com.GG.T9AgeCombat.service.repository;

import com.GG.T9AgeCombat.models.Unit;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Service
public class UnitService {
    private final EntityManager entityManager;

    public UnitService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Unit retrieveUnit(int unitId) {
        Query q = entityManager.createNativeQuery("SELECT * FROM unit u WHERE u.id = " + unitId, Unit.class);
        return (Unit) q.getSingleResult();
    }
}
