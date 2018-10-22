package com.Test2;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class CreateNodeSync implements Watcher{

	private static ZooKeeper zookeeper;
	 //创建一个与服务器的连接 需要(服务端的 ip+端口号)(session过期时间)(Watcher监听注册) 
	public static void main(String[] args) throws IOException, InterruptedException {
		
		zookeeper = new ZooKeeper("127.0.0.1:2181", 5000, new CreateNodeSync());
		System.out.println(zookeeper.getState());
		Thread.sleep(Integer.MAX_VALUE);
	}
	
	/**
	 * 创建Znode
	  * CreateMode:
	  * 	PERSISTENT (持续的，相对于EPHEMERAL，不会随着client的断开而消失)
	  *		PERSISTENT_SEQUENTIAL（持久的且带顺序的）
	  *		EPHEMERAL (短暂的，生命周期依赖于client session)
	  *		EPHEMERAL_SEQUENTIAL  (短暂的，带顺序的)
	  */
	private void doSomething(){
		try {
			String path = zookeeper.create("/node_2", "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.out.println("return path:" + path);
		
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
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
