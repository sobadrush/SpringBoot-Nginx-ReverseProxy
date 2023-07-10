package com.nanshan.springbootnginxreverseproxy;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author RogerLo
 * @date 2023/7/10
 *
 * Spring Cache 使用 caffeine 作為 Cache 核心套件
 * 設定類別
 */
@Configuration
@Slf4j
public class MyCacheConfig {

    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        log.info("=== 設定 MyCacheConfig.caffeineConfig ===");
        return Caffeine.newBuilder()
            // .expireAfterAccess(5, TimeUnit.SECONDS) // 表示項目在「最後一次訪問後」的【5秒】內保持有效。如果在【5秒】內再次訪問該項目，則計時器將被重置，並且項目的存留時間將從該訪問時間重新計算
            .expireAfterWrite(3, TimeUnit.SECONDS) // 表示項目在「寫入快取後」的【5秒】 內保持有效。如果在【5秒】內再次訪問該項目，則計時器將被重置，並且項目的存留時間將從最後一次寫入時間重新計算
            // 參考[refreshAfterWrite requires a LoadingCache] 錯誤解決
            // .refreshAfterWrite(3, TimeUnit.SECONDS) // 項目在寫入後的 3秒 內，當對該項目進行訪問時，Caffeine 快取會自動觸發刷新操作，以更新該項目的值
            .initialCapacity(50) // 在創建快取時可以容納的項目數量。這個值用於預先分配內部數據結構的容量，以提高效能
            .maximumSize(100); // 超過這個容量時，Caffeine 快取將根據預設或自定義的淘汰策略自動從快取中移除一些項目，以便為新的項目提供空間。這有助於限制快取的大小，防止快取無限增長並占用過多的系統資源
    }

    @Bean
    public CacheManager cacheManager(Caffeine caffeine) {
        log.info("=== 設定 MyCacheConfig.cacheManager 使用 Caffeine ===");
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }

}
