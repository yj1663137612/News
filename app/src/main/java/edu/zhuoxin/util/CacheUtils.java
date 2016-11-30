package edu.zhuoxin.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ACER on 2016/11/23.
 */

public class CacheUtils {
    static Map<String, SoftReference<Bitmap>> map = new HashMap<>();

    //软应用缓存
    //软应用的存储和获取方式
    public void cacheFromSoft(Bitmap bitmap) {
        //我们需要一张图片
        //将图片存到软应用中去
        SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(bitmap);
        map.put("uri", softReference);
        //软应用的获取方式
        SoftReference<Bitmap> soft = map.get("uri");
        Bitmap bit = soft.get();
    }

    //缓存中拿
    public Bitmap getbitmapfromsoft(String uri) {
        if (map.containsKey(uri)) {
            return map.get(uri).get();
        }
        return null;
    }

    Context context;

    //缓存到本地文件
    public void cacheFromFile(Bitmap bitmap) throws IOException {
        //获取缓存目录 文件夹
        File cacheDir = context.getCacheDir();
        String filename = "http://img.newyx.net/newspic/image/201311/22/535c764919.jpg";
        int index = filename.lastIndexOf("/");
        String substring = filename.substring(index);
        File file = new File(cacheDir, substring);

        //把一张照片保存到文件中去
        //第一步，创建一个文件
        //如果当前文件没有被创建
        if (!cacheDir.exists()) {
            //创建当前文件以及父目录
            cacheDir.mkdirs();
        }
        //如果当前文件没有被创建，
        if (!file.exists()) {
            //创建文件
            file.createNewFile();
        }
        OutputStream outputStream = new FileOutputStream(file);
        //通过流，将照片压缩到文件中去
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
        //刷新
        outputStream.flush();
        //关流
        outputStream.close();
    }

    //从文件中拿
    public Bitmap getbitmapfromfile(final String uri) {
        File cacheDir = context.getCacheDir();
        File[] files = cacheDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.equals(uri);
            }
        });
        if (files != null) {
            return BitmapFactory.decodeFile(files[0].getAbsolutePath());
        }
        return null;
    }

    Bitmap bitmap = null;

    //启动方法
    public Bitmap start(String uri, String imguri) {
//先从本地找
        //缓存里面找

        bitmap = getbitmapfromsoft(uri);
        if (bitmap != null) {
            return bitmap;
        }
        bitmap = getbitmapfromfile(uri);
        if (bitmap != null) {
            return bitmap;
        }
        getfromnet(imguri);
        return bitmap;
    }

    //网络请求
    public void getfromnet(String imguri) {
        ImageRequest imageRequest = new ImageRequest(imguri, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                if (response != null) {
                    //
                    cacheFromSoft(response);
                    //文件
                    try {
                        cacheFromFile(response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    bitmap = response;
                }

            }
        }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(imageRequest);
    }

    RequestQueue requestQueue;

    public CacheUtils(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public void cachelucache(String uri, Bitmap map) {
        LruCache<String, Bitmap> lrucache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / 8)) {
            //用于计算可用于显示的图片大小
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();

            }
        };
        lrucache.put(uri, map);
        lrucache.get(uri);

    }
}
