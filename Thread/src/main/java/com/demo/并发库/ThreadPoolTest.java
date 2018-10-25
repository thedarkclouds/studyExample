package com.demo.并发库;

import java.util.concurrent.*;

public class ThreadPoolTest {

    public static void main(String[] args) throws  Exception {
     final    CountDownLatch latch=new CountDownLatch(10000);
     //  ExecutorService threadPool = Executors.newFixedThreadPool(3);//固定线程
       //ExecutorService threadPool = Executors.newCachedThreadPool(); //自动增加线程
       ExecutorService threadPool = Executors.newSingleThreadScheduledExecutor();//单一线程，若线程死掉，会立刻有线程替补上来


     /* Executors.newScheduledThreadPool(3).schedule(new Runnable() {
            @Override
            public void run() {

            }
        },10,TimeUnit.SECONDS);*/ //定时线程,也可以指定步长





        Long begin=System.currentTimeMillis();

        for (int i = 1; i <= 10; i++) {
            final int task = i;
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    for (int j = 1; j <= 1000; j++) {
                        try {
                           // Thread.sleep(20);
                            System.out.println(Thread.currentThread().getName() + "  is loop of:" + j + "    for task:" + task);
                            latch.countDown();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            });


        }

        latch.await();
        Long end=System.currentTimeMillis();
        System.out.println("耗时："+(end-begin));
        threadPool.shutdown();
    }
}