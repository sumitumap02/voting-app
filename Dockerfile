# Use an official OpenJDK image as the base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the local JAR file into the container
COPY target/Online-Voting-System-0.0.1-SNAPSHOT.jar /app/online-voting-system.jar

# Expose the port that the Spring Boot application will run on
EXPOSE 8181

# Run the JAR file with Java
ENTRYPOINT ["java", "-jar", "online-voting-system.jar"]
