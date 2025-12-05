package com.example.controller;

import org.springframework.web.bind.annotation.*;
import com.example.dto.BanRequestDto;
import com.example.dto.EnrichedAlertDto;
import com.example.service.PilotageService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pilotage")
public class PilotageController {

    private final PilotageService pilotageService;

    public PilotageController(PilotageService pilotageService) {
        this.pilotageService = pilotageService;
    }

    @GetMapping("/alerts")
    public List<EnrichedAlertDto> getDashboardAlerts() {
        return pilotageService.getEnrichedAlerts();
    }

    @PostMapping("/ban")
    public Map<String, String> banPlayer(@RequestBody BanRequestDto banRequest) {
        String result = pilotageService.executeBan(banRequest);
        return Map.of("status", result);
    }
}
