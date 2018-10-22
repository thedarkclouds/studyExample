package com.demo.多线程共享数据;


//抽离方法
//抽离共享数据--封装对象
public class ThreadShareData2 {
    private  static shareData2 data1 = new shareData2();

    public static void main(String[] args) {

        new Thread(new MyData2(data1)).start();
        new Thread(new MyData3(data1)).start();

       /* new Thread(new Runnable() {
            @Override
            public void run() {
                data1.decrement();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                data1.increment();
            }
        });*/

    }

}


class shareData2 {
    private int j = 0;

    public synchronized void increment() {
        j++;
        System.out.println();
    }

    public synchronized void decrement() {
        j--;
    }
}


/****/
class MyData2 implements Runnable {
    private shareData2 shareData2;

    public MyData2(shareData2 shareData2) {
        this.shareData2 = shareData2;
    }

    @Override
    public void run() {
        shareData2.increment();
    }
}

/****/
class MyData3 implements Runnable {
    private shareData2 shareData2;

    public MyData3(shareData2 shareData2) {
        this.shareData2 = shareData2;
    }

    @Override
    public void run() {
        shareData2.decrement();
    }
}



