# Script PowerShell pour tester Kafka automatiquement
# Usage: .\test-kafka.ps1

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Test Automatique Kafka - Anti-Cheat  " -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Configuration
$BASE_URL = "http://localhost"
$TOKEN = ""

# Fonction pour faire une requête
function Invoke-ApiRequest {
    param(
        [string]$Method,
        [string]$Url,
        [string]$Body = $null,
        [hashtable]$Headers = @{}
    )

    try {
        $params = @{
            Method = $Method
            Uri = $Url
            Headers = $Headers
            ContentType = "application/json"
        }

        if ($Body) {
            $params.Body = $Body
        }

        $response = Invoke-RestMethod @params
        return $response
    }
    catch {
        Write-Host "Erreur: $_" -ForegroundColor Red
        return $null
    }
}

# Étape 1: Authentification
Write-Host "[1/5] Authentification..." -ForegroundColor Yellow
$loginUrl = "$BASE_URL`:8080/auth/login"
$loginBody = "username=admin"
$loginHeaders = @{
    "Content-Type" = "application/x-www-form-urlencoded"
}

try {
    $authResponse = Invoke-RestMethod -Method Post -Uri $loginUrl -Body $loginBody -ContentType "application/x-www-form-urlencoded"
    $TOKEN = $authResponse.token
    Write-Host "✓ Token obtenu: $($TOKEN.Substring(0,20))..." -ForegroundColor Green
}
catch {
    Write-Host "✗ Échec de l'authentification" -ForegroundColor Red
    exit 1
}

$authHeaders = @{
    "Authorization" = "Bearer $TOKEN"
    "Content-Type" = "application/json"
}

# Étape 2: Créer des profils
Write-Host "`n[2/5] Création des profils..." -ForegroundColor Yellow

$profiles = @("LegitPlayer", "SuspectPlayer", "CheaterPro")
foreach ($name in $profiles) {
    $body = @{ name = $name } | ConvertTo-Json
    $result = Invoke-ApiRequest -Method "POST" -Url "$BASE_URL`:8080/api/profils" -Body $body -Headers $authHeaders
    if ($result) {
        Write-Host "✓ Profil créé: $name (ID: $($result.id))" -ForegroundColor Green
    }
}

# Étape 3: Envoyer des logs NORMAUX
Write-Host "`n[3/5] Envoi de 5 logs normaux..." -ForegroundColor Yellow

$normalLogs = @(
    @{ source="game-server-01"; playerId="1"; action="KILL"; metadata="weapon:RIFLE,distance:25" },
    @{ source="game-server-01"; playerId="2"; action="KILL"; metadata="weapon:PISTOL,distance:15" },
    @{ source="game-server-02"; playerId="1"; action="KILL"; metadata="weapon:KNIFE,distance:2" },
    @{ source="game-server-01"; playerId="2"; action="KILL"; metadata="weapon:SNIPER,distance:150" },
    @{ source="game-server-03"; playerId="1"; action="KILL"; metadata="weapon:SHOTGUN,distance:8" }
)

$count = 0
foreach ($log in $normalLogs) {
    $body = $log | ConvertTo-Json
    $result = Invoke-ApiRequest -Method "POST" -Url "$BASE_URL`:8082/api/ingest" -Body $body -Headers $authHeaders
    if ($result) {
        $count++
        Write-Host "  → Log $count/$($normalLogs.Count) envoyé" -ForegroundColor Gray
    }
    Start-Sleep -Milliseconds 200
}
Write-Host "✓ $count logs normaux envoyés" -ForegroundColor Green

# Étape 4: Envoyer des logs SUSPECTS
Write-Host "`n[4/5] Envoi de 5 logs suspects (alertes attendues)..." -ForegroundColor Yellow

$suspectLogs = @(
    @{ source="game-server-01"; playerId="3"; action="KILL"; metadata="weapon:KNIFE,distance:50" },
    @{ source="game-server-02"; playerId="3"; action="KILL"; metadata="weapon:KNIFE,distance:100" },
    @{ source="game-server-01"; playerId="3"; action="KILL"; metadata="weapon:KNIFE,distance:75" },
    @{ source="game-server-03"; playerId="3"; action="KILL"; metadata="weapon:KNIFE,distance:120" },
    @{ source="game-server-01"; playerId="3"; action="KILL"; metadata="weapon:KNIFE,distance:95" }
)

$count = 0
foreach ($log in $suspectLogs) {
    $body = $log | ConvertTo-Json
    $result = Invoke-ApiRequest -Method "POST" -Url "$BASE_URL`:8082/api/ingest" -Body $body -Headers $authHeaders
    if ($result) {
        $count++
        Write-Host "  → Log suspect $count/$($suspectLogs.Count) envoyé (distance: $($log.metadata.Split(',')[1]))" -ForegroundColor Red
    }
    Start-Sleep -Milliseconds 200
}
Write-Host "✓ $count logs suspects envoyés" -ForegroundColor Green

# Attendre que Kafka traite
Write-Host "`nAttente du traitement par Kafka..." -ForegroundColor Cyan
Start-Sleep -Seconds 3

# Étape 5: Vérifier les alertes
Write-Host "`n[5/5] Vérification des alertes..." -ForegroundColor Yellow

$alerts = Invoke-ApiRequest -Method "GET" -Url "$BASE_URL`:8081/api/pilotage/alerts" -Headers $authHeaders

if ($alerts) {
    Write-Host "✓ $($alerts.Count) alertes détectées:" -ForegroundColor Green
    foreach ($alert in $alerts) {
        Write-Host "  - Joueur $($alert.playerId): $($alert.message)" -ForegroundColor Yellow
    }
}
else {
    Write-Host "✗ Aucune alerte récupérée" -ForegroundColor Red
}

# Résumé
Write-Host "`n========================================" -ForegroundColor Cyan
Write-Host "           RÉSUMÉ DES TESTS            " -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Logs normaux envoyés:  5" -ForegroundColor White
Write-Host "Logs suspects envoyés: 5" -ForegroundColor White
Write-Host "Alertes créées:        $($alerts.Count)" -ForegroundColor $(if($alerts.Count -eq 5){"Green"}else{"Red"})
Write-Host "`nOuvrez Kafka UI: http://localhost:8090" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
