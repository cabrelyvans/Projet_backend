package com.example.controller;

import org.springframework.web.bind.annotation.*;
import com.example.dto.GameLogDto;
import com.example.service.IngestionService;

import java.util.Map;

@RestController
@RequestMapping("/api/ingest")
public class IngestionController {

    private final IngestionService ingestionService;

    public IngestionController(IngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    @PostMapping
    public Map<String, String> ingestLog(@RequestBody GameLogDto logDto) {
        ingestionService.receiveLog(logDto);
        return Map.of("status", "RECEIVED");
    }
}