# Use a multi-stage build
FROM maven:3.9.6-amazoncorretto-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src src
RUN mvn package -DskipTests

# Use a base image with Java 21 support
FROM openjdk:21-jdk

WORKDIR /app

# Copy the packaged JAR file from the build stage
COPY --from=build /app/target/mascarade-back-end-*.jar /app/mascarade-back.jar

# Expose the port your application listens on
EXPOSE 8080

# Command to run your application
CMD ["java", "-jar", "mascarade-back.jar"]