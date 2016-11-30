package edu.zhuoxin.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import edu.zhuoxin.inter.OnLoadBitMapListener;

/**
 * Created by ACER on 2016/11/14.
 */

public class CameraCallBack implements SurfaceHolder.Callback {
    Camera camera;
    OnLoadBitMapListener listener;

    public CameraCallBack(OnLoadBitMapListener listener) {
        this.listener = listener;

    }

    //获取相机数量
    public int getNumbers(Context context) {
        int numberOfCameras = Camera.getNumberOfCameras();
        if (numberOfCameras == 0) {
            Toast.makeText(context, "相机不存在", Toast.LENGTH_SHORT).show();
        }
        if (numberOfCameras == 1) {
            return 0;
        }
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);//匹配
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                return i;
            }
        }
        return 0;
    }

    //设置焦距的方法
    public void setZoom(int zoom) {
        Camera.Parameters parameters = camera.getParameters();
        parameters.setZoom(zoom);
        camera.setParameters(parameters);
    }

    //拿到最大焦距
    public int getMaxZoom() {
        return camera.getParameters().getMaxZoom();
    }

    //拍照的方法
    public void takePicture() {

        camera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                //字节数组转为照片
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

                //存照片
                String parent = Environment.getExternalStorageDirectory().getPath();
                File file = new File(parent + File.separator + System.currentTimeMillis() + ".jpg");
                try {
                    FileOutputStream outputStream = new FileOutputStream(file);
                    //压缩照片
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                    outputStream.close();
                    if (listener != null) {
                        listener.getBitmap(bitmap);
                    }
                    camera.startPreview();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

    }

    //surface刚刚被创建的时候
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //打开相机
        //首先判断相机是否被占用
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
        camera = Camera.open();
        //设置照相机的各种参数
        Camera.Parameters parameters = camera.getParameters();
        //设置照片质量
        parameters.setJpegQuality(80);
        //设置聚焦模式 Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE:持续聚焦,自动聚焦
        parameters.setFlashMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);

        //判断是否存在闪光灯
        List<String> supportedFlashModes = camera.getParameters().getSupportedFlashModes();
        if (supportedFlashModes != null) {
            //设置闪光灯  Camera.Parameters.FLASH_MODE_ON:闪光灯打开状态
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
        }
        //设置相机旋转角度
        parameters.setRotation(180);
        parameters.setPictureFormat(ImageFormat.JPEG);
        //保存参数
        camera.setParameters(parameters);
        camera.setDisplayOrientation(90);
        try {
            camera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //开启预览
        camera.startPreview();
        //设置人脸识别
        camera.startFaceDetection();
    }

    //surface改变的时候
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    //surface被销毁的时候
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }
}
