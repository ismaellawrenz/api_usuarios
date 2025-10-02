
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Instala Maven
RUN apt-get update && \
    apt-get install -y maven && \
    mvn -v


COPY pom.xml .
COPY src ./src


RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
