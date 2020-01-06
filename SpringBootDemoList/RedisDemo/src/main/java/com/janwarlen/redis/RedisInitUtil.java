package com.janwarlen.redis;

import com.alibaba.fastjson.JSONObject;
import com.janwarlen.constant.CharConstant;
import com.janwarlen.constant.DigitConstant;
import com.janwarlen.constant.StringConstant;
import com.janwarlen.http.HttpClientUtil;
import com.janwarlen.model.redis.CacheCloud;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: janwarlen
 * @Date: 2018/12/6 10:44
 * @Description:辅助初始化redis类
 */
public class RedisInitUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisInitUtil.class);

    /**
     * redis模式为cachecloud时，需要更换spring的自扫描配置redisProperties属性值
     */
    public static void setRedisProperties(RedisProperties redisProperties, RedisEnvTypeEnum type, String cachecloudUrl) {
        if (null == type) {
            LOGGER.error("RedisConfig error:cachecloud mode is null.");
            return;
        }
        // 向cachecloud服务器请求资源
        String doGetRes = HttpClientUtil.doGet(cachecloudUrl);
        CacheCloud cacheCloud = JSONObject.parseObject(doGetRes, CacheCloud.class);
        if (null != cacheCloud) {
            String password = cacheCloud.getPassword();
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(password)) {
                redisProperties.setPassword(password);
            }
            switch (type) {
                case STANDALONE:
                    String[] standalone = cacheCloud.getStandalone().split(StringConstant.COLON);
                    redisProperties.setHost(standalone[DigitConstant.ZERO]);
                    redisProperties.setPort(Integer.parseInt(standalone[DigitConstant.ONE]));
                    break;
                case SENTINEL:
                    String sentinelNodes = cacheCloud.getSentinels().replace(CharConstant.SPACE, CharConstant.COMMA_ENG);
                    String masterName = cacheCloud.getMasterName();
                    RedisProperties.Sentinel sentinel = new RedisProperties.Sentinel();
                    sentinel.setMaster(masterName);
                    sentinel.setNodes(sentinelNodes);
                    redisProperties.setSentinel(sentinel);
                    break;
                case CLUSTER:
                    List<String> clusterNodes = Arrays.asList(cacheCloud.getShardInfo().split(StringConstant.SPACE));
                    RedisProperties.Cluster cluster = new RedisProperties.Cluster();
                    cluster.setNodes(clusterNodes);
                    redisProperties.setCluster(cluster);
                    break;
                default:
                    LOGGER.error("RedisConfig error:cachecloud mode is not correct.");
            }
        } else {
            LOGGER.error("RedisConfig error:cachecloud resource is null.");
        }
    }

    /**
     * 根据redisProperties生成对应的 JedisConnectionFactory
     * @param redisProperties redis的配置
     * @return JedisConnectionFactory
     */
    public static JedisConnectionFactory createJedisConnectionFactory(RedisProperties redisProperties) {
        JedisPoolConfig poolConfig = redisProperties.getPool() != null
                ? jedisPoolConfig(redisProperties) : new JedisPoolConfig();
        poolConfig.setBlockWhenExhausted(true);
        poolConfig.setTestOnBorrow(true);
        if (getSentinelConfig(redisProperties) != null) {
            return new JedisConnectionFactory(getSentinelConfig(redisProperties), poolConfig);
        }
        if (getClusterConfiguration(redisProperties) != null) {
            return new JedisConnectionFactory(getClusterConfiguration(redisProperties), poolConfig);
        }
        return new JedisConnectionFactory(createStandAloneJedis(redisProperties));
    }

    public static JedisShardInfo createStandAloneJedis(RedisProperties redisProperties) {
        JedisShardInfo jedisShardInfo = new JedisShardInfo(redisProperties.getHost(), redisProperties.getPort());
        jedisShardInfo.setPassword(redisProperties.getPassword());
        jedisShardInfo.setConnectionTimeout(redisProperties.getTimeout());
        return jedisShardInfo;
    }


    /**
     * 从redisProperties中生成 JedisPoolConfig
     * @param redisProperties redis配置属性
     * @return JedisPoolConfig
     */
    private static JedisPoolConfig jedisPoolConfig(RedisProperties redisProperties) {
        JedisPoolConfig config = new JedisPoolConfig();
        RedisProperties.Pool props = redisProperties.getPool();
        config.setMaxTotal(props.getMaxActive());
        config.setMaxIdle(props.getMaxIdle());
        config.setMinIdle(props.getMinIdle());
        config.setMaxWaitMillis(props.getMaxWait());
        return config;
    }

    /**
     * 从redisProperties中生成 RedisSentinelConfiguration（主从）
     * @param redisProperties redis配置属性
     * @return RedisSentinelConfiguration
     */
    private static RedisSentinelConfiguration getSentinelConfig(RedisProperties redisProperties) {
        RedisProperties.Sentinel sentinelProperties = redisProperties.getSentinel();
        if (sentinelProperties != null) {
            RedisSentinelConfiguration config = new RedisSentinelConfiguration();
            config.master(sentinelProperties.getMaster());
            config.setSentinels(createSentinels(sentinelProperties));
            return config;
        }
        return null;
    }

    /**
     * 主从
     * 从 RedisProperties.Sentinel 中读取 List<RedisNode>
     * @param sentinel redisProperties中的sentinel配置
     * @return List<RedisNode>
     */
    private static List<RedisNode> createSentinels(RedisProperties.Sentinel sentinel) {
        List<RedisNode> nodes = new ArrayList<>();
        for (String node : StringUtils
                .commaDelimitedListToStringArray(sentinel.getNodes())) {
            try {
                String[] parts = StringUtils.split(node, ":");
                Assert.state(parts.length == 2, "Must be defined as 'host:port'");
                nodes.add(new RedisNode(parts[0], Integer.valueOf(parts[1])));
            }
            catch (RuntimeException ex) {
                throw new IllegalStateException(
                        "Invalid redis sentinel " + "property '" + node + "'", ex);
            }
        }
        return nodes;
    }

    /**
     * 集群
     * 从redisProperties中生成 RedisClusterConfiguration
     * @param redisProperties redis配置属性
     * @return RedisClusterConfiguration
     */
    private static RedisClusterConfiguration getClusterConfiguration(RedisProperties redisProperties) {
        if (redisProperties.getCluster() == null) {
            return null;
        }
        RedisProperties.Cluster clusterProperties = redisProperties.getCluster();
        RedisClusterConfiguration config = new RedisClusterConfiguration(
                clusterProperties.getNodes());

        if (clusterProperties.getMaxRedirects() != null) {
            config.setMaxRedirects(clusterProperties.getMaxRedirects());
        }
        return config;
    }
}
