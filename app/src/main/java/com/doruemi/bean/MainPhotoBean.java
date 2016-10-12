package com.doruemi.bean;

import org.w3c.dom.Comment;

import java.util.List;

/**
 * Created by Administrator on 2016/9/14.
 */
public class MainPhotoBean {

    private String ErrorMsg;
    private String code;
    private int flag;
    private int start;
    private int steps;
    private int matchhis;

    private UnfollowInfo unfollowing;


    private List<PhotoInfoBean> list;


    private List<MatchListBean> matchlist;

    public String getErrorMsg() {
        return ErrorMsg;
    }

    public void setErrorMsg(String ErrorMsg) {
        this.ErrorMsg = ErrorMsg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getMatchhis() {
        return matchhis;
    }

    public void setMatchhis(int matchhis) {
        this.matchhis = matchhis;
    }

    public UnfollowInfo getUnfollowing() {
        return unfollowing;
    }

    public void setUnfollowing(UnfollowInfo unfollowing) {
        this.unfollowing = unfollowing;
    }

    public List<PhotoInfoBean> getList() {
        return list;
    }

    public void setList(List<PhotoInfoBean> list) {
        this.list = list;
    }

    public List<MatchListBean> getMatchlist() {
        return matchlist;
    }

    public void setMatchlist(List<MatchListBean> matchlist) {
        this.matchlist = matchlist;
    }

    public class UnfollowInfo {
        private int userid;
        private int id;
        private String username;
        private int isdaren;
        private String content;
        private String address;
        private String imgurl;
        private int height;
        private int laud;
        private int comment;
        private long addtime;

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getIsdaren() {
            return isdaren;
        }

        public void setIsdaren(int isdaren) {
            this.isdaren = isdaren;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getLaud() {
            return laud;
        }

        public void setLaud(int laud) {
            this.laud = laud;
        }

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }

        public long getAddtime() {
            return addtime;
        }

        public void setAddtime(long addtime) {
            this.addtime = addtime;
        }
    }

    public class PhotoInfoBean {
        public int id;
        public int userid;
        public String username;
        public String content;
        public String address;
        public String imgurl;
        public int height;
        public int laud;
        public int comment;
        public String addtime;
        public int following;
        public int haslaud;
        public int isdaren;
        public int isdownload;
        public List<Comment> comments;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getLaud() {
            return laud;
        }

        public void setLaud(int laud) {
            this.laud = laud;
        }

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public int getFollowing() {
            return following;
        }

        public void setFollowing(int following) {
            this.following = following;
        }

        public int getHaslaud() {
            return haslaud;
        }

        public void setHaslaud(int haslaud) {
            this.haslaud = haslaud;
        }

        public int getIsdaren() {
            return isdaren;
        }

        public void setIsdaren(int isdaren) {
            this.isdaren = isdaren;
        }

        public int getIsdownload() {
            return isdownload;
        }

        public void setIsdownload(int isdownload) {
            this.isdownload = isdownload;
        }

        public List<?> getComments() {
            return comments;
        }

        public void setComments(List<Comment> comments) {
            this.comments = comments;
        }
    }

    public static class MatchListBean {
        private int id;
        private String title;
        private String imgurl;
        private int eventtype;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public int getEventtype() {
            return eventtype;
        }

        public void setEventtype(int eventtype) {
            this.eventtype = eventtype;
        }
    }

    public class Comment {
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
}
