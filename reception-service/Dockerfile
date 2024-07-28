FROM openjdk:17
LABEL maintainer="reception-service"
ADD build/libs/reception-service-0.0.1-SNAPSHOT.jar reception-service.jar
ENTRYPOINT ["java", "-jar", "reception-service.jar"]