FROM openjdk:8u191-jdk-alpine3.9
ADD target/carapp.jar .
EXPOSE 8080
CMD java -jar carapp.jar