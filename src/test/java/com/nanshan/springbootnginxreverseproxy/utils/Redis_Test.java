package com.nanshan.springbootnginxreverseproxy.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nanshan.springbootnginxreverseproxy.BaseTest;
import com.nanshan.springbootnginxreverseproxy.RedisConnection;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.async.RedisAsyncCommands;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

/**
 * @author RogerLo
 * @date 2023/8/13
 */
public class Redis_Test extends BaseTest {

    @Autowired
    private RedisConnection redisConn;

    @Autowired
    private ObjectMapper objectMapper;

    private static String REDIS_KEY = "DATA_TODO:9014669";

    @Resource(name = "asyncCmd_db1") // 因為是動態註冊，故 IDE 偵測不到，正常！
    private RedisAsyncCommands redisAsyncCmd;

    @Test
    @DisplayName("[Test_001] 測試 Lettuce Redis 連線操作: SET")
    @Disabled
    void test_001() throws ExecutionException, InterruptedException {
        // RedisAsyncCommands<String, String> redisAsyncCmd = redisConn.getRedisAsyncCmd(3);

        RedisFuture<String> redisFuture = redisAsyncCmd.set(REDIS_KEY, fakeJsonData());
        redisAsyncCmd.expire(REDIS_KEY, Duration.ofMinutes(10));

        // [方式1 阻塞]: get
        // String result = redisFuture.get();
        // System.out.println("[阻塞] Redis Execute Result: " + result);

        // [方式2 非阻塞]: thenAccept
        redisFuture.thenAccept(rs -> {
            // 會等待 Redis 完成後才回頭執行此段代碼
            System.err.println("[非阻塞] Redis Execute Result: " + rs);
        });
    }

    @Test
    @DisplayName("[Test_002] 測試 Lettuce Redis 連線操作: persist")
    @Disabled
    void test_002() throws ExecutionException, InterruptedException {
        RedisAsyncCommands<String, String> redisAsyncCmd = redisConn.getRedisAsyncCmd(1);
        // 設定到期時間為無限期
        RedisFuture<Boolean> redisFuture = redisAsyncCmd.persist(REDIS_KEY);
        System.out.println("[阻塞] Redis Execute Result: " + redisFuture.get());
    }

    @Test
    @DisplayName("[Test_003] 測試 Lettuce Redis 連線操作: GET")
    @Disabled
    void test_003() throws InterruptedException, ExecutionException {
        RedisAsyncCommands<String, String> redisAsyncCmd = redisConn.getRedisAsyncCmd(1);
        RedisFuture<String> redisFuture = redisAsyncCmd.get(REDIS_KEY);

        // redisFuture.thenAccept(data -> {
        //     List<ToDoEntity> toDoList;
        //     try {
        //         toDoList = objectMapper.readValue(data, new TypeReference<List<ToDoEntity>>() {});
        //         toDoList.forEach(System.out::println);
        //     } catch (JsonProcessingException e) {
        //         throw new RuntimeException(e);
        //     }
        // });
        // Thread.sleep(10000);

        // ---

        CompletionStage<Void> completionStage = redisFuture.thenAccept(data -> {
            List<ToDoEntity> toDoList = null;
            try {
                toDoList = objectMapper.readValue(data, new TypeReference<List<ToDoEntity>>() {
                });
                toDoList.forEach(System.out::println);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        completionStage.toCompletableFuture().join();
    }

    @Test
    @DisplayName("[Test_004] 測試 Lettuce Redis 連線操作: DEL")
    @Disabled
    void test_004() throws ExecutionException, InterruptedException {
        RedisAsyncCommands<String, String> redisAsyncCmd = redisConn.getRedisAsyncCmd(1);
        RedisFuture<Long> redisFuture = redisAsyncCmd.del(REDIS_KEY);
        System.out.println("[阻塞] Redis Execute Result: " + redisFuture);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class ToDoEntity {
        private String userId;
        private String id;
        private String title;
        private boolean completed;
    }

    private static String fakeJsonData() {
        return "[\n" +
                "    {\n" +
                "        \"userId\": 1001,\n" +
                "        \"id\": 1,\n" +
                "        \"title\": \"讀 Redis\",\n" +
                "        \"completed\": false\n" +
                "    },\n" +
                "    {\n" +
                "        \"userId\": 1002,\n" +
                "        \"id\": 2,\n" +
                "        \"title\": \"準備課程內容\",\n" +
                "        \"completed\": false\n" +
                "    },\n" +
                "    {\n" +
                "        \"userId\": 1003,\n" +
                "        \"id\": 3,\n" +
                "        \"title\": \"clean my room\",\n" +
                "        \"completed\": true\n" +
                "    }\n" +
                "]";
    }
}
