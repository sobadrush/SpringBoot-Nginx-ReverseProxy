package com.nanshan.springbootnginxreverseproxy;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author RogerLo
 * @date 2023/7/10
 * <p>
 * Spring Cache 使用 caffeine 作為 Cache 核心套件
 * 設定類別
 */
@Configuration
@Slf4j
public class MyCacheConfig {

    /**
     * 設定多組 Cache
     * ref. https://liba.ro/lksc8wsv
     */
    @Bean
    public CacheManager cacheManager() {
        CaffeineCache patientInfoCache = this.buildCache("Cache-A", 10);
        CaffeineCache latestReportCache = this.buildCache("Cache-B", 30);
        SimpleCacheManager manager = new SimpleCacheManager();
        manager.setCaches(Arrays.asList(patientInfoCache, latestReportCache));
        return manager;
    }

    private CaffeineCache buildCache(String name, int minutesToExpire) {
        return new CaffeineCache(name, Caffeine.newBuilder()
                .expireAfterWrite(minutesToExpire, TimeUnit.SECONDS)
                .initialCapacity(50)
                .maximumSize(100)
                .build());
    }

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
