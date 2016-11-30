package edu.zhuoxin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.zhuoxin.adapter.CenterAdapter;
import edu.zhuoxin.entity.CenterInfo;
import edu.zhuoxin.entity.HttpInfo;
import edu.zhuoxin.entity.TitleInfo;
import edu.zhuoxin.inter.OnLoadNewsListListener;
import edu.zhuoxin.main.MainActivity;
import edu.zhuoxin.main.WebActivity;
import edu.zhuoxin.news.R;
import edu.zhuoxin.util.NewsListTask;
import me.maxwin.view.XListView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ACER on 2016/10/31.
 */

public class CenterFragment extends Fragment implements XListView.IXListViewListener, OnLoadNewsListListener, AdapterView.OnItemClickListener {

    XListView mXlst_center;
    Handler handler;
    Gson gson;
    ArrayList<CenterInfo> mData;


    //新闻列表的地址
    public static final String LIST_PATH = "http://118.244.212.82:9092/newsClient/" +
            "news_list?ver=1&subid=1&dir=1&nid=1&stamp=20140321&cnt=20";
    //新闻分类的地址
    public static final String NEWS_SORT = HttpInfo.NEWS_LIST + HttpInfo.NEWS_SORT + "ver=1&imei=10";

    //开始创建Fragment
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_center, container, false);
    }


    //这个方法表示当前Fragment所在的View被创建成功
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setTitle("资讯");
        handler = new Handler();
        super.onViewCreated(view, savedInstanceState);
        mXlst_center = (XListView) view.findViewById(R.id.xlst_center);


        NewsListTask task = new NewsListTask();
        task.setOnLoadNewsListListener(this);
        //启动异步任务
        task.execute(LIST_PATH);
        //设置两个方法
        //上拉加载
        mXlst_center.setPullLoadEnable(true);
        //下拉刷新
        mXlst_center.setPullRefreshEnable(true);
        mXlst_center.setXListViewListener(this);
        mXlst_center.setOnItemClickListener(this);

    }

    //注意上拉刷新和下拉加载需要设置监听事件，否则停不下来否则无法加载更多
    @Override
    public void OnLoadNewsList(String string) {
        //使用GSON解析 获取数据源
        gson = new Gson();
        TitleInfo title = gson.fromJson(string, new TypeToken<TitleInfo>() {
        }.getType());
        mData = title.getData();
        for (CenterInfo centerInfo : mData) {
            String link = centerInfo.getLink();
        }

        CenterAdapter adapter = new CenterAdapter(getActivity());
        adapter.setData(mData);
        mXlst_center.setAdapter(adapter);
    }

    //刷新
    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stop();
            }
        }, 2000);
    }

    //加载更多
    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                stop();
            }
        }, 2000);
    }

    public void stop() {
        //停止加载
        mXlst_center.stopLoadMore();
        //停止刷新
        mXlst_center.stopRefresh();
        //设置时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mXlst_center.setRefreshTime(dateFormat.format(new Date(System.currentTimeMillis())));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //获取到跳转页面所需要的网址
        String url = mData.get(position - 1).getLink();
        //用意图实现页面跳转
        Intent intent = new Intent(getActivity(), WebActivity.class);
        //携带数据url跳转
        intent.putExtra("position", position - 1);
        intent.putExtra("url", url);
        //携带数据回传
        startActivityForResult(intent, 1);

    }

    //requestCode:响应吗
    //resultCode：结果码
    //data :封装了返回的数据
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:

                    break;
            }
        }
    }
}



