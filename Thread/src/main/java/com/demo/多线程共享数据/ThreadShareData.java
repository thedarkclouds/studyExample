package com.demo.多线程共享数据;

public class ThreadShareData {

    public static void main(String[] args) {
       //同用一个runnable对象，数据共享

        shareData1 data1=new shareData1();

        new Thread(){}.start();
        new Thread(){}.start();
    }




}

class  shareData1 implements Runnable{
    private int count=100;

    @Override
    public void run() {
        while (true){
            count--;
        }
    }

    private int j =0;

    public synchronized  void increment(){

        j++;
    }
    public synchronized  void decrement(){

        j--;
    }




}


