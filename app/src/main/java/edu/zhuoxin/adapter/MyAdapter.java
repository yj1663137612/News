package edu.zhuoxin.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by ACER on 2016/10/31.
 */

public abstract class MyAdapter<T> extends BaseAdapter {
    //数据集合
    ArrayList<T> mList;
    LayoutInflater inflater;

    //构造方法传递参数
    public MyAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    //传递集合，首先判断集合是否为空，如果不为空，先清理，为空的时候添加
    public void setData(ArrayList<T> mData) {
        if (mList != null) {
            mList.clear();
        }

        this.mList = mData;


    }

    @Override
    public int getCount() {

        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return setView(position, convertView, parent);
    }

    //抽象方法，可以自定义view
    public abstract View setView(int position, View convertView, ViewGroup parent);
}
