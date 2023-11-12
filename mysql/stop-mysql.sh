#!/bin/bash
IMAGENAME=springsecurityjwt-mysql
CONTAINERNAME=springsecurityjwt-mysql
docker stop $CONTAINERNAME
docker rm $CONTAINERNAME
docker image rm $IMAGENAME
