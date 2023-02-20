FROM openjdk:11
ARG JAR_FILE=build/libs/bangsil-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} bangsil-user.jar
ENTRYPOINT ["java","-jar","bangsil-user.jar"]
