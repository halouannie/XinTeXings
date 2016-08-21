package com.annie.demo_http_retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.annie.demo_http_retrofit.bean.Result;
import com.annie.demo_http_retrofit.util.Url;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class getActivity extends AppCompatActivity {

    @Bind(R.id.button1)
    Button button1;
    @Bind(R.id.text)
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);
        ButterKnife.bind(this);
    }

    public void get(View view) {

        // 1.获取接口中相应方法对象的引用(异步方法)
        //由接口的实现类对象调用它当中的相应方法,返回该方法对象的引用
        //Call<Result> resultCall = RetrofitApplication.retrofitApi.get("Hello Annie", 21);

        //使用结合传传入参数
        Map<String ,String> map = new HashMap<>();
        map.put("name","Annie");
        map.put("age","22");
        map.put("leader","leader");
        Call<Result> resultCall = RetrofitApplication.retrofitApi.getMap(map);

        // 2.将引用放到请求队列中,发送请求
        resultCall.enqueue(new Callback<Result>() {
            @Override  //成功,可以在这里直接更新控件
            public void onResponse(Call<Result> call, Response<Result> response) {
                //到这里就说明json数据解析成功了,可以获取解析后的对象类型
                Result ressult = response.body(); //获取解析后的bean对象
                //text.setText(ressult == null ? "没有数据" : ressult.data.get("name")); //有数据就显示出name字段的值
                text.setText(ressult == null ? "没有数据" : ressult.data.get("name")+ ": "+ressult.data.get("leader"));
            }

            @Override //失败,可以在这里直接更新控件
            public void onFailure(Call<Result> call, Throwable t) {
                //请求失败,显示异常信息
                text.setText(t.getMessage());
            }
        });
    }
}
