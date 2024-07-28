FROM openjdk:17
LABEL maintainer="auth-service"
ADD build/libs/auth-service-0.0.1-SNAPSHOT.jar auth-service.jar
ENTRYPOINT ["java", "-jar", "auth-service.jar"]