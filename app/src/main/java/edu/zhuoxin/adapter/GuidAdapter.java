package edu.zhuoxin.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by ACER on 2016/10/27.
 * ViewPager的适配器
 */

public class GuidAdapter extends PagerAdapter {
    ImageView []mRes;
    //构建一个构造方法，传递参数
    public  GuidAdapter(ImageView []mRes){
        this.mRes=mRes;
    }
    //获取数据源长度，也就是滑动次数
    @Override
    public int getCount() {
        return mRes==null?0:mRes.length;
    }


   //判断加载的View是不是来自obj
   @Override
    public boolean isViewFromObject(View view, Object object) {

        return view==object;
    }


    //初始化条目
     @Override
    public Object instantiateItem(ViewGroup container, int position) {
         //向container中添加数据，用于在ViewPager中显示
         ImageView imageView=mRes[position];
         container.addView(imageView);
        return imageView;
    }

    //销毁看不到的条目
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView(mRes[position]);
    }
}
