package edu.zhuoxin.entity;

/**
 * Created by ACER on 2016/11/23.
 */

public class CommentLeftInfo {
    String title;
    String icon;
    String content;
    String stamp;
    String portrait;
    String uid;
    String nid;

    public CommentLeftInfo() {

    }

    public CommentLeftInfo(String title, String uid, String content, String stamp, String portrait, String icon, String cid) {
        this.title = title;
        this.icon = icon;
        this.content = content;
        this.stamp = stamp;
        this.portrait = portrait;
        this.uid = uid;
        this.nid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    @Override
    public String toString() {
        return "CommentLeftInfo{" +
                "title='" + title + '\'' +
                ", uid='" + uid + '\'' +
                ", content='" + content + '\'' +
                ", stamp='" + stamp + '\'' +
                ", portrait='" + portrait + '\'' +
                ", icon='" + icon + '\'' +
                ", nid='" + nid + '\'' +
                '}';
    }
}
