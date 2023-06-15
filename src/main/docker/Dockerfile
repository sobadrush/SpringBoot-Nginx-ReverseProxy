# ref. https://www.jinnsblog.com/2018/12/docker-dockerfile-guide.html
# ref. --platform : https://collabnix.com/warning-the-requested-images-platform-linux-amd64-does-not-match-the-detected-host-platform-linux-arm64-v8/
# 使用 jre 當作 base image，較節省容量ㄋ

# [Build 指令]：docker build --platform linux/amd64 -t sobadrush/springboot-nginx:v1 --no-cache -f ./Dockerfile .
# [Run 指令]：docker run --platform linux/amd64 -idt --name my-springboot-nginx -p 8081:8080 sobadrush/springboot-nginx:v1 /bin/bash

FROM ballerina/jre11:v1
MAINTAINER "ROGER LO"
LABEL description="這是一個使用 fabric8 的 docker-maven-plugin 打包的專案" version="1.0" owner="ROGER LO"
WORKDIR /app
COPY ./SpringBoot-Nginx-ReverseProxy.jar ./app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]

# 使用 war 檔
#FROM ballerina/jre11:v1
#MAINTAINER "ROGER LO"
#LABEL description="這是一個使用 fabric8 的 docker-maven-plugin 打包的專案" version="1.0" owner="ROGER LO"
#WORKDIR /app
#COPY ./SpringBoot-Nginx-ReverseProxy.war ./app.war
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","app.war"]