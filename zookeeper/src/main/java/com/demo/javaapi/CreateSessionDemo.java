package com.demo.javaapi;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class CreateSessionDemo {
    //多地址使用逗号分隔
    private final static String CONNECTING = "192.168.171.130:2181";
private static CountDownLatch countDownLatch=new CountDownLatch(1);
    public static void main(String[] args) throws Exception {
        //new zookeeper 表示创建连接
        ZooKeeper zooKeeper = new ZooKeeper(CONNECTING, 5000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if (watchedEvent.getState()==Event.KeeperState.SyncConnected){
                   //--
                    countDownLatch.countDown();
                    System.out.println(watchedEvent.getState());

                }


            }
        });
countDownLatch.await();
        System.out.println(zooKeeper.getState());

    }

}
