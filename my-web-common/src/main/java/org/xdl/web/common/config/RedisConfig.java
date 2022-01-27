package org.xdl.web.common.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.xdl.web.common.pojo.User;
import org.xdl.web.common.utils.RedisUtil;

/**
 * @author XieDuoLiang
 * @date 2022/1/23 下午6:14
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String,Object> restTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String,Object> restTemplate = new RedisTemplate<>();
        restTemplate.setConnectionFactory(redisConnectionFactory);
        RedisSerializer<String> stringRedisSerializer = RedisSerializer.string();
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        restTemplate.setKeySerializer(stringRedisSerializer);
        restTemplate.setValueSerializer(fastJsonRedisSerializer);
        restTemplate.setHashKeySerializer(stringRedisSerializer);
        restTemplate.setHashValueSerializer(fastJsonRedisSerializer);
        restTemplate.afterPropertiesSet();
        return restTemplate;
    }

    @Bean
    public ValueOperations<String,Object> valueOperations(RedisTemplate<String,Object> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    @Bean
    public HashOperations<String, Object, Object> hashValueOperations(RedisTemplate<String,Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
        return stringRedisTemplate;
    }

    @Bean
    public ValueOperations<String,String> stringValueOperations(StringRedisTemplate stringRedisTemplate) {
        return stringRedisTemplate.opsForValue();
    }
}
