package com.example.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate; // ✅ Import important

import com.example.dao.GameLogDao;
import com.example.dto.GameLogDto;

@Service
public class IngestionService {

    private final GameLogDao gameLogDao;
    private final RestTemplate restTemplate; // ✅ L'outil de communication

    public IngestionService(GameLogDao gameLogDao) {
        this.gameLogDao = gameLogDao;
        this.restTemplate = new RestTemplate(); // On l'initialise
    }

    @Transactional
    public void receiveLog(GameLogDto logDto) {
        // 1. On sauvegarde le log brut (Archivage)
        gameLogDao.save(logDto);
        
        // 2. COMMUNICATION : On envoie le log à l'Analyzer (Microservice 3)
        String analyzerUrl = "http://localhost:8083/internal/analysis";
        
        try {
            // On fait un POST vers l'autre service
            restTemplate.postForObject(analyzerUrl, logDto, Void.class);
            System.out.println(">>> INGESTION : Log transmis à l'Analyzer.");
        } catch (Exception e) {
            // Si l'Analyzer est éteint, on ne veut pas faire planter l'Ingestion
            System.err.println("ERREUR : Impossible de contacter l'Analyzer. " + e.getMessage());
        }
    }
}