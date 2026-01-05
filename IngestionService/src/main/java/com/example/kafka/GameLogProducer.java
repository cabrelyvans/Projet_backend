package com.example.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.dto.GameLogDto;

@Service
public class GameLogProducer {

    private final KafkaTemplate<String, GameLogDto> kafkaTemplate;

    @Value("${kafka.topic.game-logs}")
    private String topic;

    public GameLogProducer(KafkaTemplate<String, GameLogDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendGameLog(GameLogDto gameLog) {
        System.out.println(">>> KAFKA PRODUCER : Envoi du log vers le topic '" + topic + "'");
        kafkaTemplate.send(topic, gameLog.getPlayerId(), gameLog);
        System.out.println(">>> KAFKA PRODUCER : Log envoyé avec succès (playerId=" + gameLog.getPlayerId() + ")");
    }
}
