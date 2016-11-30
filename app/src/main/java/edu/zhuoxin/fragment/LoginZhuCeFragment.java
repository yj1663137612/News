package edu.zhuoxin.fragment;

import android.content.Context;
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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import edu.zhuoxin.entity.HttpInfo;
import edu.zhuoxin.inter.OnLoadResPonseListener;
import edu.zhuoxin.main.MainActivity;
import edu.zhuoxin.news.R;
import edu.zhuoxin.util.HttpUtil;

/**
 * Created by ACER on 2016/11/2.
 */

public class LoginZhuCeFragment extends Fragment implements View.OnClickListener, OnLoadResPonseListener {
    public static final String PASE_NAME = "file";
    EditText mEdt_email;
    EditText mEdt_name;
    EditText mEdt_password;
    String email;
    String name;
    String password;
    Button mBtn_zhuce;
    RequestQueue requestQueue;
    SharedPreferences sharedPreferences;
    LoginInterFragment loginInterFragment;
    FragmentManager manager;
    int result;
    String explain;
    //开始创建Fragment
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_fragment_zhuce, container, false);
    }
    //当前Fragment所在的View被创建成功时的方法


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //加载组件
        mEdt_email = (EditText) view.findViewById(R.id.edt_zhuce_emil);
        mEdt_name = (EditText) view.findViewById(R.id.edt_zhuce_name);
        mEdt_password = (EditText) view.findViewById(R.id.edt_zhuce_password);
        mBtn_zhuce = (Button) view.findViewById(R.id.btn_zhuce);
        mBtn_zhuce.setOnClickListener(this);
        //获取请求列队
        requestQueue = Volley.newRequestQueue(getActivity());
        //创建Fragment的对象
        loginInterFragment = new LoginInterFragment();
        //获取Fragment的管理器
        manager = getActivity().getSupportFragmentManager();
        ((MainActivity) getActivity()).setTitle("用户注册");

    }

    //获取输入信息的方法
    private void getEdit() {

        email = mEdt_email.getText().toString();

//        String regex = "  /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$/";
//        if (email.matches(regex)) {
        name = mEdt_name.getText().toString();
        password = mEdt_password.getText().toString();

//        } else {
//            Toast.makeText(getActivity(), "您输入的邮箱格式不正确，请重新输入", Toast.LENGTH_SHORT).show();
//
//        }

    }

    @Override
    public void onClick(View v) {

        getEdit();
        //调用Volley的方法
        new HttpUtil().connectionVolleyGET(HttpInfo.BASE_URL +
                HttpInfo.USER_REGISTER + "ver=1&uid=" + name + "&email=" + email + "&pwd=" + password, this, requestQueue);

    }

    @Override
    public void getResPonse(String response) {

        try {
            //JSON解析，对象型
            JSONObject jsonObject = new JSONObject(response);
            String message = jsonObject.getString("message");
            int status = jsonObject.getInt("status");
            if (status == -1) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
            JSONObject data = jsonObject.getJSONObject("data");
             result = data.getInt("result");
            String token = data.getString("token");
             explain = data.getString("explain");
            //保存数据，使用sharpreference
            sharedPreferences = getActivity().getSharedPreferences(PASE_NAME, Context.MODE_PRIVATE);
            //获取编辑器的对象
            SharedPreferences.Editor edit = sharedPreferences.edit();
            //向里面添加数据
            edit.putInt("result", result);
            edit.putString("token", token);
            edit.putString("explain", explain);
            //提交数据
            edit.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }



        switch (result) {
            case 0:
                //获取业务对象
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                //执行业务
                fragmentTransaction.replace(R.id.frag_center, loginInterFragment);
                //提交业务
                fragmentTransaction.commit();
                Toast.makeText(getActivity(), explain, Toast.LENGTH_SHORT).show();
                break;
            case -1:
                Toast.makeText(getActivity(), explain, Toast.LENGTH_SHORT).show();
                break;
            case -2:
                Toast.makeText(getActivity(), explain, Toast.LENGTH_SHORT).show();
                break;
            case -3:

                Toast.makeText(getActivity(), explain, Toast.LENGTH_SHORT).show();
                break;
            case 4:

                Toast.makeText(getActivity(), explain, Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
