#!/bin/bash
echo "▶️ Compilazione del backend..."
cd backend || exit 1
./mvnw clean install -DskipTests || { echo "❌ Compilazione fallita"; exit 1; }
cd ..
echo "🐳 Avvio dei container: PostgreSQL + Keycloak..."
docker compose up --build