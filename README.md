# spring-boot-app

## Simple spring boot app for api tests
### run standalone
via gradle

    `./gradlew bootRun --args='--spring.datasource.url=jdbc:postgresql://localhost:5432/appdb --spring.datasource.username=appuser --spring.datasource.password=pasword'`

build jar and run:

   `java -jar your-application.jar --spring.datasource.url=jdbc:postgresql://patgres_host:5432/appdb --spring.datasource.username=appuser --spring.datasource.password=pasword`
    
### run in docker

1. build jar:

    `./gradlew :bootJar`

2. build image from Dockerfile:

    `docker build -t spring-boot-up:latest .`

3. run image:

    `docker run --network my_network --name spring-boot-app -e SPRING_DATASOURCE_URL=jdbc:postgresql://patgres_host:5432/appdb -e SPRING_DATASOURCE_USERNAME=appuser -e SPRING_DATASOURCE_PASSWORD=pasword -p 8080:8080 image_id`
