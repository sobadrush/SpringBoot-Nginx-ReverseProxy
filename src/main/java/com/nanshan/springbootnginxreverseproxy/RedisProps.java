package com.nanshan.springbootnginxreverseproxy;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Base64;

/**
 * @author RogerLo
 * @date 2023/8/13
 */
@Getter
@Setter // 將 yaml 中的值物件化，需搭配 get/setter
@ConfigurationProperties(prefix = "my-redis") // 對應 application.yaml 中的 key: redis
public class RedisProps {
    private String host;
    private int port;
    private int db;
    private String pa55wd;

    public String getPa55wd() {
        System.out.println("...getPa55wd - Base64 Decode...");
        return new String(Base64.getDecoder().decode(this.pa55wd));
    }
}

