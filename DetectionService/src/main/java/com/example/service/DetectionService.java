package com.example.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.dao.AlertDao;
import com.example.dto.GameLogDto; // On réutilise le DTO d'ingestion
import com.example.entity.Alert;

@Service
public class DetectionService {

    private final AlertDao alertDao;

    public DetectionService(AlertDao alertDao) {
        this.alertDao = alertDao;
    }

    @Transactional
    public void analyzeLog(GameLogDto log) {
        
        // --- REGLE 1 : CHECK WEAPON RANGE ---
        // Le log contient des métadonnées JSON (ex: "weapon:KNIFE,distance:50")
        // Pour faire simple ici, on suppose qu'on a extrait les valeurs
        
        String weapon = extractValueFromMetadata(log.getMetadata(), "weapon");
        int distance = Integer.parseInt(extractValueFromMetadata(log.getMetadata(), "distance"));

        if ("KNIFE".equals(weapon) && distance > 5) {
            createAlert(log.getPlayerId(), "CRITICAL", "Attaque au couteau impossible (Distance: " + distance + "m)");
        }
        
        // --- REGLE 2 : CHECK SPEED (Exemple) ---
        // if (speed > 100) ...
    }

    private void createAlert(String playerId, String level, String msg) {
        Alert alert = new Alert();
        alert.setRelatedPlayerId(playerId);
        alert.setLevel(level);
        alert.setMessage(msg);
        alertDao.save(alert);
        System.out.println("ALERTE DÉTECTÉE : " + msg);
    }

    // Méthode utilitaire bidon pour l'exemple (à remplacer par un vrai parser JSON)
    private String extractValueFromMetadata(String meta, String key) {
        // Simulation : retourne une valeur par défaut pour tester
        if (key.equals("distance")) return "100"; 
        if (key.equals("weapon")) return "KNIFE";
        return "";
    }
}