package com.example.dao;

import org.springframework.stereotype.Repository;
import com.example.entity.Alert;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class AlertDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Alert alert) {
        entityManager.persist(alert);
    }
}