package com.demo.javaapi;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

public class CreateNodeDemo implements Watcher {

    //多地址使用逗号分隔
    private final static String CONNECTING = "192.168.171.131:2181";
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static ZooKeeper zooKeeper;
    private static Stat stat = new Stat();

    public static void main(String[] args) throws Exception {
        //new zookeeper 表示创建连接
        zooKeeper = new ZooKeeper(CONNECTING, 5000, new CreateNodeDemo());
        countDownLatch.await();
        System.out.println(zooKeeper.getState());
        //节点   值   权限（所有人 ，创建者，读权限，所有ip，授权）   节点类型（临时节点******）
        String result = zooKeeper.create("/90", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zooKeeper.getData("/90", new CreateNodeDemo(), stat);//增加一个watch
        System.out.println("创建成功result==" + result);
        //-1表示不需要管她的版本号是多是多少
        zooKeeper.setData("/90", "mic123".getBytes(), -1);
        zooKeeper.setData("/90", "mic456".getBytes(), -1);
        zooKeeper.delete("/node7",-1);
        Thread.sleep(2000);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            if (Event.EventType.None == watchedEvent.getType() && null == watchedEvent.getPath()) {
                countDownLatch.countDown();
                System.out.println(watchedEvent.getState() + "--->" + watchedEvent.getType());

            } else if (watchedEvent.getType() == Event.EventType.NodeDataChanged) {
                try {

                    System.out.println("路徑： " + watchedEvent.getPath() + "-->改變后的值" + zooKeeper.getData(watchedEvent.getPath(), true, stat));


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {
                try {

                    System.out.println("路徑： " + watchedEvent.getPath() + "-->改變后的值" + zooKeeper.getData(watchedEvent.getPath(), true, stat));


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        }
    }
}

