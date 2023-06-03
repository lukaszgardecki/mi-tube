FROM openjdk:19-alpine3.16
ADD target/app-0.0.1-SNAPSHOT.jar .
COPY uploads uploads
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app-0.0.1-SNAPSHOT.jar"]