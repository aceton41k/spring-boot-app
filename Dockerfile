# Используем базовый образ OpenJDK
FROM openjdk:17-jdk-slim

# Создаем рабочую директорию в контейнере
WORKDIR /app

# Копируем JAR-файл приложения в рабочую директорию
COPY build/libs/spring-boot-app-1.0.jar /app/spring-boot-app-1.0.jar

# Открываем порт, на котором будет работать приложение
EXPOSE 8080

# Указываем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "/app/spring-boot-app-1.0.jar"]
