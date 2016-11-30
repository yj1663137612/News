package edu.zhuoxin.util;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import edu.zhuoxin.inter.OnLoadResPonseListener;
import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * Created by ACER on 2016/11/3.
 * 各种请求网络的方法
 */

public class HttpUtil {
    //Volley的GET方法
    public void connectionVolleyGET(String url, final OnLoadResPonseListener listener, RequestQueue requestQueue) {


        //获取字符串请求
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            //请求成功的方法
            @Override
            public void onResponse(String response) {
                listener.getResPonse(response);
            }
        }, new Response.ErrorListener() {
            //请求失败的方法
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);
    }

    //Vooley的POST方法
    public void connectionVolleyPOST(String url, final String name, final String password, final OnLoadResPonseListener listener, RequestQueue requestQueue) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            //请求成功的方法
            @Override
            public void onResponse(String response) {
                listener.getResPonse(response);
            }
        }, new Response.ErrorListener() {
            //请求失败的方法
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            //Map集合用于储存数据
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("ver", "1");
                map.put("uid", name);
                map.put("pwd", password);
                map.put("device", "0");

                return map;
            }
        };

        requestQueue.add(stringRequest);
    }

    //Okhttp的GET方法
    public void connectionOkhttpGET() {
        //启动器
        OkHttpClient client = new OkHttpClient();
        //使用构造器，构建请求
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        //构建地址
        builder.url("");
        //因为这里是GET请求，所以不需要其他东西
        okhttp3.Request request = builder.build();
        //获取Call呼叫器
        Call call=client.newCall(request);
        //开始呼叫 考虑使用同步操作还是异步
          /*
        * 1.注意此时 如果要使用 同步 需要注意 call 模型在 execute（）的时候
        *   还是必须在子线程中执行，否则 报 网络工作在主线程异常
        *  2.如果使用异步 则需要注意 我们enqueue(new CallBack) 在callBack 的、
         *  时候，它是在 后台线程==守护线程， 也就是子线程中回调 所以 不能直接使用，必须
         *  发送至 主线程才能使用，那么这里可以考虑使用 接口回调
        *
        * */


    }
}
