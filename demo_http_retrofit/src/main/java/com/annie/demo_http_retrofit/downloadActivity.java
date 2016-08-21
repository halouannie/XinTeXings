package com.annie.demo_http_retrofit;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class downloadActivity extends AppCompatActivity {

    @Bind(R.id.button1)
    Button button1;
    @Bind(R.id.text)
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        ButterKnife.bind(this);
    }

    public void download(View view) {
        //获取异步对象
        Call<ResponseBody> call = RetrofitApplication.retrofitApi.download("mobilesafe.apk");

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                //获取返回数据:响应
                ResponseBody responseBody = response.body();
                InputStream inputStream = responseBody.byteStream();
                FileThread thread = new FileThread(inputStream);
                thread.start();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(downloadActivity.this, "" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case FileThread.UPDATE_PROGRESS:
                    long[] progress = (long[]) msg.obj;
                    int percent = (int) (progress[0] * 100 / progress[1] + 0.5f);
                    text.setText(percent + "%");
                    break;
            }
        }
    };

    private class FileThread extends Thread {
        private InputStream inputStream;
        public static final int UPDATE_PROGRESS = 1;

        public FileThread(InputStream input) {
            inputStream = input;
        }

        @Override
        public void run() {
            super.run();
            //保存文件
            File newFile = new File(Environment.getExternalStorageDirectory(), SystemClock.uptimeMillis() + ".apk");
            try {
                FileOutputStream out = new FileOutputStream(newFile);

                //
                int total = inputStream.available();
                long curr = 0;
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = inputStream.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                    //累计进度
                    curr = curr + len;
                    Message message = mHanlder.obtainMessage(UPDATE_PROGRESS);
                    message.obj = new long[]{curr, total};
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

}
