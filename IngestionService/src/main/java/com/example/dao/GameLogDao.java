package com.example.dao;

import org.springframework.stereotype.Repository;
import com.example.dto.GameLogDto;
import com.example.entity.GameLog;
import com.example.util.DtoEntityUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class GameLogDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(GameLogDto logDto) {
        GameLog log = DtoEntityUtil.gameLogDtoToEntity(logDto);
        entityManager.persist(log);
    }
}