FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/*.jar employee-api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "employee-api.jar"]
