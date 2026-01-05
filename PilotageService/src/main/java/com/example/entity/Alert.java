package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "profil")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false)
    private String name;

    private String level;
    private String message;
    private LocalDateTime detectedAt;
    private String relatedPlayerId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getDetectedAt() {
		return detectedAt;
	}

	public void setDetectedAt(LocalDateTime detectedAt) {
		this.detectedAt = detectedAt;
	}

	public String getRelatedPlayerId() {
		return relatedPlayerId;
	}

	public void setRelatedPlayerId(String relatedPlayerId) {
		this.relatedPlayerId = relatedPlayerId;
	}

}
