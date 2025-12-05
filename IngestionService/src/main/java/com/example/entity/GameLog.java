package com.example.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.PrePersist;

@Entity
@Table(name = "game_logs")
public class GameLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String source; // ex: "GAME_SERVER_1"

    @Column(nullable = false)
    private String playerId; // ID du joueur dans le jeu (String car externe)

    @Column(nullable = false)
    private String action; // ex: "SHOOT", "MOVE", "LOGIN"

    @Column(length = 1000) 
    private String metadata; // ex: "{ weapon: 'AK47', distance: 500 }" (JSON stocké en String)

    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }

    // Getters et Setters standard
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public String getPlayerId() { return playerId; }
    public void setPlayerId(String playerId) { this.playerId = playerId; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public String getMetadata() { return metadata; }
    public void setMetadata(String metadata) { this.metadata = metadata; }
    public LocalDateTime getTimestamp() { return timestamp; }
}