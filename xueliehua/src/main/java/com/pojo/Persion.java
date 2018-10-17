package com.pojo;

import java.io.Serializable;

public class Persion  implements Serializable {
    //唯一解析id
    //无id java会自动生成
    private static final long serialVersionUID = -6098846545121451335L;
    //序列化不保存静态变量的状态---- 静态值改变，   以序列的可被修改

    public Persion() {
    }

    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Persion{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
