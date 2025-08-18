FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY target/purchases-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-XX:+ExitOnOutOfMemoryError","-jar","app.jar"]
CMD ["--spring.profiles.active=prod"]