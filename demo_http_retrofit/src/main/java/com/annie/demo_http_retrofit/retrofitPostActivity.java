package com.annie.demo_http_retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.annie.demo_http_retrofit.bean.Login;
import com.annie.demo_http_retrofit.bean.Result;
import com.annie.demo_http_retrofit.bean.User;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class retrofitPostActivity extends AppCompatActivity {

    @Bind(R.id.button1)
    Button button1;
    @Bind(R.id.text)
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_post);
        ButterKnife.bind(this);
    }

    //post请求
    public void retrofitPost(View view) {
        // 1.异步方法
       // Call<Login> annie = RetrofitApplication.retrofitApi.post("Annie", 21);

        /*//map集合方式提交表单数据
        Map<String,String> map = new HashMap<>();
        map.put("username","Annie");
        map.put("pwd","21");
        Call<Login> annie = RetrofitApplication.retrofitApi.postMap(map);*/

        //JavaBean方式提交表单数据
        User user = new User();
        user.username = "annie";
        user.pwd = "21";
        Call<Login> annie = RetrofitApplication.retrofitApi.postBean(user);

        //2.将引用放到请求队列中,发送请求
        annie.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                //到这里就说明json数据解析成功了,可以获取解析后的对象类型
                Login ressult = response.body(); //获取解析后的bean对象
                text.setText(ressult == null ? "没有数据" : ressult.getMsg()); //有数据就显示出name字段的值
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                //请求失败,显示异常信息
                text.setText(t.getMessage());
            }
        });
    }
}
