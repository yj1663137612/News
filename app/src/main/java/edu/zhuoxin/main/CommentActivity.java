package edu.zhuoxin.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.zhuoxin.adapter.CommentAdapter;
import edu.zhuoxin.entity.CommentInfo;
import edu.zhuoxin.entity.ConTitleInfo;
import edu.zhuoxin.entity.HttpInfo;
import edu.zhuoxin.inter.OnLoadNewsListListener;
import edu.zhuoxin.inter.OnLoadResPonseListener;
import edu.zhuoxin.news.R;
import edu.zhuoxin.util.HttpUtil;
import edu.zhuoxin.util.SqlUtil;
import me.maxwin.view.XListView;

/**
 * Created by ACER on 2016/11/15.
 * 新闻评论
 */

public class CommentActivity extends AppCompatActivity implements View.OnClickListener, OnLoadResPonseListener, XListView.IXListViewListener {
    ImageView mImg_back;
    String time;
    String nid;
    String title;
    String icon;
    String name;
    RequestQueue requestQueue;
    Gson gson;
    ArrayList<CommentInfo> mData;
    XListView mXlist;
    EditText mEdit_ctx;
    ImageView mImg_send;
    public static final String PASE_NAME = "file";
    Handler handler;
    String mCtx;
    CommentAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_comment);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();

        handler = new Handler();
        //加载组件
        initView();
        //获取请求队列
        requestQueue = Volley.newRequestQueue(this);
        //获取Intent的对象
        Intent intent = this.getIntent();
        time = intent.getStringExtra("time");
        nid = intent.getStringExtra("nid");
        title = intent.getStringExtra("title");
        icon = intent.getStringExtra("icon");
        Log.e("图片///", "" + icon);
        SharedPreferences preferences = this.getSharedPreferences(PASE_NAME, Context.MODE_PRIVATE);
        name = preferences.getString("name", null);
        new HttpUtil().connectionVolleyGET(HttpInfo.BASE_URL + HttpInfo.CMT_LIST + "ver=1&nid=" + nid +
                "&type=1&stamp=" + time + "&cid=1" + "&dir=1&cnt=20", this, requestQueue);
    }

    //获取输入内容的方法
    private void getCtx() {
        mCtx = mEdit_ctx.getText().toString();
    }

    private void initView() {
        mXlist = (XListView) findViewById(R.id.xlst_comment);
        //上拉加载
        mXlist.setPullLoadEnable(true);
//        //下拉刷新
        mXlist.setPullRefreshEnable(true);
        mXlist.setXListViewListener(this);
        mImg_back = (ImageView) findViewById(R.id.img_comment_back);
        mEdit_ctx = (EditText) findViewById(R.id.edit_comment_ctx);
        mImg_send = (ImageView) findViewById(R.id.img_commnet_send);
        mImg_send.setOnClickListener(this);
        mImg_back.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_comment_back:

                this.finish();
                break;
            case R.id.img_commnet_send:

                getCtx();
                //使用SharedPreferences获取保存的数据
                SharedPreferences preferences = this.getSharedPreferences(PASE_NAME, Context.MODE_PRIVATE);
                String token = preferences.getString("token", null);
                new HttpUtil().connectionVolleyGET(HttpInfo.BASE_URL + HttpInfo.CMT_COMMIT + "ver=1&nid=" + nid +
                        "&token=" + token + "&imei=0&ctx=" + mCtx, new OnLoadResPonseListener() {
                    @Override
                    public void getResPonse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            if (status == 0) {
                                new HttpUtil().connectionVolleyGET(HttpInfo.BASE_URL + HttpInfo.CMT_LIST + "ver=1&nid=" + nid +
                                        "&type=1&stamp=" + time + "&cid=" + mCid + "&dir=1&cnt=20", new OnLoadResPonseListener() {
                                    @Override
                                    public void getResPonse(String response) {
                                        gteGSON(response);
                                    }
                                }, requestQueue);
                                Toast.makeText(CommentActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                                mEdit_ctx.setText("");
                            } else {
                                Toast.makeText(CommentActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, requestQueue);
                break;
        }

    }

    String mCid;

    //解析数据的方法
    public void gteGSON(String response) {
        Log.e("评论数据", "///" + response);
        //使用GSON解析，获取数据
        gson = new Gson();
        ConTitleInfo comment = gson.fromJson(response, new TypeToken<ConTitleInfo>() {
        }.getType());
        //数据源
        mData = comment.getData();

        for (int i = 0; i < mData.size(); i++) {
            mCid = mData.get(i).getCid();
            //评论用户名
            String uid = mData.get(i).getUid();

            //评论内容
            String content = mData.get(i).getContent();
            //评论头像
            String portrait = mData.get(i).getPortrait();
            //评论时间
            String stamp = mData.get(i).getStamp();
            String cid = mData.get(i).getCid();
            if (uid.equals(name)) {
                new SqlUtil(this).insertComment(title, uid, content, stamp, portrait, icon, cid);
            }
        }
        //适配器
        adapter = new CommentAdapter(this);
        //传递数据
        adapter.setData(mData);
        //启动适配器
        mXlist.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getResPonse(String response) {
        gteGSON(response);
    }

    //注意上拉刷新和下拉加载需要设置监听事件，否则停不下来或者无法刷新更多
    @Override
    public void onRefresh() {//上拉刷新
        Log.e("=====", "====" + time);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stop();
                new HttpUtil().connectionVolleyGET(HttpInfo.BASE_URL + HttpInfo.CMT_LIST + "ver=1&nid=" + nid +
                        "&type=1&stamp=" + time + "&cid=" + mCid + "&dir=1", CommentActivity.this, requestQueue);
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {//下拉加载
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stop();
                new HttpUtil().connectionVolleyGET(HttpInfo.BASE_URL + HttpInfo.CMT_LIST + "ver=1&nid=" + nid +
                        "&type=1&stamp=" + time + "&cid=" + mCid + "&dir=2", CommentActivity.this, requestQueue);

            }
        }, 1000);
    }

    public void stop() {
        //停止加载
        mXlist.stopLoadMore();
        //停止刷新
        mXlist.stopRefresh();
        //设置时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mXlist.setRefreshTime(dateFormat.format(new Date(System.currentTimeMillis())));
    }
}
