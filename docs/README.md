# Projet Deal - Minimal Spring Boot

## But

Ce projet est un exemple minimal pour apprendre Spring Boot avec une entité `Deal`, une couche service
avec règles métier simples, un contrôleur REST et la persistance via JPA/Postgres.

## Comment lancer

1. Configurez PostgreSQL et mettez vos identifiants dans `src/main/resources/application.yml`.
2. Lancer en mode développement:

```powershell
cd c:\Users\youco\Desktop\fx
mvn spring-boot:run
```

Ou construire et exécuter le JAR:

```powershell
mvn package
java -jar target\demo-0.0.1-SNAPSHOT.jar
```

## Exemple: POST /api/deals

URL: `POST http://localhost:8080/api/deals`

Exemple JSON:

```json
{
  "id": "d1",
  "fromCurrency": "EUR",
  "toCurrency": "USD",
  "timestamp": "2025-11-27T12:00:00",
  "amount": 100.5
}
```

PowerShell test rapide:

```powershell
$json = @{
  id = 'd1'
  fromCurrency = 'EUR'
  toCurrency = 'USD'
  timestamp = '2025-11-27T12:00:00'
  amount = 100.50
} | ConvertTo-Json

Invoke-RestMethod -Method Post -Uri http://localhost:8080/api/deals -Body $json -ContentType 'application/json'
```

## Remarques

- Les règles métier (devises différentes, unicité de l'id) sont implémentées dans la couche service.
- Replacez les credentials DB dans `src/main/resources/application.yml` avant de lancer.
