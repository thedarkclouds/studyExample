package com.demo.proxy.jdk;

public class TestFindLove {
    public static void main(String[] args) {
        //   new XiaoXinXin().get;
        try {

            Persion obj =(Persion) new MeiPo().getInstance(new XiaoXinXin());
            System.out.println(obj.getClass());
            obj.findBoyFriend();

            //原理
            //1.拿到被代理对象，然后获取他的接口
            //2.JDk代理重新生成一个类，同时实现我们给的代理对象所实现的接口
            //3.把被代理对象的引用也拿到了
            //4.重新动态生成字节码

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
