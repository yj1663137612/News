package edu.zhuoxin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import edu.zhuoxin.entity.CenterInfo;
import edu.zhuoxin.news.R;

/**
 * Created by ACER on 2016/11/24.
 */
//RecyclerView已经被适配器优化了，所以他继承的是自己的适配器
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHold> {
    Context context;
    ArrayList<CenterInfo> mData;
    //构造方法传递参数

    public PhotoAdapter(ArrayList<CenterInfo> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    //绑定  将可复用的View与ViewHolder进行绑定
    @Override
    public MyViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adapter_photo, null, false);
        return new MyViewHold(view);
    }

    //将数据与ViewHolder的进行绑定
    @Override
    public void onBindViewHolder(MyViewHold holder, int position) {
        Picasso.with(context).load(mData.get(position).getIcon()).into(holder.mImg);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class MyViewHold extends RecyclerView.ViewHolder {
        ImageView mImg;

        public MyViewHold(View itemView) {
            super(itemView);
            //加载组件
            mImg = (ImageView) itemView.findViewById(R.id.img_adapter_photo);
        }
    }
}
