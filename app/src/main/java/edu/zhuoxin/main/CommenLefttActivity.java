package edu.zhuoxin.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import edu.zhuoxin.adapter.CommentLeftAdapter;
import edu.zhuoxin.entity.CommentLeftInfo;
import edu.zhuoxin.news.R;
import edu.zhuoxin.util.SqlUtil;

/**
 * Created by ACER on 2016/11/23.
 */

public class CommenLefttActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView mImg_back;
    ListView mLst_content;
    ArrayList<CommentLeftInfo> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commentleft_activity);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        //加载组件
        initView();
        //数据源
        mList = new SqlUtil(this).queryComment();

        //适配器
        CommentLeftAdapter adapter = new CommentLeftAdapter(this);
        adapter.setData(mList);
        mLst_content.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void initView() {
        mImg_back = (ImageView) findViewById(R.id.img_commnetleft_back);
        mLst_content = (ListView) findViewById(R.id.lst_commentleft_content);
        mImg_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.finish();
    }
}
