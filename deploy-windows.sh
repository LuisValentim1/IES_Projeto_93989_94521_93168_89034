#!/bin/bash

function trap_ctrlc() {
	echo "Shutting down docker."
	sudo docker-compose down
	echo "Stopped bash opeation."
	exit 2
}

trap "trap_ctrlc" 2

cd ./Blossom_Gerador &&
  mvn clean package -DskipTests &&
	cd .. &&

cd ./blossom &&
  mvn clean package -DskipTests &&
 cd .. &&
  docker-compose build &&
  docker-compose up