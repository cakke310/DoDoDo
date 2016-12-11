package com.doruemi.bean;

/**
 * Created by Administrator on 2016-12-10.
 */
public class MyFragmentBean {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public MyFragmentBean() {

    }

    public MyFragmentBean(String name) {
        this.name = name;
    }
}
