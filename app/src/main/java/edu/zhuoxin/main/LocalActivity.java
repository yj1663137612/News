package edu.zhuoxin.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;

import edu.zhuoxin.inter.OnLoadBitMapListener;
import edu.zhuoxin.news.R;
import edu.zhuoxin.view.CameraCallBack;

/**
 * Created by ACER on 2016/11/14.
 * 自定义照相机
 */

public class LocalActivity extends AppCompatActivity implements OnLoadBitMapListener, View.OnClickListener {
    SurfaceView mSur;
    SeekBar mSek;
    Button mBtn;
    CameraCallBack callBack;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        //加载组件
        initView();
        //SurfaceView的回调事件
        SurfaceHolder holder = mSur.getHolder();
        //设置参数
        holder.setSizeFromLayout();
        //设置屏幕长亮
        holder.setKeepScreenOn(true);

        holder.setFormat(PixelFormat.JPEG);
        //SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS设置Surface中的数据来自照相机
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        callBack = new CameraCallBack(this);
        holder.addCallback(callBack);



    }

    private void initView() {
        mSur = (SurfaceView) findViewById(R.id.suf_local);
        mSek = (SeekBar) findViewById(R.id.seb_local);
        mBtn = (Button) findViewById(R.id.btn_local_take);
        mBtn.setOnClickListener(this);
        mSek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                callBack.setZoom(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                int maxZoom = callBack.getMaxZoom();
                seekBar.setMax(maxZoom);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void getBitmap(Bitmap bitmap) {

        Intent intent = new Intent(this, ShowActivity.class);
        intent.putExtra("img", bitmap);
        startActivity(intent);
        Toast.makeText(this, "拍照成功", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {
        callBack.takePicture();

    }
}
