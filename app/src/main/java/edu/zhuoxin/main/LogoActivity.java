package edu.zhuoxin.main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import edu.zhuoxin.news.R;

/**
 * Created by ACER on 2016/10/27.
 */

public class LogoActivity extends Activity {
    //文件名
    private static final String PATH_NAME="MyPrefsFile";
    //是不是第一次
    private static final String IS_FIRST="first";
    ImageView mImg_logo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences shar = this.getSharedPreferences(PATH_NAME, MODE_PRIVATE);
        boolean flag = shar.getBoolean(IS_FIRST, true);
        if (flag) {//第一次进入
            Intent intent=new Intent(LogoActivity.this,GuidActivity.class);
            startActivity(intent);
            //获取编辑器对象
            SharedPreferences.Editor edit = shar.edit();
            //用编辑器对象向里面添加对象
            edit.putBoolean(IS_FIRST,false);
            //提交数据
            edit.commit();
            //结束当前页面
           this.finish();
        }else {//不是第一次进入
            setContentView(R.layout.activity_logo);
            mImg_logo= (ImageView) findViewById(R.id.img_logo);
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.change);
            mImg_logo.startAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                  Intent intent=new Intent(LogoActivity.this,MainActivity.class);
                    startActivity(intent);
                    LogoActivity.this.finish();
                    overridePendingTransition(R.anim.enter,R.anim.exist);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }
}
