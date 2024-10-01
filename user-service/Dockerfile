FROM openjdk:21-jdk-slim

# Set the working directory inside the Docker container
WORKDIR /app

# Copy the JAR file from the target folder to the container
COPY target/user-service-0.0.1-SNAPSHOT.jar /app/user-service.jar

EXPOSE 8031

# Run the application using the Java runtime from the /app directory
ENTRYPOINT ["java", "-jar", "/app/user-service.jar"]


