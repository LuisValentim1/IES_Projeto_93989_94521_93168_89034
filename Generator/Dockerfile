FROM maven:3.5-jdk-8
FROM java:8
WORKDIR /app
COPY target/*.jar /app/thegeekyasian-docker-spring-boot-app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=docker","thegeekyasian-docker-spring-boot-app.jar"]


#Primeiro precisam do jar compilado

#mvn clean install  - cria o jar
#docker build -t gerador/container-spring .   -cria a imagem 
#docker run -p 8020:8020 -t gerador/container-spring   -deploy to container