package com.demo;

public class Persion {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Persion() {
    }

    @Override
    public String toString() {
        return "Persion{" +
                "id=" + id +
                '}';
    }
}
