package com.nanshan.springbootnginxreverseproxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author RogerLo
 * @date 2023/8/5
 */
@EnableAsync
@Configuration
public class AsyncConfig {

    @Bean(name = "my-executor")
    public Executor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("[my-executor] async-");
        executor.initialize();
        return executor;
    }

}