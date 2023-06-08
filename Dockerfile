FROM openjdk:17-alpine3.14
ADD target/mi-tube.jar .
COPY uploads uploads
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "mi-tube.jar"]