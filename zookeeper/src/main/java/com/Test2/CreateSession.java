package com.Test2;

import java.io.IOException;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class CreateSession implements Watcher{

	private static ZooKeeper zookeeper;
	//创建一个与服务器的连接 需要(服务端的 ip+端口号)(session过期时间)(Watcher监听注册)  
	public static void main(String[] args) throws IOException, InterruptedException {
		
		zookeeper = new ZooKeeper("127.0.0.1:2181", 5000, new CreateSession());
		System.out.println(zookeeper.getState());
		
		System.out.println("ZooKeeper session established.");  
		Thread.sleep(Integer.MAX_VALUE);
	}
	
	private void doSomething(){
		System.out.println("do something");
		
	}
	
	@Override
	public void process(WatchedEvent event) {
		System.out.println("Receive watched event:" + event);
		if(event.getState() == KeeperState.SyncConnected){
			doSomething();
		}
	}
}
