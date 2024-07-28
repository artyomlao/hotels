FROM openjdk:17
LABEL maintainer="users-service"
ADD build/libs/users-service-0.0.1-SNAPSHOT.jar users-service.jar
ENTRYPOINT ["java", "-jar", "users-service.jar"]