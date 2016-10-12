package com.doruemi.bean;

/**
 * Created by Administrator on 2016/10/12.
 */
public class CommentBean {
    public int cid;
    public int article_id;
    public int pid;
    public int userid;
    public String username;
    public String content;
    public long addtime;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getAddtime() {
        return addtime;
    }

    public void setAddtime(long addtime) {
        this.addtime = addtime;
    }
}
