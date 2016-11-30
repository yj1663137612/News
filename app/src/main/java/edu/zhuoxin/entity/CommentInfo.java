package edu.zhuoxin.entity;

/**
 * Created by ACER on 2016/11/18.
 */

public class CommentInfo {
    String uid;
    String content;
    String stamp;
    String cid;
    String portrait;

    public CommentInfo() {

    }

    public CommentInfo(String uid, String content, String stamp, String cid, String portrait) {
        this.uid = uid;
        this.content = content;
        this.stamp = stamp;
        this.cid = cid;
        this.portrait = portrait;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    @Override
    public String toString() {
        return "CommentInfo{" +
                "uid='" + uid + '\'' +
                ", content='" + content + '\'' +
                ", stamp='" + stamp + '\'' +
                ", cid='" + cid + '\'' +
                ", portrait='" + portrait + '\'' +
                '}';
    }
}
