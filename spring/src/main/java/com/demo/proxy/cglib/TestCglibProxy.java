package com.demo.proxy.cglib;

public class TestCglibProxy {
    public static void main(String[] args) {
        CglibProxy cglibProxy = new CglibProxy();
        RealSubject proxy = (RealSubject) cglibProxy.getProxy(RealSubject.class);
        proxy.eat();
    }

}
