FROM openjdk:11.0.4-jre-slim
EXPOSE 8090
ARG JAR_FILE=target/demo-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} demo.jar

ENTRYPOINT ["java","-jar","/demo.jar"]
