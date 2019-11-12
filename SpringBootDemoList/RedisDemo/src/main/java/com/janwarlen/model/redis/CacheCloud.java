package com.janwarlen.model.redis;

/**
 * @Auther: janwarlen
 * @Date: 2018/12/6 11:43
 * @Description:
 */
public class CacheCloud {

    private Integer appId;

    /**
     * 状态字段
     *     GOOD(1),
     *     WARN(0),
     *     ERROR(-1)
     */
    private Integer status;

    private String message;

    /**
     * redis密码
     */
    private String password;
    // 集群
    private Integer shardNum;

    /**
     * ip:port ip:port
     */
    private String shardInfo;
    // 主从
    /**
     * ip:port ip:port
     */
    private String sentinels;

    private String masterName;
    // 单机(无message、appId)
    /**
     * ip:port
     */
    private String standalone;

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getShardNum() {
        return shardNum;
    }

    public void setShardNum(Integer shardNum) {
        this.shardNum = shardNum;
    }

    public String getShardInfo() {
        return shardInfo;
    }

    public void setShardInfo(String shardInfo) {
        this.shardInfo = shardInfo;
    }

    public String getSentinels() {
        return sentinels;
    }

    public void setSentinels(String sentinels) {
        this.sentinels = sentinels;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public String getStandalone() {
        return standalone;
    }

    public void setStandalone(String standalone) {
        this.standalone = standalone;
    }
}
