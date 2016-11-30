package edu.zhuoxin.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.zhuoxin.entity.CenterInfo;
import edu.zhuoxin.news.R;

/**
 * Created by ACER on 2016/11/10.
 */

public class ShoucangAdapter extends RecyclerView.Adapter<ShoucangAdapter.MyViewHold> {

    //建一个构造方法从，传递参数
    ArrayList<CenterInfo> mList;
    View view;
    //初始化接口对象
    private OnRecycleItemClickListener mOnItemClickListener = null;

    public ShoucangAdapter(ArrayList<CenterInfo> list) {
        this.mList = list;
    }

    //为RecyclerView的每个子item设置setOnClickListener
    public static interface OnRecycleItemClickListener {
        void onItemClick(int position);
    }

    //将可复用的View与ViewHold进行绑定
    @Override
    public MyViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adapter_shoucang, null, false);
        MyViewHold myViewHold = new MyViewHold(view);

        return myViewHold;
    }


    //将数据与ViewHold进行绑定
    @Override
    public void onBindViewHolder(MyViewHold holder, final int position) {

        holder.mTxt_title.setText(mList.get(position).getTitle());
        holder.mTxt_summary.setText(mList.get(position).getSummary());
        holder.mTxt_stamp.setText(mList.get(position).getStamp());
        Picasso.with(view.getContext()).load(mList.get(position).getIcon()).into(holder.mCir);
        //将数据保存在itemView的tag中，以便点击时获取
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    //定义一个设置listener的方法
    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    static class MyViewHold extends RecyclerView.ViewHolder {
        CircleImageView mCir;
        TextView mTxt_title;
        TextView mTxt_summary;
        TextView mTxt_stamp;
        //定义接口对象

        public MyViewHold(View itemView) {
            super(itemView);
            //加载组件
            mCir = (CircleImageView) itemView.findViewById(R.id.cirl_adapter_shoucang);
            mTxt_title = (TextView) itemView.findViewById(R.id.txt_shoucang_title);
            mTxt_summary = (TextView) itemView.findViewById(R.id.txt_shoucang_summary);
            mTxt_stamp = (TextView) itemView.findViewById(R.id.txt_shoucang_stamp);


        }


    }

}




