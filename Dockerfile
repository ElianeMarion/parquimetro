FROM openjdk:17
COPY ./target /app
WORKDIR /app
ENTRYPOINT ["java", "-jar", "parquimetro-0.0.1-SNAPSHOT.jar"]