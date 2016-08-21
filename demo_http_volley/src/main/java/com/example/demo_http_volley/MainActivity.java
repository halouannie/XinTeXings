package com.example.demo_http_volley;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.button1)
    Button button1;
    @Bind(R.id.text)
    TextView text;
    @Bind(R.id.imageview)
    ImageView imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    //点点击事件
    public void start(View view) {
        //get方式请求
        //volleyGet();

        //post方式请求
        // volleyPost();

        //加载图片
       // volleyImage();

        //decodeImage();
        //图片的压缩原理
       //testDecodeImage();

        //使用自定义的request 发送网络请求
        gsonReequest();
    }

    /**
     *  使用自定义的request 发送网络请求
     */
    private void gsonReequest() {
        String url = "http://10.0.2.2:8080/apitest/test";
        // 1.创建请求对象
        GsonRequest<People> request = new GsonRequest<People>(url, People.class, new Response.Listener<People>() {
            @Override
            public void onResponse(People response) {
                text.setText(response.getName());
            }
        }, errorListener);
        // 2.创建请求队列
        RequestQueue queue = Volley.newRequestQueue(this);
        // 3.发送请求
        queue.add(request);
    }


    /**
     * 图片的压缩原理
     */
    private void testDecodeImage() {
        // 在加载图片之前获取app所占用的总内存
        long preMemory = Runtime.getRuntime().totalMemory();

        // 1.获取bitmap
        // Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
        // R.drawable.fast);
        // 使用Options来解析图片
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 设置只解析图片的宽高参数
        opts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ha,opts);
        // 得到原始图片的宽高
        int imgWidth = opts.outWidth;
        int imgHeight = opts.outHeight;
        // 设置加载图片的采样比例
        opts.inSampleSize = caculateSampleSize(imgWidth, imgHeight);

        // 再次进行加载真正的图片
        opts.inJustDecodeBounds = false;// 注意要设置为诶false

        // 设置解析图片使用的模式为RGB_565，默认是ARGB_8888
        opts.inPreferredConfig = Bitmap.Config.RGB_565;

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ha,
                opts);

        // 加载图片之后获取app占用内存
        long currentMemory = Runtime.getRuntime().totalMemory();
        long costMemory = currentMemory - preMemory;// 计算加载图片所消耗的内存
        text.setText("加载图片所消耗内存："
                + Formatter.formatFileSize(this, costMemory));
        System.out.println(preMemory +" , "+currentMemory);
        // 2.显示图片
        imageview.setImageBitmap(bitmap);
    }

    /**
     * 计算图片加载的采样比，参考ImageView的宽高和原始图片的宽高
     *
     * @return
     */
    private int caculateSampleSize(int imgWidth, int imgHeight) {
        int sampleSize = 0;
        // 拿图片的宽高和ImageView的宽高进行比较
        if (imageview.getWidth() > imageview.getHeight()) {
            // 参考ImageView的宽进行缩放
            sampleSize = imgWidth / imageview.getWidth();
        } else {
            // 参考ImageView的高进行缩放
            sampleSize = imgHeight / imageview.getHeight();
        }

        // 判断sampleSize小于1的情况
        if (sampleSize < 1) {
            sampleSize = 1;
        }
        return sampleSize;
    }


    /**
     * 加载图片
     * 参3和参4: 必须指定,表示图片进行压缩处理时参考的宽高
     * 参5: 指定图片的缩放类型; 参6:565内存比8888节省一半,但不会解析图片中的透明像素
     */
    private void volleyImage() {
        String url = "http://10.0.2.2:8080/hah.jpg";
        // 1.创建图片请求对象
        ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageview.setImageBitmap(response);
            }
        }, 300, 200, ImageView.ScaleType.FIT_XY, Bitmap.Config.RGB_565, errorListener);

        // 2.创建请求队列
        RequestQueue queue = Volley.newRequestQueue(this);
        // 3.发送请求
        queue.add(request);
    }

    /**
     * post方式请求
     */
    private void volleyPost() {
        //获取网址
        String url = Url.Post;
        //post方式提交数据需要通过集合,将数据放到一个map集合中
        final Map<String, String> data = new HashMap<>();
        data.put("username", "Annie");
        data.put("password", "21");
        StringRequest request = new StringRequest(StringRequest.Method.POST, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return data; //发送post只能重写StringRequest的getParams方法返回表单
            }
        };
        //通过application实例对象创建queue的对象
        RequestQueue queue = VolleyApplication.queue;
        //发送请求
        queue.add(request);

        //小结: volley可以用在新闻客户端,但不适合用在谷歌市场(因为要下载apk,获取进度值)
        // JsonObjectRequest:返回结果为jsonObject但是项目中一般使用google.gson 阿里fastjson
    }

    /**
     * get方式请求
     */
    private void volleyGet() {
        //获取网址
        String url = Url.GET;

        //因为服务端返回的数据是String
        // 1.参1方法  get/post 参2 访问路径 因为返回有多种情况volley 参3 正确响应监听器用来处理正确的情况 参4.错误监听器处理出错的情况.
        StringRequest request = new StringRequest(StringRequest.Method.GET, url, listener, errorListener);
        // 2.创建请求队列,也是一个集合,有了这个集合,只要一个add操作,voll就给它分配一个线程
        //RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        //通过application实例对象来创建RequestQueue对象
        RequestQueue queue = VolleyApplication.queue;
        // 3. 发送操作
        queue.add(request);

        //小结:新闻客户端,谷歌市场获取数据都可以用
    }


//-----------------------------------------------------

    //创建响应监听器:为了获取服务端返回的响应
    Response.Listener<String> listener = new Response.Listener<String>() {
        //响应数据返回
        @Override
        public void onResponse(String response) {
            text.setText(response);
        }
    };

    //为了获取服务端返回的错误信息响应（1.网络不通 2.服务端未正常启动）
    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            text.setText(error.getMessage());
        }
    };

}
