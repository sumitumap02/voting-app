# # Use an official OpenJDK image as the base image
# FROM openjdk:17-jdk-slim

# # Set the working directory in the container
# WORKDIR /app

# # Copy the local JAR file into the container
# COPY target/Online-Voting-System-0.0.1-SNAPSHOT.jar /app/online-voting-system.jar

# # Expose the port that the Spring Boot application will run on
# EXPOSE 8181

# # Run the JAR file with Java
# ENTRYPOINT ["java", "-jar", "online-voting-system.jar"]



# Use the official OpenJDK 21 image as a base
FROM eclipse-temurin:21-jdk AS build

# Set the working directory
WORKDIR /app

# Copy the Maven wrapper and pom.xml files
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn
COPY pom.xml .

# Copy the source code
COPY src ./src

# Run the Maven build to package the application
RUN ./mvnw clean package -DskipTests

# Use a lightweight JRE for running the application
FROM eclipse-temurin:21-jre

# Set the working directory
WORKDIR /app

# Copy the packaged jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port (default is 8080 for Spring Boot)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]