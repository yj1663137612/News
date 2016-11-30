package edu.zhuoxin.entity;

/**
 * Created by ACER on 2016/11/3.
 * 关于网络请求参数的封装
 */

public class HttpInfo {
    public static final String BASE_URL = "http://118.244.212.82:9092/newsClient/";
    //新闻列表的网络地址
    public static final String NEWS_LIST = "path/news_list?ver=1&subid=1&dir=1&nid=1&stamp=20140321&cnt=20";
    //注册的网络地址
    public static final String USER_REGISTER = "path/user_register?";
    //登录的网络地址
    public static final String USER_LOGIN = "path/user_login?";
    //找回密码的网络地址
    public static final String USER_FORGETPASS = "path/user_forgetpass?";
    //新闻分类的网络地址
    public static final String NEWS_SORT = "path/news_sort?";
    //发布评论的网络地址
    public static final String CMT_COMMIT = "path/cmt_commit?";
    //显示评论的网络地址
    public static final String CMT_LIST = "path/cmt_list?";
    //获取评论数量的网络地址
    public static final String CMT_NUM = "path/cmt_num?";
    //显示登录日志的网址
    public static final String USER_HOME = "path/user_home?";


}
