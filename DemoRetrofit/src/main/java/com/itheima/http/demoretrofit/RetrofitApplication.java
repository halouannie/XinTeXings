package com.itheima.http.demoretrofit;

import android.app.Application;

import com.itheima.http.demoretrofit.retrofit.ItheimaApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/7/10.
 */
public class RetrofitApplication extends Application{

    public static  ItheimaApi sApi;

    @Override
    public void onCreate() {
        super.onCreate();
        //》》1。retrofit对象创建



        //解析成对象
        Retrofit retrofit = new Retrofit.Builder()//
                .baseUrl(ItheimaApi.BASEURL)// 服务器访问路径 必须使用/结束
                .addConverterFactory(GsonConverterFactory.create())//添加Gson解析 如果服务器返回json数据 --Gson==>javaBean
                .build();//生成retrofit实例

        //retrofit.create();//参1.业务接口的class
        sApi = retrofit.create(ItheimaApi.class);
    }
}
