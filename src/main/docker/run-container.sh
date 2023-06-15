#!/bin/sh

project_loc=/Users/roger/Desktop/Program-workspace/java_workspace/SpringBoot-Nginx-ReverseProxy/
containerName=my-springboot-h2
registry=sobadrush
buildImageName=springboot-h2-img
tag=v1

docker ps -a | grep -E 'Exited.*'$containerName 2>&1 > /dev/null

# get last command result
if [ "$?" = "0" ] # 有查詢到 Exited 的容器
then
    echo "container exists ... restart it"
    docker restart $containerName
else
    echo "container not exists ... run it"
    docker run --platform linux/amd64 -idt --name $containerName -p 8081:8080 $registry/$buildImageName:$tag /bin/bash
fi

# docker logs
sleep 2s
docker logs -f $containerName