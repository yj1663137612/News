package edu.zhuoxin.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import edu.zhuoxin.news.R;

/**
 * Created by ACER on 2016/11/15.
 */

public class ShowActivity extends AppCompatActivity{
    ImageView mIng_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        //加载组件
        initView();
        //获取Intent的对象
        Intent intent = this.getIntent();
        Bitmap bitmap = intent.getParcelableExtra("img");
        mIng_show.setImageBitmap(bitmap);

    }

    private void initView() {
        mIng_show = (ImageView) findViewById(R.id.img_show);
    }
}
