FROM eclipse-temurin:21-jdk

# Create app directory
WORKDIR /app

# Copy built JAR file and rename it app.jar(assumes you've built it already)
COPY target/*.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]


# Build your JAR before running Docker:
# Run mvn clean package -DskipTests from inside back-end/.
# need to skip the test bc My context load test is failing, which is causing the maven clean command to fail. Last time, in order to have the mvn compile command working I had to have the mysql container running. However it seems like I'll get caught in a loop now where I need to have the mysql container running in order for the jar to be built, but I have to have the jar to be built to be able to run the docker compose up command to run and have my java container work correctly.