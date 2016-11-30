package edu.zhuoxin.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import edu.zhuoxin.adapter.GuidAdapter;
import edu.zhuoxin.news.R;

/**
 * Created by ACER on 2016/10/27.
 */

public class GuidActivity extends Activity implements ViewPager.OnPageChangeListener {
    //ViewPager,可以让图片左右滑动
    ViewPager mVip;
    //文件中的四个小点
    ImageView[] mImg;
    //可以滑动的图片
    ImageView []mRes;
    //可以滑动的图片的ID
    int[]mResId={R.mipmap.welcome,R.mipmap.wy,R.mipmap.bd,R.mipmap.small};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guid);

        //加载组件
        initView();
        //数据源
        initData();
        //适配器
        initAdapter();
    }

    private void initAdapter() {
        GuidAdapter adapter=new GuidAdapter(mRes);
        //启动适配器
        mVip.setAdapter(adapter);
        //刷新适配器
        adapter.notifyDataSetChanged();
    }

    private void initData() {
        //初始的时候，下标为0的小点为红色
        mImg[0].setImageResource(R.mipmap.a4);
        //四个可以滑动的图片
        mRes=new ImageView[4];
        //使用for循环，效率更高
        for (int i = 0; i <4 ; i++) {
            mRes[i]=new ImageView(this);
            mRes[i].setImageResource(mResId[i]);
        }
    }

    private void initView() {
        //可以让图片滑动
        mVip= (ViewPager) findViewById(R.id.vip_guid);
        //四个小点
        mImg=new ImageView[4];
        mImg[0]= (ImageView) findViewById(R.id.img_guid_one);
        mImg[1]= (ImageView) findViewById(R.id.img_guid_two);
        mImg[2]= (ImageView) findViewById(R.id.img_guid_three);
        mImg[3]= (ImageView) findViewById(R.id.img_guid_four);
        mVip.setOnPageChangeListener(this);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //图片滑动中执行的方法

    }

    @Override
    public void onPageSelected(int position) {//图片真正滑动到位之后执行的方法
        for (int i = 0; i < mImg.length ; i++) {
           //设置小黑点为黑色
            mImg[i].setImageResource(R.mipmap.deleat);
            if (position==3) {
                Intent intent=new Intent(GuidActivity.this,LogoActivity.class);
                startActivity(intent);
                GuidActivity.this.finish();
                overridePendingTransition(R.anim.enter,R.anim.exist);
            }
        }
        //设置图片为红色
        mImg[position].setImageResource(R.mipmap.a4);
    }

    @Override
    public void onPageScrollStateChanged(int state) {//图片滑动状态改变的时候执行的方法

    }
}
