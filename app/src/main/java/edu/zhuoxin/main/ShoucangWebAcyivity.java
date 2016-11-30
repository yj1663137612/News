package edu.zhuoxin.main;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.zhuoxin.entity.CenterInfo;
import edu.zhuoxin.news.R;
import edu.zhuoxin.util.SqlUtil;

/**
 * Created by ACER on 2016/11/14.
 */

public class ShoucangWebAcyivity extends AppCompatActivity implements View.OnClickListener {
    ImageView mImg_back;
    ImageView mImg_popu;
    WebView mWeb;
    String mUri;
    ArrayList<CenterInfo> mList;
    PopupWindow popupWindow;
    SqlUtil sqlUtil;
    int mPosition;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoucang_web);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        //加载组件
        initView();
        //获取Intent对象，用来接收传递过来的数据
        Intent intent = this.getIntent();
        mUri = intent.getStringExtra("uri");

        mPosition = intent.getIntExtra("position", -1);
        //获取所有信息的集合
        sqlUtil = new SqlUtil(this);
        mList = sqlUtil.query();
        //设置跳转之后的网页
        setWeb();
        //设置popuWindow的界面
        setPopu();
    }

    private void setPopu() {
        popupWindow = new PopupWindow();
        //设置View
        View view = getLayoutInflater().inflate(R.layout.activity_shoucang_popu, null);
        TextView mTxt_delete = (TextView) view.findViewById(R.id.txt_delete_shoucang);
        mTxt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sqlUtil.delete(mList.get(mPosition).getNid());
                mList.remove(mPosition);
                Toast.makeText(ShoucangWebAcyivity.this, "取消成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ShoucangWebAcyivity.this, MainActivity.class);
                startActivity(intent);
                ShoucangWebAcyivity.this.finish();
            }
        });
        popupWindow.setContentView(view);
        //设置长和宽
        popupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置焦点
        popupWindow.setFocusable(true);
        //设置点击取消
        popupWindow.setOutsideTouchable(true);
        //至关重要的一步，设置背景图片，不然点击不了
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

    }

    private void setWeb() {
        //加载网页
        mWeb.loadUrl(mUri);
        //设置手机客户端显示样式
        WebSettings settings = mWeb.getSettings();
        //使用手机中的JS代码，自动识别是手机端还是网页端
//        settings.setJavaScriptEnabled(true);

        //设置自适应屏幕大小 两种设置的方式
        /**
         * WebSettings.LayoutAlgorithm.NARROW_COLUMNS  尽可能子一行中显示
         * WebSettings.LayoutAlgorithm.NORMAL  不添加任何的渲染
         * WebSettings.LayoutAlgorithm.SINGLE_COLUMN 就是在一行中显示，自动调整大小
         *
         *
         * */
        //第一种：通过布局参数设置
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //第二种：设置推荐使用的窗口
        settings.setUseWideViewPort(true);
        //自适应大小，并且可以放大或缩小
        settings.setLoadWithOverviewMode(true);
        //设置是否能放大或缩小
        settings.setSupportZoom(true);
        //设置放大缩小按钮
        settings.setBuiltInZoomControls(true);
        //设置调整窗口
        settings.setSupportMultipleWindows(true);
        //设置显示控制器
        settings.setDisplayZoomControls(true);
        //设置自己的客户端
        mWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mWeb.loadUrl(mUri);
                return true;
            }
        });

    }

    private void initView() {
        mImg_back = (ImageView) findViewById(R.id.img_back_shoucang);
        mImg_popu = (ImageView) findViewById(R.id.img_shoucang_popu);
        mWeb = (WebView) findViewById(R.id.shoucang_web);
        mImg_popu.setOnClickListener(this);
        mImg_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back_shoucang:

                Intent intent = new Intent(ShoucangWebAcyivity.this, MainActivity.class);
                startActivity(intent);
                this.finish();

                break;
            case R.id.img_shoucang_popu:
                popupWindow.showAsDropDown(mImg_popu);
                break;
        }
    }
}
