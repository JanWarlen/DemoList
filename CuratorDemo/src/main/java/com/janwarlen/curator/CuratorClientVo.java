package com.janwarlen.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.AuthInfo;
import org.apache.curator.framework.api.ACLProvider;

import java.util.ArrayList;
import java.util.List;

public class CuratorClientVo {

    private String connectString;

    private int sessionTimeoutMs;

    private List<AuthInfo> authInfos = new ArrayList<>();

    private int connectionTimeoutMs;

    private RetryPolicy retryPolicy;

    private ACLProvider aclProvider;

    public String getConnectString() {
        return connectString;
    }

    public void setConnectString(String connectString) {
        this.connectString = connectString;
    }

    public int getSessionTimeoutMs() {
        return sessionTimeoutMs;
    }

    public void setSessionTimeoutMs(int sessionTimeoutMs) {
        this.sessionTimeoutMs = sessionTimeoutMs;
    }

    public List<AuthInfo> getAuthInfos() {
        return authInfos;
    }

    public void addAuthInfo(AuthInfo authInfo) {
        this.authInfos.add(authInfo);
    }

    public int getConnectionTimeoutMs() {
        return connectionTimeoutMs;
    }

    public void setConnectionTimeoutMs(int connectionTimeoutMs) {
        this.connectionTimeoutMs = connectionTimeoutMs;
    }

    public RetryPolicy getRetryPolicy() {
        return retryPolicy;
    }

    public void setRetryPolicy(RetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
    }

    public ACLProvider getAclProvider() {
        return aclProvider;
    }

    public void setAclProvider(ACLProvider aclProvider) {
        this.aclProvider = aclProvider;
    }
}
