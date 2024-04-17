# Use a multi-stage build
FROM maven:3.8.4-openjdk-21-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src src
RUN mvn package -DskipTests

# Use a lightweight base image with Java 21 support
FROM openjdk:21-jdk-alpine

WORKDIR /app

# Copy the packaged JAR file from the build stage
COPY --from=build /app/target/your-app.jar /app/your-app.jar

# Expose the port your application listens on
EXPOSE 8080

# Command to run your application
CMD ["java", "-jar", "your-app.jar"]