#./mvnw package && java -jar conveyor.jar
#./mvnw package && java -jar target/conveyor-0.0.1-SNAPSHOT.jar
#./mvnw package && java -jar target/conveyor.jar

#FROM maven:4.0-jdk-11-onbuild
#CMD ["java","-jar","target/conveyor-0.0.1-SNAPSHOT.jar"]


FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 8080
ARG JAR_FILE=target/conveyor-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]


#docker build -t conveyor:0.0.1 .