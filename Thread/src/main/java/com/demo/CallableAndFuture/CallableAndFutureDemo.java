package com.demo.CallableAndFuture;

import java.util.Random;
import java.util.concurrent.*;

public class CallableAndFutureDemo {
    //带有返回值的线程池  submit提交  线程 Runnable  or  callable
    public static void main(String[] args) {

        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        Future<String> future = threadPool.submit(new Callable<String>() {
            public String call() throws Exception {
                Thread.sleep(200);
                return "hello";
            }

            ;
        });
        System.out.println("等待结果");
        try {
            System.out.println("拿到结果：" + future.get());
        } catch (Exception e) {
            e.printStackTrace();
        }




        //提交多个线程任务，等待返回值
        ExecutorService threadPool2 = Executors.newFixedThreadPool(10);
        CompletionService completionService = new ExecutorCompletionService(threadPool2);
        //执行十次任务
        for (int i = 0; i < 10; i++) {
            final int seq = i;
            completionService.submit(new Callable() {
                @Override
                public Integer call() throws Exception {
                    Thread.sleep(new Random().nextInt(5000));//随机返回不超过5秒
                    return seq;
                }
            });
        }

        try {
            for (int j = 0; j < 10; j++) {
                System.out.println(completionService.take().get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
