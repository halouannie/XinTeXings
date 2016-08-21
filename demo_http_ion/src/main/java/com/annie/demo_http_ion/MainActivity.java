package com.annie.demo_http_ion;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import org.json.JSONObject;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 */
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.button1)
    Button button1;
    @Bind(R.id.text)
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

    }

    /**!
     * 执行点击事件
     */
    public void start(View view) {
        //ion的get请求
       //ionGet();
        //ion的post请求
        //ionPost();
        //提交json对象
        //ionPostJson();
        //文件上传
        //updownFile();

        //文件下载
        download();

    }

    /**
     * 文件下载
     */
    private void download() {


        //获取网址
        String url = Url.DOWNLOAD;
        //创建文件路劲
        File file=new File("dsta/haha.jpg");
        //File file = new File(Environment.getExternalStorageDirectory(),"haha.jpg");

        //进度相关的回调对象.ProgressCallback
        ProgressCallback progressCallback= new ProgressCallback() {
            @Override
            public void onProgress(long downloaded, long total) {
                int percent= (int) (downloaded*100f/total+0.5f);
                System.out.println("下载百分比:"+percent+"%");//99.1 99.2
            }
        };

        Ion.with(this)
                .load(url)
                .progress(progressCallback)//为获取进度百分比.添加一个进度相关的回调对象
                .write(file)
                .setCallback(new FutureCallback<File>() {
                    @Override
                    public void onCompleted(Exception e, File result) {
                        //下载成功显示出文件路径,否则显示异常信息
                        text.setText(e==null?result.getAbsolutePath():e.getMessage());
                    }
                });//发起请求 把服务端的文件 写到本地
        //企业:apk更新/谷歌市场 下载apk
    }

    /**
     * 文件上传
     */
    private void updownFile() {
        //获取网址
        String url = Url.UPLOAD;
        //创建文件对象
        File file = new File(Environment.getExternalStorageDirectory() + "ha.jpg");
        Ion.with(this)
                .load(url)
                .uploadProgress(new ProgressCallback() {
                    @Override
                    public void onProgress(long downloaded, long total) {
                        int percent = (int) (downloaded * 100f / total + 0.5f);
                        System.out.println("当前进度:"+percent+"%");
                    }
                })//添加上传的回调对象获取过程百分比
                .setMultipartFile("file",file)//参1。服务端接收文件的变文件量名,2.文件
                .asString()
                .setCallback(callback);

        //回调对象:获取操作过程中的相关参数.
        //企业:微信朋友圈/qq空间说说 /商品晒图
        //微信 给一个朋友发图片 传视频  6%  面试 知识+功能
    }

    /**
     *  提交json对象
     */
    private void ionPostJson() {
        //获取网址
        String url = Url.POSTJSON;
        //创建json对象
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("smscount","100");
        jsonObject.addProperty("body","hahaha");//拼接json字符串
        Ion.with(this)//传入上下文
                .load(url)
                .setJsonObjectBody(jsonObject)//把json数据放到请求中
                .asString()//将inputstream转换为string
                .setCallback(callback);//设置回调函数,接收服务器返回的response对象

        //小结:调用setJsonObjectBody就可以将一个jsonobject对象放入请求
        //企业:备份功能/短信/数据
    }

    /**
     * ion的post请求
     */
    private void ionPost() {
        //获取网址
        String url = Url.POST;
        Ion.with(this)//传入上下文
                .load(url) //指定返回路径
                .setBodyParameter("name","Annie")
                .setBodyParameter("pwd","521") //把参数传递给服务器
                .asString()//将inputstream转换为string
                .setCallback(callback);//设置回调函数,接收服务器返回的response对象

        //企业:1.用户登录 使用post提交账号与密码&第三方登录的实现 sharedsdk
        //[http://sharesdk.mob.com/#/sharesdk]一般有要求同时掌握第三方登录集成

        //小结:进行post与进行get请求的具体细节都被封装在ion核心类中，所以开发者在调用框架的时候没有区别。
        //但是post提交参数必须放在请求表单里面，所以ion提供setBodyParameter(key-value)给开发者往请求表单里面添加参数
    }

    /**
     *  ion的get请求
     */
    private void ionGet() {
        // 获取网络地址
        String url = Url.GET;
        Ion.with(this)//传入上下文
                .load(url) //指定返回路径
                .asString() //将inputstream转换为string
                .setCallback(callback); //设置回调函数,接收服务器返回的response对象
        //企业: 1.新闻客户端:获取json 2.谷歌市场:获取json 都会用到该框架
        //3.基本上所有的get json界面都可以使用
    }

    //ion的回调函数
   private FutureCallback<String> callback = new FutureCallback<String>() {
        @Override
        public void onCompleted(Exception e, String result) {
            //回调函数一般在主线程执行,e:表示异常信息; result:表示返回的结果
            //如果请求 成功返回结果,否则打印异常信息
            text.setText(e == null ? result : e.getMessage());
        }
    };
}
