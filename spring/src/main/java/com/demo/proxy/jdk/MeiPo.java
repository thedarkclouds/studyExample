package com.demo.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//InvocationHandler 具有代理功能
//媒婆
public class MeiPo implements InvocationHandler {

    private Persion target;


    //获取被代理人个人资料
    public Object getInstance(Persion target) throws  Exception {
       this.target=target;
        Class clazz=target.getClass();
        System.out.println("被代理的对象=="+clazz);
        return   Proxy.newProxyInstance(target.getClass().getClassLoader(),clazz.getInterfaces(),this);

    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        bored();
        Object result = method.invoke(target, args);
        interesting();
        return result;
    }



    private void bored(){
        System.out.println("没对象，没人玩，无聊呀！");
    }

    private void interesting(){
        System.out.println("有对象，真有趣，每天都很开心！");
    }

}
