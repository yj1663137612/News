package edu.zhuoxin.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import edu.zhuoxin.fragment.CenterFragment;
import edu.zhuoxin.fragment.LeftFragment;
import edu.zhuoxin.fragment.LoginrFragment;
import edu.zhuoxin.fragment.RightFragment;
import edu.zhuoxin.news.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView mImg_menu;
    ImageView mImg_login;
    CenterFragment centerFragment;
    RightFragment rightFragment;
    LeftFragment leftFragment;
    FragmentManager manager;
    SlidingMenu mSldm;
    TextView mTxt_title;
    private FragmentTransaction fragmentTransaction;
    //  Fragment    hiden    show   replace   add   remove?


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        //加载组件
        initView();
        mSldm = new SlidingMenu(this);
        //获取Fragment的对象
        centerFragment = new CenterFragment();
        rightFragment = new RightFragment();
        leftFragment = new LeftFragment();
        //获取V4包的Fragment的管理器
        manager = getSupportFragmentManager();
        //获取业务对象
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        //执行业务
        fragmentTransaction.replace(R.id.frag_center, centerFragment);
        //提交业务
        fragmentTransaction.commit();
        //设置SlidingMenu的属性
        setSlidingMenu();


    }

    private void setSlidingMenu() {

        //设置全屏可以滑动
        mSldm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //设置渐变效果，偏移度
        mSldm.setFadeDegree(0.5f);
        //设置菜单移动速度
        mSldm.setBehindScrollScale(1f);
        //设置滑动后距左边的距离
        mSldm.setBehindOffset(200);
        //设置左边滑动的图片,第一菜单
        mSldm.setMenu(R.layout.activity_menu);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.lyt_menu, leftFragment);
        transaction.commit();
        //设置右边滑动的图片 第二菜单
        mSldm.setSecondaryMenu(R.layout.activity_login);
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.lyt_login, rightFragment);

        fragmentTransaction.commit();
        //设置左右都有菜单
        mSldm.setMode(SlidingMenu.LEFT_RIGHT);
        mSldm.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        //设置阴影颜色
//        mSldm.setShadowDrawable();

    }


    private void initView() {
        mTxt_title = (TextView) findViewById(R.id.txt_main);
        mImg_login = (ImageView) findViewById(R.id.img_login);
        mImg_menu = (ImageView) findViewById(R.id.img_menu);
        mImg_menu.setOnClickListener(this);
        mImg_login.setOnClickListener(this);
    }

    //设置关闭侧滑的方法
    public void closeSlidingMenu() {
        if (mSldm.isMenuShowing()) {
            mSldm.showContent();

        } else {
            mSldm.showMenu();
        }
    }

    public void closeSecond() {
        if (mSldm.isSecondaryMenuShowing()) {
            mSldm.toggle();
        } else {
            mSldm.showSecondaryMenu();
        }
    }

    //设置title的方法
    public void setTitle(String title) {
        mTxt_title.setText(title);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_login:
                closeSecond();
                break;
            case R.id.img_menu:
                closeSlidingMenu();
                break;
        }
    }

}
