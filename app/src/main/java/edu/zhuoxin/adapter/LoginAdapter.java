package edu.zhuoxin.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.zhuoxin.entity.LoginCenterInfo;
import edu.zhuoxin.news.R;

/**
 * Created by ACER on 2016/11/21.
 */

public class LoginAdapter extends MyAdapter<LoginCenterInfo> {
    Context mContext;

    public LoginAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public View setView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = inflater.inflate(R.layout.activity_adapter_login, parent, false);
            holder.mTxt_time = (TextView) convertView.findViewById(R.id.txt_login_time);
            holder.mTxt_adress = (TextView) convertView.findViewById(R.id.txt_login_adress);
            holder.mTxt = (TextView) convertView.findViewById(R.id.txt_login_adapter);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.mTxt_time.setText(mList.get(position).getTime());
        holder.mTxt_adress.setText(mList.get(position).getAddress());
        holder.mTxt.setText(mList.get(position).getDevice());
        return convertView;
    }

    static class Holder {
        TextView mTxt_time;
        TextView mTxt_adress;
        TextView mTxt;
    }
}
