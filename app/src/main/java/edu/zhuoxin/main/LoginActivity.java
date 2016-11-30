package edu.zhuoxin.main;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

import edu.zhuoxin.adapter.LoginAdapter;
import edu.zhuoxin.entity.HttpInfo;
import edu.zhuoxin.entity.LoginCenterInfo;
import edu.zhuoxin.fragment.LoginInterFragment;
import edu.zhuoxin.inter.OnLoadResPonseListener;
import edu.zhuoxin.news.R;
import edu.zhuoxin.util.HttpUtil;

/**
 * Created by ACER on 2016/11/7.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnLoadResPonseListener {
    String mName;
    TextView mTXt_name;
    ImageView mImg_photo;
    Button mBtn_out;
    ListView mList;
    PopupWindow popupWindow;
    TextView mTxt_carema;
    TextView mTxt_picket;
    TextView mTxt_jifen;
    TextView mTxt_num;
    File file;
    String path;
    ImageView mImg_back;
    public static final String PASE_NAME = "file";
    ArrayList<LoginCenterInfo> list;
    SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_login);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();

        //加载组件
        initView();
        //获取Intent对象
        Intent intent = this.getIntent();
        mName = intent.getStringExtra("name");
        //获取请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        mTXt_name.setText(mName);
        //设置PopupWidow;
        setPopu();
        //使用SharedPreferences获取保存的数据
        preferences = this.getSharedPreferences(PASE_NAME, Context.MODE_PRIVATE);
        String token = preferences.getString("token", null);
        //获取登录日志
        new HttpUtil().connectionVolleyGET(HttpInfo.BASE_URL + HttpInfo.USER_HOME + "ver=1&imei=0&token=" + token, this, requestQueue);
    }

    private void setPopu() {
        popupWindow = new PopupWindow();
        //设置View
        View view = this.getLayoutInflater().inflate(R.layout.activity_login_popu, null);
        mTxt_carema = (TextView) view.findViewById(R.id.txt_camera);
        mTxt_picket = (TextView) view.findViewById(R.id.txt_picket);
        mTxt_carema.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用系统相机
                //从图库中获取照片
                /*分析：1：系统相机是系统的东西，调用的时候需要权限:android.permission.CAMERA
                * */
                //2：使用意图来调用相机:MediaStore媒体中心，ACTION_IMAGE_CAPTURE：照相机
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //3：照相的时候，照的照片需要保存下来，所以需要提供一个路径，用于保存照片
                //判断内存卡是否挂载
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    //获取地址
                    path = Environment.getExternalStorageDirectory().getPath();

                }
                //    获取文件
                file = new File(path + File.separator + System.currentTimeMillis());
                //设置保存路径  MediaStore.EXTRA_OUTPUT,:指的是媒体向出输出
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                //启动回调  requestCode:请求码，>=0
                startActivityForResult(intent, 1);

            }
        });
        mTxt_picket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //从图库中获取数据:Intent.ACTION_PICK：进入图库获取照片的意图
                Intent intent = new Intent(Intent.ACTION_PICK);
                //设置类型
                intent.setType("image/*");
                intent.putExtra("return-data", true);
                startActivityForResult(intent, 2);

            }
        });

        popupWindow.setContentView(view);
        //设置焦点
        popupWindow.setFocusable(true);
        //设置点击取消
        popupWindow.setOutsideTouchable(true);
        //至关重要的步骤，需要一个背景图片，没有背景图片，点击取消不了
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置宽高
        popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);

    }

    // 获取数据回传后得到的数据
//    requestCode请求码
//    resultCode：结果码
//    data 数据回传后封装的数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    //Parcelable序列化
                    cropFromFile(file);
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
//                    Bitmap bitmap = data.getParcelableExtra("data");
                    mImg_photo.setImageBitmap(bitmap);

                    break;
                case 2:
                    //通过内容提供者获取数据
                    ContentResolver contentResolver = getContentResolver();

