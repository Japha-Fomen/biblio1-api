# ============================================================
#  biblio1-api - image Docker en deux etapes
#  Etape 1 : on compile avec Maven et le JDK complet
#  Etape 2 : on ne garde que le JAR et un JRE leger
# ============================================================

# ---------- Etape 1 : construction ----------
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# On copie d'abord le pom seul : tant qu'il ne change pas,
# Docker reutilise le telechargement des dependances (cache).
COPY biblio1-api/pom.xml .
RUN mvn -B dependency:go-offline

# Puis le code source, qui change beaucoup plus souvent.
COPY biblio1-api/src ./src

# On construit le JAR executable. Retirer -DskipTests
# le jour ou les tests JUnit doivent tourner a la construction.
RUN mvn -B clean package -DskipTests


# ---------- Etape 2 : execution ----------
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# On recupere uniquement le JAR produit a l'etape precedente.
COPY --from=build /app/target/*.jar app.jar

# Port par defaut en local. Render impose le sien via $PORT.
EXPOSE 8080

# Render fournit la variable PORT a l'execution.
# Le :-8080 sert de valeur par defaut hors Render.
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar app.jar"]
