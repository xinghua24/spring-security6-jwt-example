#!/bin/bash
IMAGENAME=springsecurityjwt-mysql
CONTAINERNAME=springsecurityjwt-mysql
docker start $CONTAINERNAME
if [ $? -ne 0 ]; then
    echo 'clear data...'
    rm -rf ./.mysql-data
    echo 'build docker image...'
    docker build -t $IMAGENAME .
    echo 'docker run...'
    docker run --name $CONTAINERNAME -d \
      -p 127.0.0.1:3306:3306 \
      -v ${PWD}/.mysql-data:/var/lib/mysql \
      $IMAGENAME
fi