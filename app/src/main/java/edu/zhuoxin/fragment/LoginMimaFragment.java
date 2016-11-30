package edu.zhuoxin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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

public class LoginMimaFragment extends Fragment implements View.OnClickListener, OnLoadResPonseListener {
    //开始创建Fragment
    EditText mEdit_email;
    Button mBtn_queren;
    String email;
    RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_fragment_mima, container, false);
    }
    //当前Fragment所在的View被创建成功时的方法


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).setTitle("忘记密码");
        mEdit_email = (EditText) view.findViewById(R.id.edt_mima_email);
        mBtn_queren = (Button) view.findViewById(R.id.btn_queren);
        mBtn_queren.setOnClickListener(this);
        //获取请求队列
        requestQueue = Volley.newRequestQueue(getActivity());
    }

    //获取输入信息的方法
    public void getEdit() {
        email = mEdit_email.getText().toString();
    }

    @Override
    public void onClick(View v) {
        getEdit();
        new HttpUtil().connectionVolleyGET(HttpInfo.BASE_URL + HttpInfo.USER_FORGETPASS + "ver=1" + "&email=" + email, this,
                requestQueue);

    }

    @Override
    public void getResPonse(String response) {

        try {
            //使用JSON解析
            JSONObject jsonObject=new JSONObject(response);
            String message = jsonObject.getString("message");
            int status = jsonObject.getInt("status");
            JSONObject data = jsonObject.getJSONObject("data");
            int result = data.getInt("result");
            String explain = data.getString("explain");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
