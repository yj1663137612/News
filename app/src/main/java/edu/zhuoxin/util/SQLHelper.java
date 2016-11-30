package edu.zhuoxin.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import edu.zhuoxin.db.DBInfo;

/**
 * Created by ACER on 2016/11/9.
 * 创建收藏新闻的数据库
 */

public class SQLHelper extends SQLiteOpenHelper {


    public SQLHelper(Context context) {
        super(context, DBInfo.DB_NAME, null, DBInfo.DB_VERSION);
    }

    //创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
        //用SQL语句，创建数据库表(收藏新闻)
        String sql = "create table %1$s(%2$s text,%3$s text,%4$s text,%5$s text,%6$s text,%7$s text,%8$s text)";
        String format = String.format(sql, DBInfo.TABLE_NAME, DBInfo.SUMMARY, DBInfo.ICON, DBInfo.STAMP, DBInfo.TITLE, DBInfo.NID, DBInfo.LINK, DBInfo.TYPE);
        //执行SQL语句，创建一张表
        db.execSQL(format);
        //用SQL语句，创建数据库表（跟帖内容）
        String sqlite = "create table %1$s(%2$s text,%3$s text,%4$s text,%5$s text,%6$s text,%7$s text,%8$s text)";
        String comment = String.format(sqlite, DBInfo.COMMENT_NAME, DBInfo.TITLE, DBInfo.UID, DBInfo.CONTENT, DBInfo.TIME, DBInfo.PORTRAIT, DBInfo.ICON,DBInfo.NID);
        //执行SQL语句，创建一张表
        db.execSQL(comment);

    }

    //更新数据库
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
