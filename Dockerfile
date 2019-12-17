FROM openjdk:8-jdk-alpine

LABEL maintainer="tries1@naver.com"

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=build/libs/spring-cloud-web-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} spring-cloud-web.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=dev", "-jar", "/spring-cloud-web.jar"]
