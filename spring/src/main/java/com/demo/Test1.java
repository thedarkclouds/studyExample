package com.demo;

public class Test1 {
    public static void main(String[] args) {
        Persion persion=new Persion();
        set(persion);
        System.out.println(persion.getId());


    }

    public static void set(Persion persion){
        persion.setId(100);
    }
}
