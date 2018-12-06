package com.janwarlen.redis;

import org.apache.commons.lang3.StringUtils;

/**
 * @Auther: janwarlen
 * @Date: 2018/11/26 17:28
 * @Description:
 */
public enum RedisEnvTypeEnum {

    STANDALONE("standalone"),
    SENTINEL("sentinel"),
    CLUSTER("cluster"),
    CACHECLOUD("cachecloud");

    /**
     * redis模式，single单点，sentinel主从,depon德邦
     */
    private String envType;

    RedisEnvTypeEnum(String envType) {
        this.envType = envType;
    }

    public String getEnvType() {
        return envType;
    }

    /**
     * 根据模式字符串获取对应枚举类型
     * @param mode 模式字符串
     * @return 枚举类型
     */
    public static RedisEnvTypeEnum getType(String mode) {
        RedisEnvTypeEnum[] types = RedisEnvTypeEnum.values();
        for (RedisEnvTypeEnum type : types) {
            if (StringUtils.equals(type.getEnvType(), mode)) {
                return type;
            }
        }
        return null;
    }
}
