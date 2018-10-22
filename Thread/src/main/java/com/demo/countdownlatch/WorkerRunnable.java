package com.demo.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class WorkerRunnable implements  Runnable {
    private final CountDownLatch doneSignal;
    private final int i;

    WorkerRunnable(CountDownLatch doneSignal, int i) {
        this.doneSignal = doneSignal;
        this.i = i;
    }
     @Override
    public void run() {
        doWork(i);
        doneSignal.countDown();
    }


    void doWork(int i) {
        System.out.println(i+"******work");
    }


    public static void main(String[] args) throws InterruptedException {
        CountDownLatch doneSignal = new CountDownLatch(10);
        Executor e = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; ++i) {
            e.execute(new WorkerRunnable(doneSignal, i));
        }

        doneSignal.await();           // wait for all to finish
        System.err.println("work");
    }

  /*  作者：莫那·鲁道
    链接：https://juejin.im/post/5ae754dd6fb9a07abc29b2ce
    来源：掘金
    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。*/




}
