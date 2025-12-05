package com.example.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "alerts")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String level; // "WARNING" (Suspect) ou "CRITICAL" (Triche avérée)

    @Column(nullable = false)
    private String message; // "Tir impossible : Distance > Portée max"

    private String relatedPlayerId; // Le tricheur
    private LocalDateTime detectedAt;

    @PrePersist
    protected void onCreate() {
        this.detectedAt = LocalDateTime.now();
    }

    // Getters et Setters...
    public void setLevel(String level) { this.level = level; }
    public void setMessage(String message) { this.message = message; }
    public void setRelatedPlayerId(String id) { this.relatedPlayerId = id; }
    // ... ajoute les autres getters
}