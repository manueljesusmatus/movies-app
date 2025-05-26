FROM eclipse-temurin:22-jdk-jammy
LABEL maintainer="mmatush <mmatushdeveloper@gmail.com>"
WORKDIR /app

ENV TZ=America/Santiago

RUN apt-get update &&  \
    apt-get install -y vim &&  \
    apt-get clean

COPY *.jar app.jar
ENTRYPOINT ["java", "-Xms256m", "-Xmx512m", "-jar", "app.jar"]