package com.doruemi.bean;

/**
 * Created by c_xuwei-010 on 2016/12/12.
 */
public class ThemePhotoBean {

    public int id;

    public int cid;

    public int userid;

    public String username;

    public String content;

    public String imgurl;

    public int laud;

    public String atime;

    @Override
    public String toString() {
        return "ThemePhotoBean{" +
                "id=" + id +
                ", cid=" + cid +
                ", userid=" + userid +
                ", username='" + username + '\'' +
                ", content='" + content + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", laud=" + laud +
                ", atime=" + atime +
                '}';
    }
}
