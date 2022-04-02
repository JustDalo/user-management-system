FROM openjdk:18-oracle
EXPOSE 8080

COPY ./target/spring-0.0.1-SNAPSHOT.jar spring-0.0.1_SNAPSHOT.jar
ENTRYPOINT ["java","-jar","spring-0.0.1_SNAPSHOT.jar"]