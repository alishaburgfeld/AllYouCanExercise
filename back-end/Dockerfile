FROM eclipse-temurin:21
WORKDIR /Exercise_App
# COPY ./src/main ./pom.xml ./mvnw ./.mvn/wrapper/maven-wrapper.properties ./
COPY . .
# RUN chmod +x mvnw
CMD ["./mvnw", "spring-boot:run"]

# ideally build the jar and then copy that in instead of the mvn wrapper and src/main. then the command would be to run the jar.
