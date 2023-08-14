package com.nanshan.springbootnginxreverseproxy;

import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.text.MessageFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutionException;

/**
 * @author RogerLo
 * @date 2023/8/13
 *
 * 使用 Lettuce 進行 Redis 連線
 */
@Component
@Log4j2
public class RedisConnection {

    // private final RedisProps redisProps;
    private StatefulRedisConnection<String, String> connection;
    private RedisClient redisClient;
    private final DefaultListableBeanFactory context;

    // 因已在 RedisConnection 建構子中進行 Redis 連線設定
    // 故這邊要組裝 RedisProps 時，需也用建構子注入
    public RedisConnection(@Autowired RedisProps redisProps, DefaultListableBeanFactory context, WebApplicationContext webApplicationContext) {
        RedisURI redisUri = RedisURI.builder()
            .withHost(redisProps.getHost())
            .withPort(redisProps.getPort())
            // .withDatabase(redisProps.getDb())
            .withPassword(redisProps.getPa55wd())
            .withTimeout(Duration.of(10, ChronoUnit.SECONDS))
            .build();
        this.redisClient = RedisClient.create(redisUri);
        this.redisClient.setOptions(
            ClientOptions.builder()
            .autoReconnect(true)
            .timeoutOptions(TimeoutOptions.builder().fixedTimeout(Duration.ofSeconds(10)).build())
            .build());
        this.connection = redisClient.connect();
        // this.redisProps = redisProps;
        this.context = context;
    }

    @PostConstruct
    private void initBean() throws ExecutionException, InterruptedException {
        this.getRedisAsyncCmd(1);
        this.getRedisAsyncCmd(3);
    }

    public <T, R> RedisAsyncCommands<T, R> getRedisAsyncCmd(int db) throws ExecutionException, InterruptedException {
        String beanName = MessageFormat.format("asyncCmd_db{0}", db);
        if (context.containsBean(beanName)) {
            return (RedisAsyncCommands<T, R>) context.getBean(beanName);
        }

        RedisAsyncCommands<String, String> asyncCmd = this.connection.async();
        asyncCmd.select(db).get();
        context.registerSingleton(beanName, asyncCmd);
        return (RedisAsyncCommands<T, R>) context.getBean(beanName);
    }

    @PreDestroy
    public void beforeDestroy() {
        log.info("... Destroy Redis RedisConnection ...");
        this.connection.close();
        this.redisClient.close();
        redisClient.shutdown();
    }

}
