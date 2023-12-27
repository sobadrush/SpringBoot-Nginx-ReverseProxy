package com.nanshan.springbootnginxreverseproxy;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author RogerLo
 * @date 2023/12/27
 */
@Configuration
public class CacheConfig {

    /**
     * 自訂的 key 產生器
     */
    @Bean("myKeyGenerator")
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                String keyStr = method.getName() + "[" + Arrays.asList(params) + "]";
                System.out.println("keyStr : " + keyStr);
                return keyStr;
            }
        };
    }

}
