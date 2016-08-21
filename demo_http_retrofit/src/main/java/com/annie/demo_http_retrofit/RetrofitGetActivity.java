package com.annie.demo_http_retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.annie.demo_http_retrofit.bean.Info;
import com.annie.demo_http_retrofit.util.RetrofitApi;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;

public class RetrofitGetActivity extends AppCompatActivity {

    @Bind(R.id.button1)
    Button button1;
    @Bind(R.id.text)
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_get);
        ButterKnife.bind(this);
    }

    //get请求
    public void retrofitGet(View view) {
       /* //获取 retrofit创建器 动态创建 class; Retrofit.Builder:代理对象创建器(注解处理器)
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(com.annie.demo_http_retrofit.util.Url.SERVER_HOST)
                //GsonConverterFactory:gson 解析工厂 将 json 字符串解析成 java 对象
                .addConverterFactory(GsonConverterFactory.create());//将json转换成方法的返回对象
        //创建接口的代理对象,也就是接口的实现类
        RetrofitApi retrofitApi = builder.build().create(RetrofitApi.class);*/

//=====================================================================================================

        //获取get(接口中定义的方法名)请求对象的引用,拿着这个引用可以去控制什么时候发送请求
        Call<Info> infoCall = RetrofitApplication.retrofitApi.retrofitGet();
        //将引用放到请求队列中,这时才在子线程中执行发请求的代码
        infoCall.enqueue(new Callback<Info>() {
            @Override  //成功,可以在这里直接更新控件
            public void onResponse(Call<Info> call, Response<Info> response) {
                //到这里就说明json数据解析成功了,可以获取解析后的对象类型
                Info info = response.body(); //获取解析后的bean对象
                text.setText(info==null?"没有数据":info.name); //有数据就显示出name字段的值
            }

            @Override  //失败,可以在这里直接更新控件
            public void onFailure(Call<Info> call, Throwable t) {
                //请求失败,显示异常信息
                text.setText(t.getMessage());
            }
        });
    }
}
