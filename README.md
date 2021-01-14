# blossom

<img src="https://i.imgur.com/dPcLwIx.png" >

## For online use
- Online server at [http://deti-engsoft-11.ua.pt/](http://deti-engsoft-11.ua.pt/ "Blossom App")
- Useful emails:
    - working account for client **g11@mail.pt** with password **pass**
    - for admin **admin@mail.pt** with password **pass**

## Class

Introdução a Engenharia do Software

## Project abstract

Web application with tracking capabilities in relation to soil moisture and
PH, with subsequent advice on current status and ability to generate
triggers at certain thresholds.

## Project description

Blossom is a full stack web app project, fully developed by students from concept, to
technical architecture, all the way to the final implementation, using enterprise frameworks
and following agile methods of production .
Furthermore we’ll work as teams do in actual work environments following structured roles
(Team manager, Product owner, Architect, DevOps master) for each developer, each with
their own specific responsibilities over different important aspects of the project as a whole.
Although everyone shall still work as a core developer instead of focusing solely on their
assigned role.

<img src="https://i.imgur.com/YftU0q9.png" >


## How to run the project

- The deployment is really simple
  While on the main folder (the one with docker-compose)

- Windows:
'./deploy-windows.sh'

- Linux:
'./deploy.sh'

Alternatively if you wish to apply changes on only 1 container, compile using 'mvn package install' followed by:
'docker-compose up --build'

and to turn off
'docker-compose down'

Execute the following on the command line while on the "blossom folder"

'./mvnw spring-boot:run'

## Dependencies

Blossom is a Spring based application compiled using maven.
Maven will provide all the dependencie instalation required to run the project

Since the project is deployed using a docker container all the depencies are ready, these include:
-Spring Web
-Thymeleaf
-JPA
-Srping MVC
-Spring Dev Tools
-Spring SQL Driver

## Data Sensing

In the scope of this project we’ll generate semi-random data similar to what we’d find from
actual real life soil monitoring. The part of our project responsible for the Data generation will
be held in a separate docker container, this will prove beneficial in case we want to change
our data source to real data as the project evolves.