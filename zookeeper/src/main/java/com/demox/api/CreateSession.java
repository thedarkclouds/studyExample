package com.demox.api;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
/*
zookeeper的java api操作
        创建会话：
        Zookeeper(String connectString,int sessionTimeout,Watcher watcher)
        Zookeeper(String connectString,int sessionTimeout,Watcher watcher,boolean canBeReadOnly)
        Zookeeper(String connectString,int sessionTimeout,Watcher watcher,long sessionId,byte[] sessionPasswd)
        Zookeeper(String connectString,int sessionTimeout,Watcher watcher,long sessionId,byte[] sessionPasswd,boolean canBeReadOnly)
        参数说明：
        connectString -- host:port[，host:port][basePath] 指定的服务器列表，多个host:port之间用英文逗号分隔。还可以可选择的指定一个基路径，如果指定了一个基路径，则所有后续操作基于这个及路径进行。
        sessionTimeOut -- 会话超时时间。以毫秒为单位。客户端和服务器端之间的连接通过心跳包进行维系，如果心跳包超过这个指定时间则认为会话超时失效。
        watcher -- 指定默认观察者。如果为null表示不需要观察者。
        canBeReadOnly -- 是否支持只读服务。只当一个服务器失去过半连接后不能再进行写入操作时，是否继续支持读取操作。略
        sessionId、SessionPassword -- 会话编号 会话密码，用来实现会话恢复。

        **注意，整个创建会话的过程是异步的，构造方法会在初始化连接后即返回，并不代表真正建立好了一个会话，此时会话处于"CONNECTING"状态。
        **当会话真正创建起来后，服务器会发送事件通知给客户端，只有客户端获取到这个通知后，会话才真正建立。*/


public class CreateSession implements Watcher {

    private static ZooKeeper zookeeper;
    //创建一个与服务器的连接 需要(服务端的 ip+端口号)(session过期时间)(Watcher监听注册)


    public static void main(String[] args) throws IOException, InterruptedException {

        zookeeper = new ZooKeeper("192.168.171.131:2181", 5000, new CreateSession());
        System.out.println(zookeeper.getState());
        Thread.sleep(Integer.MAX_VALUE);
    }

    private void doSomething(){
        System.out.println("do something");
    }


    /*需要我们注意就一点：ZooKeeper 允许客户端向服务端注册一个 Watcher 监听，当服务端的一些指定事件触发了这个 Watcher，
    那么就会向指定客户端发送一个事件通知来实现分布式的通知功能。

    zookeeper的所有的API，都有同步和异步两种方式，使用异步API时，client可为每个operation设置callback，在operation被执行后，
    zookeeper会执行对应的callback*/

    @Override
    public void process(WatchedEvent event) {
        System.out.println("Receive watched event:" + event);
        if(event.getState() == Event.KeeperState.SyncConnected){
            System.out.println("ZooKeeper session established.");
            doSomething();
        }
    }

}
