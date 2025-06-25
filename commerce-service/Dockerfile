FROM maven:3.9.7-eclipse-temurin-11 AS build
COPY . /usr/src/app
WORKDIR /usr/src/app
RUN mvn clean package -DskipTests
FROM openjdk:11-slim
COPY --from=build /usr/src/app/target/commerce-service.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
