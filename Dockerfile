FROM amazoncorretto:17

EXPOSE 4000

ADD target/spring-boot-docker.jar spring-boot-docker.jar

ENTRYPOINT ["java", "-jar", "/spring-boot-docker.jar"]