# ref. https://www.jinnsblog.com/2018/12/docker-dockerfile-guide.html
# ref. --platform : https://collabnix.com/warning-the-requested-images-platform-linux-amd64-does-not-match-the-detected-host-platform-linux-arm64-v8/
# 使用 jre 當作 base image，較節省容量

# [Build 指令]：docker build --platform linux/amd64 -t sobadrush/springboot-nginx:v1 --no-cache -f ./Dockerfile .
# [Run 指令]：docker run --platform linux/amd64 -idt --name my-springboot-nginx -p 8081:8080 sobadrush/springboot-nginx:v1 /bin/bash

#【使用 SpringBoot Jar 啟動】
#FROM ballerina/jre11:v1
#MAINTAINER "ROGER LO"
#LABEL description="這是一個使用 fabric8 的 docker-maven-plugin 打包的專案" version="1.0" owner="ROGER LO"
#WORKDIR /app
#COPY ./SpringBoot-Nginx-ReverseProxy.jar ./app.jar
#EXPOSE 8080 18090
#ENTRYPOINT ["java","-jar","app.jar"]

#【使用 war 啟動 SpringBoot (不推薦！)】
#FROM ballerina/jre11:v1
#MAINTAINER "ROGER LO"
#LABEL description="這是一個使用 fabric8 的 docker-maven-plugin 打包的專案" version="1.0" owner="ROGER LO"
#WORKDIR /app
#COPY ./SpringBoot-Nginx-ReverseProxy.war ./app.war
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","app.war"]

#【使用 war + Tomcat 啟動(不推薦！)】
#FROM tomcat:9.0.71-jdk11
#MAINTAINER "ROGER LO"
#LABEL description="這是一個使用 tomcat:9.0.71 打包的專案" version="1.0" owner="ROGER LO"
#COPY ./SpringBoot-Nginx-ReverseProxy.war /usr/local/tomcat/webapps/app.war


# 設定 SpringBoot 遠端偵錯
# - 多開啟 18090 port
# - address 設定：*:18090 或 0.0.0.0:18090 → 讓所有人都能連
# - run-container.sh 中的 docker run 新增 -p 18090:18090
FROM ballerina/jre11:v1
MAINTAINER "ROGER LO"
LABEL description="這是一個使用 fabric8 的 docker-maven-plugin 打包的專案" version="1.0" owner="ROGER LO"
WORKDIR /app
COPY ./SpringBoot-Nginx-ReverseProxy.jar ./app.jar
EXPOSE 8080 8787
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,address=*:8787,server=y,suspend=n","-jar","app.jar"]
