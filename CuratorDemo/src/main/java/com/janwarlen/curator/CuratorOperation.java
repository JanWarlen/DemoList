package com.janwarlen.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionStateListener;

public class CuratorOperation {

    public static CuratorFramework createCurator(CuratorClientVo curatorClientVo) {
        CuratorFrameworkFactory.Builder build = CuratorFrameworkFactory.builder();
        build = build.connectString(curatorClientVo.getConnectString());

        if (curatorClientVo.getSessionTimeoutMs() > 0) {
            build = build.sessionTimeoutMs(curatorClientVo.getSessionTimeoutMs());
        }

        if (!curatorClientVo.getAuthInfos().isEmpty()) {
            build = build.authorization(curatorClientVo.getAuthInfos());
        }

        if (curatorClientVo.getConnectionTimeoutMs() > 0) {
            build = build.connectionTimeoutMs(curatorClientVo.getConnectionTimeoutMs());
        }

        if (null != curatorClientVo.getRetryPolicy()) {
            build = build.retryPolicy(curatorClientVo.getRetryPolicy());
        }

        if (null != curatorClientVo.getAclProvider()) {
            build = build.aclProvider(curatorClientVo.getAclProvider());
        }

        return build.build();
    }

    public static void addConnectionStateListener(CuratorFramework client, ConnectionStateListener connectionStateListener) {
        client.getConnectionStateListenable().addListener(connectionStateListener);
    }

}
