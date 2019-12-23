package com.janwarlen.redis;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.util.Objects;

@Configuration
public class RedisConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisConfig.class);

    @Value("${com.janwarlen.redis.mode}")
    private String redisMode;

    @Value("${cachecloud.mode}")
    private String cachecloudMode;
    @Value("${cachecloud.max-active}")
    private Integer cachecloudMaxActive;
    @Value("${cachecloud.max-wait}")
    private Integer cachecloudMaxWait;
    @Value("${cachecloud.max-idle}")
    private Integer cachecloudMaxIdle;
    @Value("${cachecloud.min-idle}")
    private Integer cachecloudMinIdle;

    @Value("${cachecloud.url}")
    private String cachecloudUrl;

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisEnvTypeEnum type = RedisEnvTypeEnum.getType(redisMode);
        if (null == type) {
            LOGGER.error("RedisConfig error:redis mode is null.");
            return null;
        }
        if (RedisEnvTypeEnum.CACHECLOUD.equals(type)) {
            type = RedisEnvTypeEnum.getType(cachecloudMode);
            RedisEnvTypeEnum cachecloudType = RedisEnvTypeEnum.getType(cachecloudMode);
            RedisInitUtil.setRedisProperties(redisProperties, cachecloudType, cachecloudUrl);
            RedisProperties.Pool pool = redisProperties.getPool();
            if (Objects.nonNull(cachecloudMaxActive)) {
                pool.setMaxActive(cachecloudMaxActive);
            }
            if (Objects.nonNull(cachecloudMaxWait)) {
                pool.setMaxWait(cachecloudMaxWait);
            }
            if (Objects.nonNull(cachecloudMaxIdle)) {
                pool.setMaxIdle(cachecloudMaxIdle);
            }
            if (Objects.nonNull(cachecloudMinIdle)) {
                pool.setMinIdle(cachecloudMinIdle);
            }
        }
        if (null == type) {
            LOGGER.error("RedisConfig error:cachecloud mode is null.");
            return null;
        }
        return getRedisConnectionFactory(type);
    }

    /**
     * 根据spring的自扫描配置redisProperties创建对应的 RedisConnectionFactory
     * （实际为JedisConnectionFactory，但JedisConnectionFactory为RedisConnectionFactory子类）
     * @param type 配置文件中的redis类型
     * @return RedisConnectionFactory
     */
    private RedisConnectionFactory getRedisConnectionFactory(RedisEnvTypeEnum type) {
        switch (type) {
            case STANDALONE:
                redisProperties.setSentinel(null);
                redisProperties.setCluster(null);
                break;
            case SENTINEL:
                redisProperties.setCluster(null);
                break;
            case CLUSTER:
                redisProperties.setSentinel(null);
                break;
            default:
                LOGGER.error("RedisConfig:redis mode is not correct.");
                return null;
        }
        return RedisInitUtil.createJedisConnectionFactory(redisProperties);
    }
}
