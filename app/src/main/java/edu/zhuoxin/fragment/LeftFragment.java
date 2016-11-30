package edu.zhuoxin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import edu.zhuoxin.main.CommenLefttActivity;
import edu.zhuoxin.main.LocalActivity;
import edu.zhuoxin.main.MainActivity;
import edu.zhuoxin.main.PhotoActivity;
import edu.zhuoxin.news.R;

/**
 * Created by ACER on 2016/11/3.
 */

public class LeftFragment extends Fragment implements View.OnClickListener {
    LinearLayout mLyt_xinwen;
    LinearLayout mLyt_shoucang;
    LinearLayout mLyt_local;
    LinearLayout mLyt_comment;
    LinearLayout mLyt_photo;
    CenterFragment centerFragment;
    ShoucangFragment shoucangFragment;
    FragmentManager manager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_left, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLyt_xinwen = (LinearLayout) view.findViewById(R.id.lyt_xingwen);
        mLyt_shoucang = (LinearLayout) view.findViewById(R.id.lyt_shoucang);
        mLyt_local = (LinearLayout) view.findViewById(R.id.lyt_local);
        mLyt_comment = (LinearLayout) view.findViewById(R.id.lyt_comment);
        mLyt_photo = (LinearLayout) view.findViewById(R.id.lyt_photo);
        mLyt_xinwen.setOnClickListener(this);
        mLyt_shoucang.setOnClickListener(this);
        mLyt_local.setOnClickListener(this);
        mLyt_comment.setOnClickListener(this);
        mLyt_photo.setOnClickListener(this);
        //创建Fragment的对象
        centerFragment = new CenterFragment();
        shoucangFragment = new ShoucangFragment();
        //获取管理器
        manager = getActivity().getSupportFragmentManager();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lyt_xingwen:
                //获取业务对象
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                //执行业务
                fragmentTransaction.replace(R.id.frag_center, centerFragment);
                //提交业务
                fragmentTransaction.commit();
                ((MainActivity) getActivity()).closeSlidingMenu();
                break;
            case R.id.lyt_shoucang:
                //获取业务对象
                FragmentTransaction transaction = manager.beginTransaction();
                //执行业务
                transaction.replace(R.id.frag_center, shoucangFragment);
                //提交业务
                transaction.commit();
                ((MainActivity) getActivity()).closeSlidingMenu();
                break;
            case R.id.lyt_local:
                Intent intent = new Intent(getActivity(), LocalActivity.class);
                startActivity(intent);
                break;
            case R.id.lyt_comment:
                Intent inten = new Intent(getActivity(), CommenLefttActivity.class);
                startActivity(inten);
                break;
            case R.id.lyt_photo:
                Intent inte = new Intent(getActivity(), PhotoActivity.class);
                startActivity(inte);
                break;

        }
    }
}
