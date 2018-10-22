package com.demox.api;

import org.apache.zookeeper.*;

import java.io.IOException;

public class CreateNodeASync implements  Watcher{

    private static ZooKeeper zookeeper;
    //创建一个与服务器的连接 需要(服务端的 ip+端口号)(session过期时间)(Watcher监听注册)
    public static void main(String[] args) throws IOException, InterruptedException {
         //异步构造器
        zookeeper = new ZooKeeper("192.168.171.131:2181", 5000, new CreateNodeASync());
        System.out.println(zookeeper.getState());
        Thread.sleep(Integer.MAX_VALUE);
    }
    private void doSomething() {
        zookeeper.create("/node_1", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT,
                new IStringCallback(), "this is content");

        System.out.println("do something");
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("Receive watched event:" + event);
        if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
            doSomething();
        }
    }

    static class IStringCallback implements AsyncCallback.StringCallback {

        @Override
        public void processResult(int rc, String path, Object ctx, String name) {

            System.out.println("rc:" + rc);
            System.out.println("path:" + path);
            System.out.println("ctx:" + ctx);
            System.out.println("name:" + name);

        }
    }


}
