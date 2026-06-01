# =========================
# Build Stage
# =========================
FROM gradle:8.11.1-jdk17 AS builder

WORKDIR /app

# Copy only Gradle-related files first
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Fix Windows CRLF issue
RUN sed -i 's/\r$//' gradlew

# Make wrapper executable
RUN chmod +x gradlew

# Download dependencies separately for better caching
RUN gradle dependencies --no-daemon

# Copy source code only
COPY src src

# Build application
RUN gradle clean bootJar --no-daemon

# =========================
# Runtime Stage
# =========================
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]