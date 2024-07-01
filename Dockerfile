FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .

RUN chmod -R 755 /app

RUN mvn clean package -DskipTests

RUN ls -l /app/target/

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=builder /app/target/media-0.0.1-SNAPSHOT.jar /target/

RUN java -version


ENTRYPOINT ["java", "-jar", "/target/media-0.0.1-SNAPSHOT.jar"]
