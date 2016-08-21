package com.annie.demo_http_retrofit;

import android.app.Application;

import com.annie.demo_http_retrofit.util.RetrofitApi;
import com.annie.demo_http_retrofit.util.Url;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ma on 2016/7/11.
 */
public class RetrofitApplication extends Application {

    public static  RetrofitApi retrofitApi;

    @Override
    public void onCreate() {
        super.onCreate();

        //获取 retrofit创建器 动态创建 class; Retrofit.Builder:代理对象创建器(注解处理器)
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Url.HOST)
                //GsonConverterFactory:gson 解析工厂 将 json 字符串解析成 java 对象
                .addConverterFactory(GsonConverterFactory.create());//将json转换成方法的返回对象
        //创建接口的代理对象,也就是接口的实现类
        retrofitApi = builder.build().create(RetrofitApi.class);
    }
}
