FROM openjdk:17
LABEL maintainer="gateway-service"
ADD build/libs/gateway-service-0.0.1-SNAPSHOT.jar gateway-service.jar
ENTRYPOINT ["java", "-jar", "gateway-service.jar"]