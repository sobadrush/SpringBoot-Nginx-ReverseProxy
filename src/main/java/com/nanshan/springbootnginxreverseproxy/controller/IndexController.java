package com.nanshan.springbootnginxreverseproxy.controller;

import io.micrometer.core.instrument.MeterRegistry;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author RogerLo
 * @date 2024/1/5
 */
@RestController
@RequestMapping("/indexController")
public class IndexController {

    @Autowired
    private MeterRegistry registry; // 計量器註冊表

    private io.micrometer.core.instrument.Counter counter_core;
    private io.micrometer.core.instrument.Counter counter_index;
    private AtomicInteger app_online_count;

    @PostConstruct
    private void init() {
        counter_core = registry.counter("app_requests_method_count_total" /*指標名稱*/, "method", "IndexController.core" /*自己定義的 Counter 標籤名稱*/);
        counter_index = registry.counter("app_requests_method_count_total" /*指標名稱*/, "method", "IndexController.index" /*自己定義的 Counter 標籤名稱*/);
        app_online_count = registry.gauge("app_online_count" /*指標名稱*/, new AtomicInteger(0));
    }

    @GetMapping(value = "/index", produces = { MediaType.APPLICATION_JSON_VALUE })
    public String index() {
        try {
            counter_index.increment();
        } catch (Exception e) {
            return ExceptionUtils.getStackTrace(e);
        }
        return counter_index.count() + " index of springboot2-prometheus.";
    }

    @GetMapping(value = "/core", produces = { MediaType.APPLICATION_JSON_VALUE })
    public String coreUrl() {
        try {
            counter_core.increment();
        } catch (Exception e) {
            return ExceptionUtils.getStackTrace(e);
        }
        return counter_core.count() + " coreUrl Monitor by Prometheus.";
    }

    @GetMapping(value = "/online", produces = { MediaType.APPLICATION_JSON_VALUE })
    public String onlineCount() {
        int people = 0;
        try {
            people = new Random().nextInt(2000);
            app_online_count.set(people);
        } catch (Exception e) {
            return ExceptionUtils.getStackTrace(e);
        }
        return "current online people: " + people;
    }

}
