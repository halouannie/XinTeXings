package com.example.demo_http_volley;

import android.app.Application;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * 使用application实例对象来实现单例模式
 */
public class VolleyApplication extends Application{
    public static RequestQueue queue;
    @Override
    public void onCreate() {
        super.onCreate();
        // 2.创建请求队列,也是一个集合,有了这个集合,只要一个add操作,voll就给它分配一个线程
        queue = Volley.newRequestQueue(getApplicationContext());
    }
}
