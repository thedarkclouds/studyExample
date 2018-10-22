package com.lock;

import org.I0Itec.zkclient.ZkClient;

public class LOCK2Test implements Runnable{

 private  static  final    ZkClient zk = new ZkClient("192.168.171.131:2181", 5 * 10000);
    private static  String name="";
    @Override
    public void run() {

        LOCK2 myDistributedLock = new LOCK2(zk, name);
        System.out.println("name*********"+name);
        myDistributedLock.lock();
        myDistributedLock.unlock();
    }

    public static void main(String[] args) {

       for (int i=0; i<2;i++){
           name=i+name;
           Thread t1=new Thread(new LOCK2Test());
           t1.start();
       }




    }

}
