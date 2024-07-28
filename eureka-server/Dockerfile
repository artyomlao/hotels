FROM openjdk:17
LABEL maintainer="eureka-service"
ADD build/libs/eureka-server-0.0.1-SNAPSHOT.jar eureka-service.jar
ENTRYPOINT ["java", "-jar", "eureka-service.jar"]