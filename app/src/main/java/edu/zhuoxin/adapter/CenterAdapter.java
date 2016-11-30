package edu.zhuoxin.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.zhuoxin.entity.CenterInfo;
import edu.zhuoxin.news.R;

/**
 * Created by ACER on 2016/10/31.
 */

public class CenterAdapter extends MyAdapter<CenterInfo> {
    int[] arr = {R.mipmap.g, R.mipmap.h, R.mipmap.i, R.mipmap.j, R.mipmap.k, R.mipmap.l, R.mipmap.m, R.mipmap.n, R.mipmap.o, R.mipmap.p,
            R.mipmap.q, R.mipmap.r, R.mipmap.s, R.mipmap.t, R.mipmap.u, R.mipmap.v, R.mipmap.w, R.mipmap.x, R.mipmap.y, R.mipmap.z,};
    Context context;

    public CenterAdapter(Context context) {
        super(context);
        this.context = context;

    }

    @Override
    public View setView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();

            convertView = inflater.inflate(R.layout.activity_adapter_center, parent, false);
            holder.mLyt_back = (LinearLayout) convertView.findViewById(R.id.LYT_daapter_center);
            holder.mCir = (CircleImageView) convertView.findViewById(R.id.cirl_adapter);
            holder.mTxt_title = (TextView) convertView.findViewById(R.id.txt_adapter_title);
            holder.mTxt_summary = (TextView) convertView.findViewById(R.id.txt_adapter_summary);
            holder.mTxt_stamp = (TextView) convertView.findViewById(R.id.txt_adapter_stamp);
            convertView.setTag(holder);

        } else {
            holder = (Holder) convertView.getTag();
        }

//        holder.mLyt_back.setBackgroundResource(arr[position % arr.length]);
        holder.mTxt_title.setText(mList.get(position).getTitle());
        holder.mTxt_summary.setText(mList.get(position).getSummary());
        holder.mTxt_stamp.setText(mList.get(position).getStamp());
        Picasso.with(convertView.getContext()).load(mList.get(position).getIcon()).into(holder.mCir);
        return convertView;
    }

    static class Holder {
        CircleImageView mCir;
        TextView mTxt_title;
        TextView mTxt_summary;
        TextView mTxt_stamp;
        LinearLayout mLyt_back;

    }
}
