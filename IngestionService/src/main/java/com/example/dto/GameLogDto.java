package com.example.dto;

public class GameLogDto {

    private String source;
    private String playerId;
    private String action;
    private String metadata;

    public GameLogDto() {}

    // Getters et Setters
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public String getPlayerId() { return playerId; }
    public void setPlayerId(String playerId) { this.playerId = playerId; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public String getMetadata() { return metadata; }
    public void setMetadata(String metadata) { this.metadata = metadata; }
}