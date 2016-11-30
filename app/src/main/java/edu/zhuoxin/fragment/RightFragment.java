package edu.zhuoxin.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import edu.zhuoxin.main.LoginActivity;
import edu.zhuoxin.main.MainActivity;
import edu.zhuoxin.news.R;

/**
 * Created by ACER on 2016/11/3.
 */

public class RightFragment extends Fragment implements View.OnClickListener {
    LinearLayout mLyt_rigt;
    LoginInterFragment loginInterFragment;
    FragmentManager manager;
    ImageView mImg_qq;
    ImageView mImg_weixin;
    ImageView mImg_weibo;
    ImageView mImg_xinlang;
    public static final String PASE_NAME = "file";
    TextView mTxt_right;
    ImageView mImg_right;
    int result;
    String name;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.acyivity_right, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLyt_rigt = (LinearLayout) view.findViewById(R.id.lyt_right);
        mTxt_right = (TextView) view.findViewById(R.id.txt_right);
        mImg_right = (ImageView) view.findViewById(R.id.img_right);
        mImg_qq = (ImageView) view.findViewById(R.id.img_qq);
        mImg_weixin = (ImageView) view.findViewById(R.id.img_weixin);
        mImg_weibo = (ImageView) view.findViewById(R.id.img_weibo);
        mImg_xinlang = (ImageView) view.findViewById(R.id.img_xinlang);
        mLyt_rigt.setOnClickListener(this);
        mImg_qq.setOnClickListener(this);
        mImg_weixin.setOnClickListener(this);
        mImg_weibo.setOnClickListener(this);
        mImg_xinlang.setOnClickListener(this);
        loginInterFragment = new LoginInterFragment();
        manager = getActivity().getSupportFragmentManager();
        //使用SharedPreferences获取保存的数
        SharedPreferences preferences = getActivity().getSharedPreferences(PASE_NAME, Context.MODE_PRIVATE);
        result = preferences.getInt("result", -1);
        name = preferences.getString("name", null);
        if (result == 0 && name != null) {
            mImg_right.setImageResource(R.mipmap.psb);
            mTxt_right.setText(name);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lyt_right:
                if (result == 0 && name != null) {
                    ((MainActivity) getActivity()).closeSecond();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.putExtra("name", name);
                    startActivity(intent);
                } else {
                    ((MainActivity) getActivity()).closeSecond();
                    FragmentTransaction fragmentTransaction = manager.beginTransaction();
                    fragmentTransaction.replace(R.id.frag_center, loginInterFragment);
                    fragmentTransaction.commit();

                }

                break;
            case R.id.img_qq:
                //初始化SDK
                ShareSDK.initSDK(getActivity());
                //实例化对象
                OnekeyShare onekeyShare = new OnekeyShare();
                //关闭SSO授权
                onekeyShare.disableSSOWhenAuthorize();
                onekeyShare.setTitle("尼古拉斯");
                onekeyShare.setText("赵四");
                onekeyShare.show(getActivity());
                break;
            case R.id.img_weixin:
                //初始化SDK
                ShareSDK.initSDK(getActivity());
                //实例化对象
                OnekeyShare share_one = new OnekeyShare();
                //关闭SSO授权
                share_one.disableSSOWhenAuthorize();
                share_one.setTitle("尼古拉斯");
                share_one.setText("赵四");
                share_one.show(getActivity());
                break;
            case R.id.img_weibo:
                //初始化SDK
                ShareSDK.initSDK(getActivity());
                //实例化对象
                OnekeyShare share = new OnekeyShare();
                //关闭SSO授权
                share.disableSSOWhenAuthorize();
                share.setTitle("尼古拉斯");
                share.setText("赵四");
                share.show(getActivity());
                break;
            case R.id.img_xinlang:
                //初始化SDK
                ShareSDK.initSDK(getActivity());
                //实例化对象
                OnekeyShare share_two = new OnekeyShare();
                //关闭SSO授权
                share_two.disableSSOWhenAuthorize();
                share_two.setTitle("尼古拉斯");
                share_two.setText("赵四");
                share_two.show(getActivity());
                break;
        }

    }
}
