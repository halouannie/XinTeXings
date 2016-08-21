package com.annie.demo_http_retrofit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //get请求
    public void retrofitGet(View view) {
        Intent intent = new Intent(this,RetrofitGetActivity.class);
        startActivity(intent);

    }

    //get请求加强版
    public void get(View view) {
        Intent intent = new Intent(this,getActivity.class);
        startActivity(intent);
    }

    //post请求
    public void retrofitPost(View view) {
        Intent intent = new Intent(this,retrofitPostActivity.class);
        startActivity(intent);
    }

    //文件下载
    public void download(View view) {
        Intent intent = new Intent(this,downloadActivity.class);
        startActivity(intent);
    }


    //文件上传
    public void upload(View view) {
        Intent intent = new Intent(this,uploadActivity.class);
        startActivity(intent);
    }
}
