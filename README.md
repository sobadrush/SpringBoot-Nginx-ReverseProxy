# SpringBoot-Nginx-ReverseProxy

## 說明
> 本專案主要用來測試 Nginx 的:
>>反向代理\
>>附載均衡

## 使用
1. 建置 image： ```sh build-image.sh```
2. 啟動 container：```sh run-container.sh```

## 附加功能
 - SpringBoot 使用 JSP
 - Junit 參數化測試
 - 使用 H2 資料庫
 - Docker 容器化
 - Docker Compose
 - Nginx 反向代理
 - 遠端偵錯 18787 port (@ 「use-external-tomcat」 branch)
 - Maven Filtering (@ 「use-external-tomcat」 branch)
 - YAML 多 Profile 配置
 - Spring 讀取外部設定檔 (docker-compose)

- Spring Actuator URL
    - http://localhost:7001/my-actuator/health
    - http://localhost:7001/my-actuator/env
    - http://localhost:7001/my-actuator/metrics
    - http://localhost:7001/my-actuator/mappings
    - http://localhost:7001/my-actuator/beans
    - http://localhost:7001/my-actuator/prometheus

| #  |                               說明                               |                                 URL                                 |
|:--:|:--------------------------------------------------------------:|:-------------------------------------------------------------------:|
| 1  |      🚩Junit5 入门系列教程-14-junit5 参数化测试(@ParameterizedTest)       |    https://blog.csdn.net/ryo1060732496/article/details/80823696     |
| 2  |            [H2資料庫]            在Spring Boot使用H2内存数据库            |           https://www.cnblogs.com/flydean/p/12680291.html           |
| 3  |          [H2資料庫] Day 07 - Spring Boot 資料庫 H2 ＋ Entity          |           https://ithelp.ithome.com.tw/articles/10237915            |
| 4  |              [SpringBoot使用JSP] Spring Boot + JSP               |         https://dotblogs.com.tw/ShihGoGo/2022/06/23/153716          |
| 5  |       [SpringBoot使用JSP]  Day 7 Spring Boot-Controller(下)       |           https://ithelp.ithome.com.tw/articles/10194035            |
| 6  |                       🚩Dockerfile 指令教學                        |                      https://liba.ro/5z94d0bqe                      |
| 7  |                         [古古 blog] 遠端偵錯                         |      https://kucw.github.io/blog/2020/1/intellij-remote-debug/      |
| 8  |                        [baeldung] 遠端偵錯                         |         https://www.baeldung.com/intellij-remote-debugging          |
| 8  |                windows下tomcat9配置远程debug(用idea)                 |      https://blog.csdn.net/u010999809/article/details/96761048      |
| 9  |              [Maven] SpringBoot filtering 替換參數-1               |             https://juejin.cn/post/6985810164620197902              |
| 10 |              [Maven] SpringBoot filtering 替換參數-2               |        https://blog.csdn.net/sayyy/article/details/114889238        |
| 11 |              [Maven] SpringBoot filtering 替換參數-3               |             https://juejin.cn/post/6844904185557680142              |
| 12 |                         Tomcat 管理介面-1                          |         https://www.cnblogs.com/wangjiming/p/12492764.html          |
| 13 |                         Tomcat 管理介面-2                          |   https://blog.csdn.net/weixin_42198656/article/details/121350952   |
| 14 |             YAML to List of Objects in Spring Boot             |           https://www.baeldung.com/spring-boot-yaml-list            |
| 15 | Using application.yml vs application.properties in Spring Boot |       https://www.baeldung.com/spring-boot-yaml-vs-properties       |
| 16 |                        YAML Placeholder                        |      https://blog.csdn.net/nuyoahso/article/details/115320410       |
| 17 |                            YAML 字串                             |    https://blog.csdn.net/zhoudingding/article/details/106251013     |
| 18 |                   springboot之入门2篇(yaml语法和使用）                   |      https://blog.csdn.net/nuyoahso/article/details/115320410       |
| 19 |                       CommandLineRunner                        | https://z.itpub.net/article/detail/9359DFC80B3615560719EA1529CD2520 |
| 20 |                           NGINX 反向代理                           |           https://ithelp.ithome.com.tw/articles/10221704            |


# 監控
| # |                          說明                          |            URL            |
|:-:|:----------------------------------------------------:|:-------------------------:|
| 1 | Spring Boot 使用 Micrometer 集成 Prometheus 监控 Java 应用性能 | https://liba.ro/5zdf2a9r9 |
| 2 |           [古古] SpringBoot - 監控工具 Actuator            | https://liba.ro/5zdf2qycp |