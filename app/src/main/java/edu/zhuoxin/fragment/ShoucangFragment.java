package edu.zhuoxin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.zhuoxin.adapter.ShoucangAdapter;
import edu.zhuoxin.entity.CenterInfo;
import edu.zhuoxin.main.MainActivity;
import edu.zhuoxin.main.ShoucangWebAcyivity;
import edu.zhuoxin.news.R;
import edu.zhuoxin.util.SqlUtil;

/**
 * Created by ACER on 2016/11/10.
 */

public class ShoucangFragment extends Fragment implements ShoucangAdapter.OnRecycleItemClickListener {
    RecyclerView mRecy;
    ArrayList<CenterInfo> list;
    SqlUtil sqlUti;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_fragment_shoucang, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecy = (RecyclerView) view.findViewById(R.id.recy_shoucang);
        //数据源
        sqlUti = new SqlUtil(getActivity());
        list = sqlUti.query();

        //适配器
        ShoucangAdapter adapter = new ShoucangAdapter(list);
        mRecy.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        mRecy.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

//        mRecy.setAdapter();

        ((MainActivity) getActivity()).setTitle("收藏新闻");
    }

    @Override
    public void onItemClick(int position) {
        //获取收藏网页的网址
        String uri = list.get(position).getLink();

        //用意图实现页面跳转
        Intent intent = new Intent(getActivity(), ShoucangWebAcyivity.class);
        intent.putExtra("uri", uri);
        intent.putExtra("position", position);
        //数据回传
        startActivityForResult(intent, 1);
        getActivity().finish();

    }


}
