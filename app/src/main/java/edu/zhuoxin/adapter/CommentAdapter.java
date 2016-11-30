package edu.zhuoxin.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.zhuoxin.entity.CommentInfo;
import edu.zhuoxin.news.R;

/**
 * Created by ACER on 2016/11/18.
 */

public class CommentAdapter extends MyAdapter<CommentInfo> {
    Context mContext;

    public CommentAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public View setView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        if (convertView == null) {
            holder = new Holder();
            convertView = inflater.inflate(R.layout.activity_adapter_comment, parent, false);
            holder.mImg = (CircleImageView) convertView.findViewById(R.id.img_adapter_comment);
            holder.mTtx_uid = (TextView) convertView.findViewById(R.id.txt_comment_uid);
            holder.mTtx_stamp = (TextView) convertView.findViewById(R.id.txt_comment_stamp);
            holder.mTtx_content = (TextView) convertView.findViewById(R.id.txt_comment_content);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        //设置用户头像
        Picasso.with(convertView.getContext()).load(mList.get(position).getPortrait()).into(holder.mImg);
        holder.mTtx_uid.setText(mList.get(position).getUid());
        holder.mTtx_stamp.setText(mList.get(position).getStamp());
        holder.mTtx_content.setText(mList.get(position).getContent());

        return convertView;
    }

    static class Holder {
        CircleImageView mImg;
        TextView mTtx_uid;
        TextView mTtx_stamp;
        TextView mTtx_content;


    }
}
