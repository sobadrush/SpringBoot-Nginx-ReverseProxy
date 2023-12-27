package com.nanshan.springbootnginxreverseproxy;

import com.nanshan.springbootnginxreverseproxy.utils.GzipUtil;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.Duration;

/**
 * Spring Cached 使用 Redis 的配置類別
 */
@Configuration
public class MyRedisCacheConfig implements CachingConfigurer {

    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    @Override
    public CacheManager cacheManager() {
        return new RedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory), this.redisCacheConfiguration());
    }

    private RedisCacheConfiguration redisCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(5))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JacksonGzipSerializer()));
    }

    private class JacksonGzipSerializer extends GenericJackson2JsonRedisSerializer {

        @Override
        public byte[] serialize(Object source) throws SerializationException {
            byte[] raw = super.serialize(source);
            try {
                return GzipUtil.compress(raw);
            } catch (IOException ioe) {
                throw new SerializationException("Exception", ioe);
            }
        }

        @Override
        public Object deserialize(byte[] source) throws SerializationException {
            try {
                byte[] raw = GzipUtil.decompress(source);
                return deserialize(raw, Object.class);
            } catch (IOException ioe) {
                throw new SerializationException("Exception", ioe);
            }
        }

    }

}