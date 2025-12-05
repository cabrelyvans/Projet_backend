package com.example.controller;

import org.springframework.web.bind.annotation.*;
import com.example.dto.GameLogDto;
import com.example.service.DetectionService;

@RestController
@RequestMapping("/internal/analysis") // URL interne
public class InternalDetectionController {

    private final DetectionService detectionService;

    public InternalDetectionController(DetectionService detectionService) {
        this.detectionService = detectionService;
    }

    // Ce endpoint sera appelé par le Microservice 2
    @PostMapping
    public void analyzeIncomingLog(@RequestBody GameLogDto logDto) {
        System.out.println(">>> ANALYZER : Reçu un log de l'Ingestion. Analyse en cours...");
        detectionService.analyzeLog(logDto);
    }
}