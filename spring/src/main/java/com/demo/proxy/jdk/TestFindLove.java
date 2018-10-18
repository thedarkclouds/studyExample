package com.demo.proxy.jdk;

public class TestFindLove {
    public static void main(String[] args) {
        //   new XiaoXinXin().get;
        try {

            Persion obj =(Persion) new MeiPo().getInstance(new XiaoXinXin());
            System.out.println(obj.getClass());
            obj.findBoyFriend();

          /*  jdk 有动态代理支持.参看jdk文档中的Proxy类
            jdk动态代理的原理我先一句话概括下：通过类装载器拿到真实实现类和真实实现类的接口的字节码文件，
            然后构造生成一个代理类(是有真实类字节码文件的)。生成完后此时又回归待静态代理的uml结构上了。*/
            


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
