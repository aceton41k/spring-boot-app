FROM maven:3.8.7-openjdk-18-slim AS build

WORKDIR /app

COPY . /app

RUN mvn clean package

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/spring-0.0.1-SNAPSHOT.jar /app/spring-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/spring-0.0.1-SNAPSHOT.jar"]
