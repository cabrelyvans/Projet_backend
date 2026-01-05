-- Script d'initialisation des bases de données pour les microservices

-- Création de la base de données identity pour ProfilService
CREATE DATABASE IF NOT EXISTS identity CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Création de la base de données monitoring pour IngestionService et DetectionService
CREATE DATABASE IF NOT EXISTS monitoring CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Création de la base de données gestion_activite_db pour PilotageService
CREATE DATABASE IF NOT EXISTS gestion_activite_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Affichage des bases de données créées
SHOW DATABASES;
