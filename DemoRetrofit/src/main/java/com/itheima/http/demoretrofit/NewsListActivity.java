package com.itheima.http.demoretrofit;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.itheima.http.demoretrofit.bean.CarNewsResult;
import com.itheima.http.demoretrofit.bean.NewsResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/10.
 */
public class NewsListActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        //加载布局

        //加载数据  1.http 2.json 3.javaBean
        Call<NewsResult> call = RetrofitApplication.sApi.getNewList();
        call.enqueue(new Callback<NewsResult>() {
            @Override
            public void onResponse(Call<NewsResult> call, Response<NewsResult> response) {

                NewsResult newsResult = response.body();
                System.out.println(newsResult);
            }

            @Override
            public void onFailure(Call<NewsResult> call, Throwable t) {

            }
        });
    }
}
