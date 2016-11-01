package com.doruemi.bean;

/**
 * Created by seekingL on 2016/7/27.
 *
 */
public class RecommandUser {

    public int userid;

    public String username;

    public int following;

    @Override
    public String toString() {
        return "RecommandUser{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                '}';
    }
}
