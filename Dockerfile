FROM maven:3.9-eclipse-temurin-22-alpine AS build

WORKDIR /app

COPY . /app

RUN bash -c 'until mvn clean package; do echo "Retrying..."; sleep 5; done'

FROM eclipse-temurin:22-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/spring-0.0.1-SNAPSHOT.jar /app/spring-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/spring-0.0.1-SNAPSHOT.jar"]
