package edu.zhuoxin.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.zhuoxin.entity.CommentInfo;
import edu.zhuoxin.entity.CommentLeftInfo;
import edu.zhuoxin.news.R;

/**
 * Created by ACER on 2016/11/18.
 * 跟帖信息的适配器
 */

public class CommentLeftAdapter extends MyAdapter<CommentLeftInfo> {
    Context mContext;

    public CommentLeftAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public View setView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = inflater.inflate(R.layout.activity_adapter_commentleft, parent, false);
            holder.mImg_icon = (CircleImageView) convertView.findViewById(R.id.cimg_commentleft_icon);
            holder.mTtx_title = (TextView) convertView.findViewById(R.id.txt_commentleft_title);
            holder.mImg_portrai = (CircleImageView) convertView.findViewById(R.id.cimg_commentleft_portrai);
            holder.mTtx_uid = (TextView) convertView.findViewById(R.id.txt_commentleft_uid);
            holder.mTtx_stamp = (TextView) convertView.findViewById(R.id.txt_commentleft_stamp);
            holder.mTtx_content = (TextView) convertView.findViewById(R.id.txt_commentleft_content);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        //设置用户头像
        Picasso.with(convertView.getContext()).load(mList.get(position).getIcon()).into(holder.mImg_icon);
        holder.mTtx_title.setText(mList.get(position).getTitle());
        Picasso.with(convertView.getContext()).load(mList.get(position).getPortrait()).into(holder.mImg_portrai);
        holder.mTtx_uid.setText(mList.get(position).getUid());
        holder.mTtx_stamp.setText(mList.get(position).getStamp());
        holder.mTtx_content.setText(mList.get(position).getContent());
        return convertView;
    }

    static class Holder {
        CircleImageView mImg_icon;
        TextView mTtx_title;
        CircleImageView mImg_portrai;
        TextView mTtx_uid;
        TextView mTtx_stamp;
        TextView mTtx_content;

    }
}
