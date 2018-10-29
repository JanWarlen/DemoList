package com.janwarlen.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class JUnitCuratorOperation {

    private static CuratorFramework curator;

    /**
     * 验证不同zk客户端实例之间watcher是否在一个单线程上
     */
    private static CuratorFramework curator_2;

    private CountDownLatch countDownLatch = new CountDownLatch(1);


    /**
     * init
     *
     * @throws Exception
     */
    @BeforeClass
    public static void testPrepareClient() throws Exception {
        CuratorClientVo curatorClientVo = new CuratorClientVo();
        curatorClientVo.setConnectString("master:2181");
        curatorClientVo.setSessionTimeoutMs(60 * 1000);
        curatorClientVo.setConnectionTimeoutMs(10 * 1000);
        ExponentialBackoffRetry exponentialBackoffRetry = new ExponentialBackoffRetry(10 * 1000, 3);
        curatorClientVo.setRetryPolicy(exponentialBackoffRetry);

        curator = CuratorOperation.createCurator(curatorClientVo);

        curatorClientVo.setConnectString("slave1:2181,slave2:2182");
        curator_2 = CuratorOperation.createCurator(curatorClientVo);

        ConnectionStateListener connectionStateListener = new ConnectionStateListener() {
            @Override
            public void stateChanged(CuratorFramework client, ConnectionState newState) {
                switch (newState) {
                    case CONNECTED:
                        System.out.println("connected");
                        break;
                    case RECONNECTED:
                        System.out.println("RECONNECTED");
                        break;
                    case LOST:
                        System.out.println("LOST");
                        break;
                    case SUSPENDED:
                        System.out.println("SUSPENDED");
                        break;
                    case READ_ONLY:
                        System.out.println("READ_ONLY");
                        break;
                }
            }
        };
        CuratorOperation.addConnectionStateListener(curator, connectionStateListener);

        curator.start();
        curator_2.start();

        curator.blockUntilConnected();
        curator_2.blockUntilConnected();

    }

    @Test
    public void testTempNode() throws Exception {
        //验证临时节点是否会在session和连接断开消失
        curator.create().withMode(CreateMode.EPHEMERAL).forPath("/testtmp", "lalala".getBytes());

        //只监听节点内容变更事件
        curator_2.getData().usingWatcher(new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("getData:" + watchedEvent);

                if (watchedEvent.getType() == Event.EventType.NodeDataChanged) {
                    countDownLatch.countDown();
                }
            }
        }).forPath("/testtmp");

        countDownLatch.await();
    }

    /**
     * test Watcher range
     *
     * @throws Exception
     */
    public void testWatcherArea() throws Exception {
        //create temp node for test
        curator.create().withMode(CreateMode.EPHEMERAL).forPath("/testtmp", "lalala".getBytes());

        //只监听节点变动事件
        curator.getChildren().usingWatcher(new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("getChildren:" + watchedEvent);
            }
        }).forPath("/testtmp");

        //只监听节点内容变更事件
        curator_2.getData().usingWatcher(new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("getData:" + watchedEvent);
            }
        }).forPath("/testtmp");

        //wait one minutes for operation on zk
        Thread.sleep(1 * 60 * 1000);
    }

    /**
     * 验证watcher在单个实例中是单线程处理
     *
     * @throws Exception
     */
    @Test
    public void testWatcherSingleProcess() throws Exception {
        System.out.println("-------------------------------------------------");

        //create temp node for test
        curator.create().withMode(CreateMode.EPHEMERAL).forPath("/testtmp", "lalala".getBytes());

        //只监听节点变动事件
        curator.getChildren().usingWatcher(new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("getChildren:" + watchedEvent);
                //验证watcher在单个实例中是单线程处理
                try {
                    Thread.sleep(20 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).forPath("/testtmp");

        //只监听节点变动事件
        curator.getData().usingWatcher(new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("getChildren:" + watchedEvent);
                //验证watcher在单个实例中是单线程处理
                try {
                    Thread.sleep(20 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).forPath("/testtmp");

        //只监听节点内容变更事件
        curator_2.getData().usingWatcher(new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("getData:" + watchedEvent);

                if (watchedEvent.getType() == Event.EventType.NodeDataChanged) {
                    countDownLatch.countDown();
                }
            }
        }).forPath("/testtmp");

        countDownLatch.await();
    }

    /**
     * 测试同一个watcher重复绑定是否会被调用多次
     * 不会
     */
    @Test
    public void testWatcherMultiSetViaCurator() throws Exception {
        //create temp node for test
        curator.create().withMode(CreateMode.EPHEMERAL).forPath("/testtmp", "lalala".getBytes());

        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("----------------------------------------------------------------------");
                System.out.println("getChildren:" + watchedEvent);
                System.out.println("----------------------------------------------------------------------");
            }
        };

        //只监听节点变动事件
        curator.getData().usingWatcher(watcher).forPath("/testtmp");
        curator.getData().usingWatcher(watcher).forPath("/testtmp");

        //wait one minutes for operation on zk
        Thread.sleep(2 * 60 * 1000);
    }

    /**
     * 测试直接使用zk的handle绑定wather是否触发两次
     * @throws Exception
     */
    @Test
    public void testZooKeeperHandleWatcher() throws Exception {
        //create temp node for test
        curator.create().withMode(CreateMode.EPHEMERAL).forPath("/testtmp", "lalala".getBytes());

        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("----------------------------------------------------------------------");
                System.out.println("getChildren:" + watchedEvent);
                System.out.println("----------------------------------------------------------------------");
            }
        };

        Stat stat = new Stat();

        curator.getZookeeperClient().getZooKeeper().getData("/testtmp", watcher, stat);
        curator.getZookeeperClient().getZooKeeper().getData("/testtmp", watcher, stat);

//        curator.getData().usingWatcher(watcher).forPath("/testtmp");

        countDownLatch.await();
    }

    @AfterClass
    public static void testFinish() {
        curator.close();
        curator_2.close();
    }
}
