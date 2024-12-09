# Build stage: Use an official OpenJDK 21 image as the base
FROM openjdk:21-jdk-slim AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven pom file and the source code to the container
COPY pom.xml .
COPY src ./src

# Install Maven, build the application, and remove unnecessary files
RUN apt-get update && apt-get install -y maven && \
    mvn clean package -DskipTests && \
    rm -rf src && rm -rf ~/.m2

# Run stage: Use a smaller image to run the app
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Define the command to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]