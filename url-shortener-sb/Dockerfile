# Step 1: Build the application using Maven
FROM openjdk:21-jdk-slim AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper and Maven configuration files
COPY mvnw ./
COPY .mvn/ .mvn

# Make the Maven wrapper executable
RUN chmod +x mvnw

# Copy the pom.xml and download the dependencies
COPY pom.xml ./
RUN ./mvnw dependency:go-offline -DskipTests

# Step 2: Copy the source code and compile the project
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Step 3: Use OpenJDK 17 as the base image for runtime
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the builder stage (adjust the filename if necessary)
COPY --from=builder /app/target/*.jar app.jar

# Expose the port your application will run on (default Spring Boot is 8080)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
