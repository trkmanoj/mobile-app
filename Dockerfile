FROM openjdk:8-jdk-alpine
EXPOSE 8080
COPY target/mobile-0.0.1-SNAPSHOT.jar mobile-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/mobile-0.0.1-SNAPSHOT.jar"]