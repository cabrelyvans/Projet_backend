package com.example.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.example.dao.PilotageDao;
import com.example.dto.BanRequestDto;
import com.example.dto.EnrichedAlertDto;
import com.example.dto.PlayerProfileDto;
import com.example.entity.Alert;
import com.example.entity.Sanction;

import java.util.ArrayList;
import java.util.List;

@Service
public class PilotageService {

    private final PilotageDao pilotageDao;
    private final RestTemplate restTemplate;

    public PilotageService(PilotageDao pilotageDao) {
        this.pilotageDao = pilotageDao;
        this.restTemplate = new RestTemplate(); // Pour appeler les autres services (Profil & Jeu)
    }

    /**
     * Récupère les alertes et va chercher les pseudos dans le service Profil
     */
    public List<EnrichedAlertDto> getEnrichedAlerts() {
        List<Alert> rawAlerts = pilotageDao.findCriticalAlerts();
        List<EnrichedAlertDto> result = new ArrayList<>();

        for (Alert alert : rawAlerts) {
            EnrichedAlertDto dto = new EnrichedAlertDto();
            dto.setLevel(alert.getLevel());
            dto.setMessage(alert.getMessage());
            dto.setDate(alert.getDetectedAt());
            
            // Conversion String -> Long pour l'ID
            try {
                Long pId = Long.parseLong(alert.getRelatedPlayerId());
                dto.setPlayerId(pId);
                
                // --- APPEL EXTERNE VERS SERVICE PROFIL (Port 8081) ---
                String profilUrl = "http://localhost:8081/api/profils/" + pId;
                try {
                    PlayerProfileDto profile = restTemplate.getForObject(profilUrl, PlayerProfileDto.class);
                    if (profile != null) {
                        dto.setPlayerName(profile.getName());
                    } else {
                        dto.setPlayerName("Inconnu");
                    }
                } catch (Exception e) {
                    dto.setPlayerName("Service Profil Inaccessible");
                }
                
            } catch (NumberFormatException e) {
                dto.setPlayerName("ID Invalide");
            }

            result.add(dto);
        }
        return result;
    }

    /**
     * Exécute le bannissement
     */
    @Transactional
    public String executeBan(BanRequestDto request) {
        // 1. Sauvegarde en base (Audit)
        Sanction sanction = new Sanction();
        sanction.setPlayerId(request.getPlayerId());
        sanction.setReason(request.getReason());
        sanction.setAdminUser(request.getAdminUsername());

        pilotageDao.saveSanction(sanction);

        // 2. Appel Webhook (Simulation) vers le jeu
        System.out.println(">>> PILOTAGE : Ordre de BAN envoyé pour " + request.getPlayerId());
        
        return "SUCCESS";
    }
}
