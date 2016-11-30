package edu.zhuoxin.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import edu.zhuoxin.db.DBInfo;
import edu.zhuoxin.entity.CenterInfo;
import edu.zhuoxin.entity.CommentLeftInfo;

/**
 * Created by ACER on 2016/11/9.
 * 创建数据库的工具类
 */

public class SqlUtil {
    Context mContext;
    SQLHelper mSqlhelper;
    SQLiteDatabase database;

    public SqlUtil(Context context) {
        this.mContext = context;
        mSqlhelper = new SQLHelper(context);
    }

    //插入内容(新闻)
    public void insert(String summary, String icon, String stamp, String title, String nid, String link, String type) {
        //先判断数据库中的这条新闻是否存在
        //查询数据库，得到集合 遍历集合
        for (CenterInfo centerInfo : query()) {
            //判断数据库中的数据是否存在，如果存在，结束操作
            while (centerInfo.getNid().equals(nid)) {
                Toast.makeText(mContext, "已经收藏过该新闻", Toast.LENGTH_SHORT).show();
                return;
            }

        }
        //打开一个可写的数据库
        database = mSqlhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBInfo.SUMMARY, summary);
        values.put(DBInfo.ICON, icon);
        values.put(DBInfo.STAMP, stamp);
        values.put(DBInfo.TITLE, title);
        values.put(DBInfo.NID, nid);
        values.put(DBInfo.LINK, link);
        values.put(DBInfo.TYPE, type);
        database.insert(DBInfo.TABLE_NAME, null, values);

    }

    //插入内容(跟帖)
    public void insertComment(String title, String uid, String content, String stamp, String portrait, String icon, String cid) {
        //先判断数据库中的这条新闻是否存在
        //查询数据库，得到集合 遍历集合
        for (CommentLeftInfo commentLeftInfo : queryComment()) {
            //判断数据库中的数据是否存在，如果存在，结束操作
            while (commentLeftInfo.getNid().equals(cid)) {
                return;
            }

        }
        //打开一个可写的数据库
        database = mSqlhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBInfo.TITLE, title);
        values.put(DBInfo.UID, uid);
        values.put(DBInfo.CONTENT, content);
        values.put(DBInfo.TIME, stamp);
        values.put(DBInfo.PORTRAIT, portrait);
        values.put(DBInfo.ICON, icon);
        values.put(DBInfo.NID, cid);
        database.insert(DBInfo.COMMENT_NAME, null, values);
    }

    //删除内容(新闻)
    public void delete(String nid) {
        SQLiteDatabase database = mSqlhelper.getWritableDatabase();
        database.delete(DBInfo.TABLE_NAME, DBInfo.NID + " = ?", new String[]{nid});

    }

    //查询内容（新闻）
    public ArrayList<CenterInfo> query() {
        //定义一个集合，用于储存查询到的数据
        ArrayList<CenterInfo> list = new ArrayList<>();
        //打开一个可写的数据库
        SQLiteDatabase readableDatabase = mSqlhelper.getReadableDatabase();
        //得到游标
        Cursor cursor = readableDatabase.query(DBInfo.TABLE_NAME, null, null, null, null, null, null);
        // 通过游标获取数据 ColumnIndex代表数据库的列

        while (cursor.moveToNext()) {
            String summary = cursor.getString(cursor.getColumnIndex(DBInfo.SUMMARY));
            String icon = cursor.getString(cursor.getColumnIndex(DBInfo.ICON));
            String stamp = cursor.getString(cursor.getColumnIndex(DBInfo.STAMP));
            String title = cursor.getString(cursor.getColumnIndex(DBInfo.TITLE));
            String nid = cursor.getString(cursor.getColumnIndex(DBInfo.NID));
            String link = cursor.getString(cursor.getColumnIndex(DBInfo.LINK));
            String type = cursor.getString(cursor.getColumnIndex(DBInfo.TYPE));
            list.add(new CenterInfo(summary, icon, stamp, title, nid, link, type));
        }
        return list;

    }

    //查询内容(跟帖)
    public ArrayList<CommentLeftInfo> queryComment() {
        //定义一个集合，用于储存查询到的数据
        ArrayList<CommentLeftInfo> list = new ArrayList<>();
        //打开一个可写的数据库
        SQLiteDatabase readableDatabase = mSqlhelper.getReadableDatabase();
        //得到游标
        Cursor cursor = readableDatabase.query(DBInfo.COMMENT_NAME, null, null, null, null, null, null);
        // 通过游标获取数据 ColumnIndex代表数据库的列

        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex(DBInfo.TITLE));
            String icon = cursor.getString(cursor.getColumnIndex(DBInfo.ICON));
            String stamp = cursor.getString(cursor.getColumnIndex(DBInfo.TIME));
            String content = cursor.getString(cursor.getColumnIndex(DBInfo.CONTENT));
            String cid = cursor.getString(cursor.getColumnIndex(DBInfo.NID));
            String portrait = cursor.getString(cursor.getColumnIndex(DBInfo.PORTRAIT));
            String uid = cursor.getString(cursor.getColumnIndex(DBInfo.UID));
            list.add(new CommentLeftInfo(title, uid, content, stamp, portrait, icon, cid));
        }

        return list;

    }

}
