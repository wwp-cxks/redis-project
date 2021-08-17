package com.redis.lottery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author cxks
 * @Date 2021/8/17 22:39
 */
@Configuration
public class RedisConfiguration {
    /**
     * 重写Redis序列化方式，使用json方式
     */

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 创建一个json的序列化对象
        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        // 设置value的序列化方式json
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // 设置key的序列化方式String
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        // 设置hash key的序列化方式string
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // 设置hash value的序列化方式json
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}

