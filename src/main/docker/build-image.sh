#!/bin/sh

project_loc=/Users/roger/Desktop/Program-workspace/java_workspace/SpringBoot-Nginx-ReverseProxy/
containerName=my-springboot-h2
registry=sobadrush
buildImageName=springboot-h2-img
tag=v1

# maven build
cd $project_loc
mvn clean package -Dmaven.test.skip=true

# cd to docker folder
cd $project_loc/src/main/docker/

# remove existing jar/war in docker folder
find $project_loc/src/main/docker \( -iname "*.jar" -o -iname "*.war" \) | xargs -L 1 -I {} rm {}

# move artifact into docker folder
find $project_loc/target \( -iname "*.jar" -o -iname "*.war" \) -maxdepth 1 | xargs -L 1 -I {} mv {} .

# docker stop/rm/rmi
existsContainerId=`docker ps -a | grep $containerName | awk '{print $1}'`
existsImageName=`docker ps -a | grep $containerName | awk '{print $2}'`

[ -n "$existsContainerId" ] && docker stop $existsContainerId && docker rm $existsContainerId || echo " === 無存在的 existsContainerId，不進行 stop & rm === "

imageId=`docker images -q $registry/$buildImageName:$tag`
[ -n "$imageId" ] && docker rmi $imageId || echo " === 無存在的 imageName，不進行 rmi === "

# docker build
docker build --platform linux/amd64 -t $registry/$buildImageName:$tag --no-cache -f ./Dockerfile .

# docker run
#docker run --platform linux/amd64 -idt --name $containerName -p 8081:8080 $registry/$buildImageName:$tag /bin/bash
