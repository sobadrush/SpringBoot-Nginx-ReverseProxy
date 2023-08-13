package com.nanshan.springbootnginxreverseproxy;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

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

    // 因已在 RedisConnection 建構子中進行 Redis 連線設定
    // 故這邊要組裝 RedisProps 時，需也用建構子注入
    public RedisConnection(@Autowired RedisProps redisProps) {
        RedisURI redisUri = RedisURI.builder()
            .withHost(redisProps.getHost())
            .withPort(redisProps.getPort())
            .withDatabase(redisProps.getDb())
            .withPassword(redisProps.getPa55wd())
            .withTimeout(Duration.of(10, ChronoUnit.SECONDS))
            .build();
        this.redisClient = RedisClient.create(redisUri);
        this.connection = redisClient.connect();
        // this.redisProps = redisProps;
    }

    public RedisAsyncCommands<String, String> getRedisAsyncCmd() {
        RedisAsyncCommands<String, String> asyncCmd = this.connection.async();
        log.info(">>> asyncCmd.ping() = {}", asyncCmd.ping());
        return this.connection.async();
    }

    @PreDestroy
    public void beforeDestroy() {
        log.info("... Destroy Redis RedisConnection ...");
        this.connection.close();
        this.redisClient.close();
        redisClient.shutdown();
    }

}
