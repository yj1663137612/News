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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.zhuoxin.adapter.LoginAdapter;
import edu.zhuoxin.entity.HttpInfo;
import edu.zhuoxin.entity.LoginCenterInfo;
import edu.zhuoxin.inter.OnLoadResPonseListener;
import edu.zhuoxin.main.LoginActivity;
import edu.zhuoxin.main.MainActivity;
import edu.zhuoxin.news.R;
import edu.zhuoxin.util.HttpUtil;

/**
 * Created by ACER on 2016/11/2.
 */

public class LoginInterFragment extends Fragment implements View.OnClickListener, OnLoadResPonseListener {
    EditText mEdt_name;
    EditText mEdt_password;
    Button mBtn_login;
    Button mBtn_zhuce;
    Button mBtn_mima;
    FragmentManager manager;
    LoginZhuCeFragment loginZhuCeFragment;
    LoginMimaFragment loginMimaFragment;
    LinearLayout mLyt_fose;
    String name;
    String password;
    RequestQueue requestQueue;
    public static final String PASE_NAME = "file";
    SharedPreferences sharedPreferences;

    //开始创建Fragment
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.activity_fragment_login, container, false);
    }
    //当前Fragment所在的View被创建成功时的方法
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //加载组件
        mEdt_name = (EditText) view.findViewById(R.id.edt_login_name);
        mEdt_password = (EditText) view.findViewById(R.id.edt_login_password);
        mBtn_zhuce = (Button) view.findViewById(R.id.btn_zhuce);
        mBtn_mima = (Button) view.findViewById(R.id.btn_mima);
        mBtn_login = (Button) view.findViewById(R.id.btn_login);
        mLyt_fose = (LinearLayout) view.findViewById(R.id.lyt_login_fose);
        mLyt_fose.setFocusable(true);
        mBtn_zhuce.setOnClickListener(this);
        mBtn_mima.setOnClickListener(this);
        mBtn_login.setOnClickListener(this);
        //获取Fragment的对象
        loginZhuCeFragment = new LoginZhuCeFragment();
        loginMimaFragment = new LoginMimaFragment();
        //获取V4的Fragment的管理器
        manager = getActivity().getSupportFragmentManager();
        //获取输入进来的密码和账号的方法
        ((MainActivity) getActivity()).setTitle("用户登录");
        //获取请求对列
        requestQueue = Volley.newRequestQueue(getActivity());
    }


    //获取输入信息的方法
    public void getEdit() {
        name = mEdt_name.getText().toString();
        password = mEdt_password.getText().toString();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_zhuce:
                //获取业务对象
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                //执行业务
                fragmentTransaction.replace(R.id.frag_center, loginZhuCeFragment);
                //提交业务
                fragmentTransaction.commit();

                break;
            case R.id.btn_mima:

                //获取业务对象
                FragmentTransaction transaction = manager.beginTransaction();
                //执行业务
                transaction.replace(R.id.frag_center, loginMimaFragment);
                //提交业务
                transaction.commit();
                break;
            case R.id.btn_login:
                getEdit();
                new HttpUtil().connectionVolleyPOST(HttpInfo.BASE_URL + HttpInfo.USER_LOGIN, name, password, this, requestQueue);


                break;

        }
    }


    @Override
    public void getResPonse(String response) {

        try {
            //使用JSON解析
            JSONObject jsonObject = new JSONObject(response);
            String message = jsonObject.getString("message");
            int status = jsonObject.getInt("status");
            if (status == -1) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
            JSONObject data = jsonObject.getJSONObject("data");
            int result = data.getInt("result");
            String token = data.getString("token");
            String explain = data.getString("explain");
            //保存数据，使用sharpreference
            sharedPreferences = getActivity().getSharedPreferences(PASE_NAME, Context.MODE_PRIVATE);
            //获取编辑器的对象
            SharedPreferences.Editor edit = sharedPreferences.edit();
            //向里面添加数据
            edit.putInt("result", result);
            edit.putString("token", token);
            edit.putString("explain", explain);
            edit.putString("name", name);
            //提交数据
            edit.commit();
            if (result == 0) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.putExtra("name", name);


                Toast.makeText(getActivity(), "登录成功", Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
