package com.itheima.http.demoretrofit;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.http.demoretrofit.bean.Result;
import com.itheima.http.demoretrofit.bean.ResultResponse;
import com.itheima.http.demoretrofit.bean.ResultUpload;
import com.itheima.http.demoretrofit.bean.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView mTvPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvPercent = (TextView) findViewById(R.id.main_tv);
    }
    public void start(View view)
    {
        //retrofitGet();
       // retrofitPost();
        
        //retrofitDownLoad();

        retrofitUploadFiles();
    }

    private void retrofitUploadFiles() {

        Map<String, RequestBody> files=new HashMap<>();

        File file1=new File(Environment.getExternalStorageDirectory(),"1.png");
        File file2=new File(Environment.getExternalStorageDirectory(),"2.png");
        File file3=new File(Environment.getExternalStorageDirectory(),"3.png");

        //.txt  -> text/plain
        //.jpg -->image/jpeg
        MediaType  pngType=MediaType.parse("image/png");
        RequestBody body1=RequestBody.create(pngType,file1);//参1类型 参2文件
        RequestBody body2=RequestBody.create(pngType,file2);//参1类型 参2文件
        RequestBody body3=RequestBody.create(pngType,file3);//参1类型 参2文件

        files.put( "file\"; filename=\"" + file1.getName(), body1);
        files.put("file\"; filename=\"" +  file2.getName(), body2);
        files.put( "file\"; filename=\"" + file3.getName(), body3);
        //微信朋友圈
        //商品评论-晒图
        //Call包含线和的对象用来发送请求
        Call<ResultUpload> call = RetrofitApplication.sApi.uploadFiles(files);

        call.enqueue(new Callback<ResultUpload>() {
            @Override
            public void onResponse(Call<ResultUpload> call, Response<ResultUpload> response) {

                ResultUpload body = response.body();

                Toast.makeText(MainActivity.this, ""+body.getData(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ResultUpload> call, Throwable t) {
                Toast.makeText(MainActivity.this, ""+t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void retrofitDownLoad() {

        //获取异步对象
        Call<ResponseBody> call = RetrofitApplication.sApi.download("zaowu.apk");

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                
                //获取返回数据:响应
                ResponseBody responseBody = response.body();
                InputStream inputStream = responseBody.byteStream();
                FileThread thread=new FileThread(inputStream);
                thread.start();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, ""+t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Handler mHanlder=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case FileThread.UPDATE_PROGRESS:
                    long[] progress= (long[]) msg.obj;
                    int percent= (int) (progress[0]*100/progress[1]+0.5f);
                    mTvPercent.setText(percent+"%");
                    break;
            }
        }
    };
    private class FileThread extends Thread
    {
        private InputStream inputStream;
        public static final int UPDATE_PROGRESS=1;

        public FileThread(InputStream input)
        {
            inputStream=input;
        }

        @Override
        public void run() {
            super.run();
            //保存文件
            File newFile=new File(Environment.getExternalStorageDirectory(), SystemClock.uptimeMillis()+".apk");
            try {
                FileOutputStream out=new FileOutputStream(newFile);

                //
                int total=inputStream.available();
                long curr=0;
                int len=0;
                byte[] buffer=new byte[1024];
                while((len=inputStream.read(buffer))!=-1)
                {
                    out.write(buffer,0,len);
                    //累计进度
                    curr=curr+len;
                    Message message = mHanlder.obtainMessage(UPDATE_PROGRESS);
                    message.obj=new long[]{curr,total};
                    mHanlder.sendMessage(message);//--->handleMessage
                }
                out.flush();
                out.close();
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void retrofitPost()
    {
        //异步方法
      //  Call<ResultLogin> call = RetrofitApplication.sApi.login("翁", "111");

        //        Map<String,String> map=new HashMap<>();
        //        map.put("username","白龙马");
        //        map.put("pwd","111");
        //        Call<ResultLogin> call = RetrofitApplication.sApi.login(map);
        User user=new User();
        user.username="白龙马";
        user.pwd="11111";
        Call<ResultResponse> call = RetrofitApplication.sApi.login(user);
        call.enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                ResultResponse body = response.body();
                Toast.makeText(MainActivity.this, ""+body.getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, ""+t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void retrofitGet() {
        //Call<Result> getMethod = RetrofitApplication.sApi.getJson();
        //@Query

        //Call<Result> getMethod = RetrofitApplication.sApi.getQuery("白龙马",1000);

        Map<String,String> map=new HashMap<>();
        map.put("name","白龙马");
        map.put("age","11");
        Call<Result> getMethod = RetrofitApplication.sApi.getQueryMap(map);
        //发请求
        getMethod.enqueue(new Callback<Result>() {
            //成功  直接更新控件
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                   //Response返回数据的封装
                Result body = response.body();
                Toast.makeText(MainActivity.this, body==null?"请求数据为空.":body.msg+" "+body.data.get("name"), Toast.LENGTH_SHORT).show();
            }
           //失败 直接更新控件
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });//参1回调对象CallBack
    }
}
