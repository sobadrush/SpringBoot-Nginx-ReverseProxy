package com.nanshan.springbootnginxreverseproxy.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author RogerLo
 * @date 2023/7/10
 */
@Service
public class TimeService {

    @Cacheable(cacheNames = "myGetTime")
    public java.util.Date getTime() {
        return new Date();
    }

    @Cacheable(cacheNames = "myGetCurrentTimeMillis")
    public Long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

}
