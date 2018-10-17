package com.demo.proxy.jdk;
//小星星单身
public class XiaoXinXin implements  Persion {

    private String sex="女";
    private  String name="小星星";

    @Override
    public void findLove() {
        System.out.println("我叫"+this.name+",性别："+this.sex);
        System.out.println("长得帅的");
        System.out.println("有房有车");
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
