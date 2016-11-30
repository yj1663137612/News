package edu.zhuoxin.main;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import edu.zhuoxin.entity.CenterInfo;
import edu.zhuoxin.entity.HttpInfo;
import edu.zhuoxin.entity.TitleInfo;
import edu.zhuoxin.inter.OnLoadNewsListListener;
import edu.zhuoxin.inter.OnLoadResPonseListener;
import edu.zhuoxin.news.R;
import edu.zhuoxin.util.HttpUtil;
import edu.zhuoxin.util.NewsListTask;
import edu.zhuoxin.util.SqlUtil;

/**
 * Created by ACER on 2016/11/1.
 */

public class WebActivity extends AppCompatActivity implements View.OnClickListener, OnLoadNewsListListener, OnLoadResPonseListener {
    String mUrl;
    WebView mWeb;
    ImageView mImg_back;
    ImageView mImg_popu;
    TextView mTxt_tie;
    TextView mTxt_shoucang;
    TextView mTxt_qq;
    TextView mTxt_weixin;
    PopupWindow popupWindow;
    ArrayList<CenterInfo> mData;
    SqlUtil sqlUtil;
    int mPosition;
    String nid;
    String time;
    String mTitle;
    String mIcon;
    RequestQueue requestQueue;
    //新闻列表的地址
    public static final String LIST_PATH = "http://118.244.212.82:9092/newsClient/" +
            "news_list?ver=1&subid=1&dir=1&nid=1&stamp=20140321&cnt=20";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        //加载组件
        initView();
        NewsListTask task = new NewsListTask();
        task.setOnLoadNewsListListener(this);
        //启动异步任务
        task.execute(LIST_PATH);
        //获取到intent对象，
        Intent intent = this.getIntent();
        //接收跳转时所携带的数据
        mUrl = intent.getStringExtra("url");
        mPosition = intent.getIntExtra("position", -1);
        //获取请求队列
        requestQueue = Volley.newRequestQueue(this);
        sqlUtil = new SqlUtil(this);

        //设置跳转之后的网页
        setWeb();
        //设置PopuWindow
        setPopu();

    }

    private void setPopu() {
        popupWindow = new PopupWindow();
        //设置View
        View view = getLayoutInflater().inflate(R.layout.activity_web_popu, null);
        mTxt_shoucang = (TextView) view.findViewById(R.id.txt_web_shoucang);
        mTxt_shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WebActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                sqlUtil.insert(mData.get(mPosition).getSummary(), mData.get(mPosition).getIcon(), mData.get(mPosition).getStamp(),
                        mData.get(mPosition).getTitle(), mData.get(mPosition).getNid(), mData.get(mPosition).getLink(), mData.get(mPosition).getType());
            }
        });
        mTxt_qq = (TextView) view.findViewById(R.id.txt_web_qq);
        mTxt_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //初始化SDK
                ShareSDK.initSDK(WebActivity.this);
                //实例化对象
                OnekeyShare onekeyShare = new OnekeyShare();
                //关闭sso授权
                onekeyShare.disableSSOWhenAuthorize();
                onekeyShare.setTitle(mData.get(mPosition).getTitle());
                onekeyShare.setText(mData.get(mPosition).getSummary());
                //分享此内容的网络链接
                onekeyShare.setTitleUrl(mUrl);
                //启动分享GUT
                onekeyShare.show(WebActivity.this);
            }
        });
        mTxt_weixin = (TextView) view.findViewById(R.id.txt_web_weixin);
        mTxt_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//初始化SDK
                ShareSDK.initSDK(WebActivity.this);
                //实例化对象
                OnekeyShare onekeyShare = new OnekeyShare();
                //关闭sso授权
                onekeyShare.disableSSOWhenAuthorize();
                onekeyShare.setImageUrl(mData.get(mPosition).getIcon());
                onekeyShare.setTitle(mData.get(mPosition).getTitle());
                onekeyShare.setText(mData.get(mPosition).getSummary());
                //分享此内容的网络链接
                onekeyShare.setTitleUrl(mUrl);
                //启动分享GUT
                onekeyShare.show(WebActivity.this);
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

    public void setWeb() {
        //加载网页
        mWeb.loadUrl(mUrl);
        //设置手机客户端显示样式
        WebSettings settings = mWeb.getSettings();
//        //使用手机中的JS代码，自动识别是手机端还是网页端
        settings.setJavaScriptEnabled(true);

        //设置自适应屏幕大小 两种设置的方式
        /**
         * WebSettings.LayoutAlgorithm.NARROW_COLUMNS  尽可能子一行中显示
         * WebSettings.LayoutAlgorithm.NORMAL  不添加任何的渲染
         * WebSettings.LayoutAlgorithm.SINGLE_COLUMN 就是在一行中显示，自动调整大小
         *
         *
         * */
        //第一种：通过布局参数设置
////        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
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
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(mUrl);
                return super.shouldOverrideUrlLoading(view, mUrl);
            }
        });


    }

    private void initView() {
        mWeb = (WebView) findViewById(R.id.web);
        mImg_back = (ImageView) findViewById(R.id.img_web_back);
        mTxt_tie = (TextView) findViewById(R.id.txt_web);
        mImg_popu = (ImageView) findViewById(R.id.img_web);
        mTxt_tie.setOnClickListener(this);
        mImg_popu.setOnClickListener(this);
        mImg_back.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_web_back:
                this.setResult(-1);
                finish();
                break;
            case R.id.img_web:
                popupWindow.showAsDropDown(mImg_popu);
                break;
            case R.id.txt_web:
                Intent intent = new Intent(WebActivity.this, CommentActivity.class);
                intent.putExtra("time", time);
                intent.putExtra("nid", nid);
                intent.putExtra("title", mTitle);
                intent.putExtra("icon", mIcon);
                Log.e("图片",""+mIcon);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void OnLoadNewsList(String string) {
        //使用GSON解析 获取数据源
        Gson gson = new Gson();
        TitleInfo title = gson.fromJson(string, new TypeToken<TitleInfo>() {
        }.getType());
        mData = title.getData();
        nid = mData.get(mPosition).getNid();
        mTitle = mData.get(mPosition).getTitle();
        mIcon = mData.get(mPosition).getIcon();
        String stamp = mData.get(mPosition).getStamp();

        String yy = stamp.substring(0, 4);
        String MM = stamp.substring(5, 7);
        String dd = stamp.substring(8, 10);
        time = yy + MM + dd;
        new HttpUtil().connectionVolleyGET(HttpInfo.BASE_URL + HttpInfo.CMT_NUM +
                "ver=1&nid=" + mData.get(mPosition).getNid(), this, requestQueue);
    }

    @Override
    public void getResPonse(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            String data = jsonObject.getString("data");
            mTxt_tie.setText(data + "跟帖");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
