package com.lock;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/*我们基于ZkClient这个客户端来实现，当然也可以用原生Zookeeper API,大致是一样的
        坐标如下：
<dependency>
<groupId>com.101tec</groupId>
<artifactId>zkclient</artifactId>
<version>0.2</version>
</dependency>*/


public class LOCK2 {

    private ZkClient zkClient;
    private String name;
    private String currentLockPath;
    private CountDownLatch countDownLatch;

    private static final String PARENT_LOCK_PATH = "/distribute_lock";

    public LOCK2(ZkClient zkClient, String name) {
        this.zkClient = zkClient;
        this.name = name;
    }

    //加锁
    public void lock() {
        //判断父节点是否存在，不存在就创建
        if (!zkClient.exists(PARENT_LOCK_PATH)) {
            try {
                //多个线程只会成功建立一次
                zkClient.createPersistent(PARENT_LOCK_PATH);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //创建当前目录下的临时有序节点
        currentLockPath = zkClient.createEphemeralSequential(PARENT_LOCK_PATH + "/", System.currentTimeMillis());
        //校验是否最小节点
        System.out.println("有序节点创建。。加锁=="+currentLockPath);
        checkMinNode(currentLockPath);
    }

    //解锁
    public void unlock() {
        System.out.println("delete : " + currentLockPath);
        zkClient.delete(currentLockPath);
    }


    private boolean checkMinNode(String lockPath) {
        //获取当前目录下所有子节点
        List<String> children = zkClient.getChildren(PARENT_LOCK_PATH);
        Collections.sort(children);
        int index = children.indexOf(lockPath.substring(PARENT_LOCK_PATH.length() + 1));
        if (index == 0) {
            System.out.println(name + "：success");
            if (countDownLatch != null) {
              //  --1
                countDownLatch.countDown();
            }
            return true;
        } else {
            String waitPath = PARENT_LOCK_PATH + "/" + children.get(index - 1);
            System.out.println("----"+waitPath);
            //等待前一个节点释放的监听
            waitForLock(waitPath);
            return false;
        }
    }


    private void waitForLock(String prev) {
        System.out.println(name + " current path :" + currentLockPath + "：fail add listener" + " wait path :" + prev);
        countDownLatch = new CountDownLatch(1);
        zkClient.subscribeDataChanges(prev, new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println("prev node is done");
                checkMinNode(currentLockPath);
            }
        });
        if (!zkClient.exists(prev)) {
            return;
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        countDownLatch = null;
    }

}
