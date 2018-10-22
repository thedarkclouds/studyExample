package CountDown;

import java.util.concurrent.CountDownLatch;

import static CountDown.Persion.*;

public class Task {
   // https://mp.weixin.qq.com/s/jWI5rLwDB2yZIC3tPn5Vog   提升
    //https://juejin.im/post/5b7e0880e51d45388b6af5d3  JUC之CountDownLatch的源码和使用场景分析
    private static CountDownLatch latch = new CountDownLatch(3);

    public static void main(String[] args) throws Exception{
        new Thread()
        {
            public void run()
            {
                fatherToRes();
                //--1
                latch.countDown();
            };
        }.start();
        new Thread()
        {
            public void run()
            {
                motherToRes();
                latch.countDown();
            };
        }.start();
        new Thread()
        {
            public void run()
            {
                meToRes();
                latch.countDown();
            };
        }.start();
        //--到0，就行了
        //释放线程
        latch.await();
        togetherToEat();
    }

}
