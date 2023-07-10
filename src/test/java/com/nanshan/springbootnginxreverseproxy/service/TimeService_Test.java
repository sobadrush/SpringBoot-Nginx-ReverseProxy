package com.nanshan.springbootnginxreverseproxy.service;

import com.nanshan.springbootnginxreverseproxy.BaseTest;
import com.nanshan.springbootnginxreverseproxy.utils.SpringContextUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

import java.text.SimpleDateFormat;

/**
 * @author RogerLo
 * @date 2023/7/10
 */
public class TimeService_Test extends BaseTest {

    @Autowired
    private TimeService timeService;

    @Autowired
    private CacheManager cacheManager;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Test
    @DisplayName("[Test_000] 預設使用哪個 CacheManager")
    @Disabled
    void test_000() {
        System.out.println("contains cacheManager bean: " + SpringContextUtil.containsBean("cacheManager"));
        System.out.println("cacheManager = " + cacheManager);
    }

    @Test
    @DisplayName("[Test_001] 測試 @Cacheable")
    @Disabled
    void test_001() throws InterruptedException {
        for (int i = 0; i < 30; i++) {
            System.out.println(sdf.format(timeService.getTime()));
            Thread.sleep(1000);
        }
    }

}
