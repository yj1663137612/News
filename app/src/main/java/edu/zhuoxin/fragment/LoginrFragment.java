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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import edu.zhuoxin.entity.HttpInfo;
import edu.zhuoxin.inter.OnLoadResPonseListener;
import edu.zhuoxin.main.LoginActivity;
import edu.zhuoxin.main.MainActivity;
import edu.zhuoxin.news.R;
import edu.zhuoxin.util.HttpUtil;

/**
 * Created by ACER on 2016/11/2.
 */

public class LoginrFragment extends Fragment {


    //开始创建Fragment
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_login, container, false);
    }
    //当前Fragment所在的View被创建成功时的方法


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}
