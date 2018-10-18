package com.demo.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * cglib实现动态代理
 */

public class CglibProxy implements MethodInterceptor {

    /*Enhancer允许为非接口类型创建一个Java代理。Enhancer动态创建了给定类型的子类但是拦截了所有的方法。
    和Proxy不一样的是，不管是接口还是类他都能正常工作*/

    private Enhancer enhancer = new Enhancer();

    /*
     * getProxy(SuperClass.class)方法通过入参即父类的字节码，通过扩展父类的class来创建代理对象。
     * intercept()方法拦截所有目标类方法的调用，
     *   obj表示目标类的实例，
     *   method为目标类方法的反射对象，
     *   args为方法的动态入参，
     *   proxy为代理类实例。
     * proxy.invokeSuper(obj, args)通过代理类调用父类中的方法
     * */


    public Object getProxy(Class clazz) {
        //设置需要创建子类的类
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        //通过字节码技术动态创建子类实例
        return enhancer.create();
    }

    //实现MethodInterceptor接口方法
    public Object intercept(Object obj, Method method, Object[] args,
                            MethodProxy proxy) throws Throwable {
        prepare();
        Object result = proxy.invokeSuper(obj, args);//通过代理类调用父类中的方法
        clean();
        return result;
    }

    private void prepare() {
        System.out.println("CGLIB 准备碗筷！");
    }

    private void clean() {
        System.out.println("CGLIB 清洗碗筷！");
    }


}
