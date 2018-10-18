package com.demo.proxy.jdk;
//小星星单身
public class XiaoXinXin implements  Persion {

    private String sex="女";
    private  String name="小星星";


    @Override
    public void findBoyFriend() {
        System.out.println("找呀找，找到一个小对象");
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
