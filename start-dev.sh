#!/bin/bash

# Costruisce il jar Spring Boot
cd backend || exit
./mvnw clean package -DskipTests
cd ..

# Avvia tutto tramite docker compose
docker compose up --build
