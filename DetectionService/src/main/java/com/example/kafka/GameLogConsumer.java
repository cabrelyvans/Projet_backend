package com.example.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.dto.GameLogDto;
import com.example.service.DetectionService;

@Service
public class GameLogConsumer {

    private final DetectionService detectionService;

    public GameLogConsumer(DetectionService detectionService) {
        this.detectionService = detectionService;
    }

    @KafkaListener(topics = "${kafka.topic.game-logs}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeGameLog(GameLogDto gameLog) {
        System.out.println(">>> KAFKA CONSUMER : Réception d'un log depuis Kafka (playerId=" + gameLog.getPlayerId() + ")");

        try {
            detectionService.analyzeLog(gameLog);
            System.out.println(">>> KAFKA CONSUMER : Log analysé avec succès.");
        } catch (Exception e) {
            System.err.println("ERREUR : Échec de l'analyse du log. " + e.getMessage());
        }
    }
}
