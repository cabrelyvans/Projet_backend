package com.example.dao;

import org.springframework.stereotype.Repository;
import com.example.entity.Alert;
import com.example.entity.Sanction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PilotageDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Récupère les alertes CRITIQUES
     */
    public List<Alert> findCriticalAlerts() {
        // Requête JPQL
        return entityManager.createQuery(
            "SELECT a FROM Alert a WHERE a.level = :level ORDER BY a.detectedAt DESC", Alert.class)
            .setParameter("level", "CRITICAL")
            .getResultList();
    }

    /**
     * Sauvegarde une sanction
     */
    public void saveSanction(Sanction sanction) {
        entityManager.persist(sanction);
    }
}
