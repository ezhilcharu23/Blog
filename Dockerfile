FROM openjdk:11
ADD target/apps.jar apps.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","apps.jar"]