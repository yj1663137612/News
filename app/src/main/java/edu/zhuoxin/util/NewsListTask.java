package edu.zhuoxin.util;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import edu.zhuoxin.inter.OnLoadNewsListListener;

/**
 * Created by ACER on 2016/10/27.
 * 获取新闻列表的异步任务
 */

public class NewsListTask extends AsyncTask<String, Void, String> {
    HttpURLConnection mUrlConnection;
    InputStream mInputStream;
    //第一个执行的方法，在做准备工作
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    //是第二执行的方法 其中的参数和的数据类型及值 和我们开启这个 异步任务时 传入的参数有关系
    @Override
    protected String doInBackground(String... params) {

        try {
            //获取URL的对象
            URL url = new URL(params[0]);

            //打开链接
             mUrlConnection = (HttpURLConnection) url.openConnection();

            //获取响应码
            int responseCode = mUrlConnection.getResponseCode();

            if (responseCode == 200) {
                //获取一个输入流，读取数据
                mInputStream = mUrlConnection.getInputStream();
                byte[] bytes = new byte[1024];
                int length;
                //字符缓冲区，用于读取流中的内容
                StringBuffer buffer = new StringBuffer();
                while (((length = mInputStream.read(bytes)) != -1)) {
                    buffer.append(new String(bytes, 0, length));


                }
                return buffer.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            //断开连接
            if (mUrlConnection != null) {
                mUrlConnection.disconnect();
            }
            //关流
            if (mInputStream != null) {
                try {
                    mInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    //第四个方法，在第二个方法处理完以后，得到结果
    @Override
    protected void onPostExecute(String string) {
        if (string != null&onLoadNewsListListener!=null) {
            onLoadNewsListListener.OnLoadNewsList(string);
        }

        super.onPostExecute(string);

    }
    //写一个接口对象
    OnLoadNewsListListener onLoadNewsListListener;
    public void setOnLoadNewsListListener( OnLoadNewsListListener onLoadNewsListListener){
        this.onLoadNewsListListener=onLoadNewsListListener;
    }
}
