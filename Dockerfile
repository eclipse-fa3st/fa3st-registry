FROM eclipse-temurin:17-jdk-alpine
COPY starter/target/fa3st-registry-starter-*.jar fa3st-registry-starter.jar
ENTRYPOINT ["java","-jar","/fa3st-registry-starter.jar"]
