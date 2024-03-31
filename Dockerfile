FROM openjdk:11.0.4-jre-slim
EXPOSE 8181
ARG JAR_FILE=target/demo-cicd-k8s-1.0.jar
ADD ${JAR_FILE} demo.jar

ENTRYPOINT ["java","-jar","/demo.jar"]
