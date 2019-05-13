#!/bin/bash

source ./.env

if [ "$1" = "-f" ] ; then
  docker-compose stop
  docker-compose rm -fv
  docker rmi -f postgres:11
fi

docker-compose build
docker-compose up