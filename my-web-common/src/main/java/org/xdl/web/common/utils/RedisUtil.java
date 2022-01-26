package org.xdl.web.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import redis.clients.jedis.commands.JedisCommands;
import redis.clients.jedis.params.SetParams;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author XieDuoLiang
 * @date 2022/1/24 下午3:10
 */
@Component
public class RedisUtil {

    private final static String LOCK_KEY = "redisLock";

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Resource
    private ValueOperations<String,Object> valueOperations;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private ValueOperations<String,String> stringValueOperations;

    //region json
    public void save(String key, Object value) {
        valueOperations.set(key,value);
    }

    public <T> T get(String key, Class<T> clazz) {
        return JSONObject.parseObject(JSON.toJSONString(valueOperations.get(key)),clazz);
    }

    public void incr(String key) {
        valueOperations.increment(key);
    }
    //endregion

    //region string
    public void save(String key, String value) {
        stringValueOperations.set(key,value);
    }

    public void save(String key, String value, long time, TimeUnit timeUnit) {
        stringValueOperations.set(key,value,time,timeUnit);
    }

    public void append(String key, String value) {
        stringValueOperations.append(key, value);
    }

    public String get(String key) {
        return stringValueOperations.get(key);
    }

    public boolean getLock() {
        String result = stringRedisTemplate.execute((RedisCallback<String>) connection -> {
            JedisCommands jedisCommands = (JedisCommands) connection.getNativeConnection();
            SetParams setParams = new SetParams();
            setParams.nx().px(100000L);
            return jedisCommands.set(LOCK_KEY, "1", setParams);
        });
        return "OK".equals(result);
    }
    //endregion

    public Boolean del(String key) {
        return stringValueOperations.getOperations().delete(key);
    }
}
