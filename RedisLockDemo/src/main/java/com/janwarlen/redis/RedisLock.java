package com.janwarlen.redis;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisLock {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisLock.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 获取锁
     *
     * @param key 锁的key
     * @param value 系统当前时间+超时时间
     * @return 是否成功获取锁
     */
    public boolean lock(String key, String value) {

        if(stringRedisTemplate.opsForValue().setIfAbsent(key, value)) {
            // key不存在，拿到锁
            return true;
        }

        String lastValue = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isNotBlank(lastValue) && Long.parseLong(lastValue) < System.currentTimeMillis()) {
            // currentValue不为空且小于当前时间,即锁过期
            String oldValue = stringRedisTemplate.opsForValue().getAndSet(key, value);
            if (lastValue.equals(oldValue)) {
                // 防并发
                return true;
            }
        }
        return false;
    }

    /**
     * 解锁
     * @param key 锁的key
     * @param value 获取锁使用的value
     */
    public void unlock(String key, String value) {
        try {
            String lastValue = stringRedisTemplate.opsForValue().get(key);
            if (StringUtils.isNotBlank(lastValue) && lastValue.equals(value)) {
                stringRedisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (Throwable e) {
            LOGGER.error("unlock redis Lock error,key:" + key + ",value:" + value, e.getMessage());
        }
    }
    /**
     * 强制解锁
     * @param key 锁的key
     */
    public void unlock(String key) {
        try {
            stringRedisTemplate.opsForValue().getOperations().delete(key);
        } catch (Throwable e) {
            LOGGER.error("force unlock redis Lock error,key:" + key, e.getMessage());
        }
    }

}
