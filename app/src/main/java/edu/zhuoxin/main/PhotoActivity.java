package edu.zhuoxin.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import edu.zhuoxin.adapter.PhotoAdapter;
import edu.zhuoxin.entity.CenterInfo;
import edu.zhuoxin.entity.CommentInfo;
import edu.zhuoxin.entity.TitleInfo;
import edu.zhuoxin.inter.OnLoadNewsListListener;
import edu.zhuoxin.news.R;
import edu.zhuoxin.util.NewsListTask;

/**
 * Created by ACER on 2016/11/24.
 */

public class PhotoActivity extends AppCompatActivity implements OnLoadNewsListListener {
    RecyclerView mRec_right;
    RecyclerView mRec_left;
    Gson gson;
    ArrayList<CenterInfo> mData;
    //新闻列表的地址
    public static final String LIST_PATH = "http://118.244.212.82:9092/newsClient/" +
            "news_list?ver=1&subid=1&dir=1&nid=1&stamp=20140321&cnt=20";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
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
    }

    private void initView() {
        mRec_left = (RecyclerView) findViewById(R.id.recy_photo_left);
        mRec_right = (RecyclerView) findViewById(R.id.recy_photo_right);
    }

    @Override
    public void OnLoadNewsList(String string) {
        gson = new Gson();
        TitleInfo title = gson.fromJson(string, new TypeToken<TitleInfo>() {
        }.getType());
        mData = title.getData();
        PhotoAdapter adapter = new PhotoAdapter(mData, this);
        mRec_right.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL));
        mRec_left.setLayoutManager(new GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, true));
        mRec_left.setAdapter(adapter);
        mRec_right.setAdapter(adapter);
    }
}
