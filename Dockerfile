# syntax=docker/dockerfile:1
# Stage 1 : Build de l'application avec Gradle
FROM eclipse-temurin:25-jdk-alpine AS builder
LABEL authors="FONTAINE Théo"
WORKDIR /build

# Copier les fichiers Gradle
COPY gradle gradle/
COPY gradlew gradlew.bat ./

# Copier le code source et build.gradle
COPY build.gradle settings.gradle ./
COPY src src/

# Builder l'application
RUN chmod +x gradlew && \
    ./gradlew build -x test --no-daemon

# Stage 2 : Image finale légère
FROM eclipse-temurin:25-jre-alpine
ARG UID=10001

# Créer un utilisateur non-root
RUN adduser -D -u ${UID} appuser

WORKDIR /app
USER appuser

# Copier le JAR depuis le builder
COPY --from=builder --chown=appuser:appuser /build/build/libs/*-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
