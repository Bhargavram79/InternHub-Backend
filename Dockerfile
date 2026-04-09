FROM maven:3.9.6-eclipse-temurin-17

WORKDIR /app

COPY pom.xml .
COPY src src/
COPY mvnw .
COPY .mvn .mvn/

RUN mvn clean package -DskipTests

EXPOSE 8080

ENV JAVA_OPTS=""

ENTRYPOINT exec java $JAVA_OPTS -jar target/*.jar
