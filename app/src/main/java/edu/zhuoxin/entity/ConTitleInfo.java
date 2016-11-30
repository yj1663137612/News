package edu.zhuoxin.entity;

import java.util.ArrayList;

/**
 * Created by ACER on 2016/10/31.
 * 外圈数据的集合
 */

public class ConTitleInfo {
    String message;
    String status;
    ArrayList<CommentInfo> data;


    public ConTitleInfo() {

    }
    public ConTitleInfo(String message, String status, ArrayList<CommentInfo> data) {
        this.message = message;
        this.status = status;
        this.data = data;

    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<CommentInfo> getData() {
        return data;
    }



    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setData(ArrayList<CommentInfo> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TitleInfo{" +
                "message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", data=" + data +
                '}';
    }
}
