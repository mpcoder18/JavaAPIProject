# Use an official OpenJDK runtime as a base image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the target directory into the container
COPY target/testingspecification-0.0.1-SNAPSHOT.jar /app/testingspecification-0.0.1-SNAPSHOT.jar

# Expose the port your application runs on (default for Spring Boot is 8080)
EXPOSE 8080

# Run the JAR file using Java
ENTRYPOINT ["java", "-jar", "/app/testingspecification-0.0.1-SNAPSHOT.jar"]

