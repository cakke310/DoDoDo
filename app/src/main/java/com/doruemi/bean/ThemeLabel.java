package com.doruemi.bean;

/**
 * Created by c_xuwei-010 on 2016/12/7.
 */
public class ThemeLabel {

    public int id;

    public int cid;

    public String labelname;

    public int count;

    public String editor;

    @Override
    public String toString() {
        return "ThemeLabel{" +
                "id=" + id +
                ", cid=" + cid +
                ", labelname='" + labelname + '\'' +
                ", count=" + count +
                ", editor='" + editor + '\'' +
                '}';
    }
}