//                    //根据地址值，拿数据
//                    Uri uri = data.getData();
//                    String[] array = {MediaStore.Images.Media.DATA};
//                    //获取游标
//                    Cursor cursor = contentResolver.query(uri, array, null, null, null);
//                    //将游标放到第一位
//                    cursor.moveToFirst();
//                    //获取路径
//                    String path = cursor.getString(cursor.getColumnIndex(array[0]));
//                    //关游标
//                    cursor.close();
//                    Bitmap bitmaps = BitmapFactory.decodeFile(path);
//                    mImg_photo.setImageBitmap(bitmaps);
                    cropPhoto(data.getData());
                    break;
                case 3:
                    Bitmap bitmap1 = data.getParcelableExtra("data");
                    mImg_photo.setImageBitmap(bitmap1);


                    break;
            }
        }

    }

    //剪切照相机的图片（方形）
    public void cropFromFile(File file) {
        //使用意图剪切照片
        Intent intent = new Intent();
        //设置要剪切的资源文件和类型
        intent.setDataAndType(Uri.fromFile(file), "image/*");
        //设置剪切意图
        intent.setAction("com.android.camera.action.CROP");
        //开启剪切
        intent.putExtra("crop", "true");
        //设置裁剪框的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //设置裁剪后输出的照片大小
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        //设置剪切圆形图片
        intent.putExtra("circleCrop","true");
        //设置返回数据
        intent.putExtra("return-data", true);
        //启动
        startActivityForResult(intent, 3);

    }

    //剪切从图库中获取的照片(方形)
    public void cropPhoto(Uri uri) {
        //使用意图剪切照片
        Intent intent = new Intent();
        //设置要剪切的资源文件和类型
        intent.setDataAndType(uri, "image/*");
        //设置剪切意图
        intent.setAction("com.android.camera.action.CROP");
        //开启剪切
        intent.putExtra("crop", "true");
        //设置裁剪框的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //设置裁剪后输出的照片大小
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        //设置剪切圆形图片
        intent.putExtra("circleCrop","true");
        //设置返回数据
        intent.putExtra("return-data", true);
        //启动
        startActivityForResult(intent, 3);

    }

    private void initView() {
        mBtn_out = (Button) findViewById(R.id.btn_out);
        mImg_back = (ImageView) findViewById(R.id.img_login_back);
        mTXt_name = (TextView) findViewById(R.id.txt_centerLogin_name);
        mImg_photo = (ImageView) findViewById(R.id.img_center_login);
        mList = (ListView) findViewById(R.id.lst_center_login);
        mTxt_jifen = (TextView) findViewById(R.id.txt_jifen_login);
        mTxt_num = (TextView) findViewById(R.id.txt_num_login);
        mBtn_out.setOnClickListener(this);
        mImg_photo.setOnClickListener(this);
        mImg_back.setOnClickListener(this);

    }

    //显示PopupWindow
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_center_login:
                popupWindow.showAtLocation(getLayoutInflater().inflate(R.layout.activity_center_login, null), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.img_login_back:
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                this.finish();
                break;
            case R.id.btn_out:
                //获取编辑器对象
                SharedPreferences.Editor edit = preferences.edit();
                edit.clear();
                edit.putInt("result", -1);
                //提交数据
                edit.commit();
                Toast.makeText(this, "退出成功", Toast.LENGTH_SHORT).show();
                Intent inten = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(inten);
                this.finish();
                break;
        }


    }


    @Override
    public void getResPonse(String response) {
        list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject data = jsonObject.getJSONObject("data");
            String loginlog = data.getString("loginlog");
            String integration = data.getString("integration");
            String comnum = data.getString("comnum");
            JSONArray array = new JSONArray(loginlog);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String time = object.getString("time");
                String substring = time.substring(0, 10);
                String address = object.getString("address");
                String device = object.getString("device");
                Log.e("//***", "" + substring + address + device);
                list.add(new LoginCenterInfo(substring, address, device));
            }
            mTxt_jifen.setText("积分：" + integration);
            mTxt_num.setText("跟帖数统计：" + comnum);
            //适配器
            LoginAdapter adapter = new LoginAdapter(this);
            adapter.setData(list);
            mList.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
