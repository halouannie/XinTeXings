package com.annie.demo_http_retrofit.util;

import com.annie.demo_http_retrofit.bean.Info;
import com.annie.demo_http_retrofit.bean.Login;
import com.annie.demo_http_retrofit.bean.Result;
import com.annie.demo_http_retrofit.bean.ResultUpload;
import com.annie.demo_http_retrofit.bean.User;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 接口没有实现代码.代码都是由retrofit框架动态生成.开发者只需要定义输入参数与返回参数
 */
public interface RetrofitApi {
    //此路径不能以/开头
    @GET("test?name=Annie")
    public Call<Info> retrofitGet();
    //call: retrofit的一个包装类,可以发送异步请求
    //@GET标注在方法表示这个方法将来向服务器发起get请求,后面括号内表示路径

//=====================================================================================

    //{"msg":"成功收到信息","data":{"name":"黑马","age":"1"}}==>Gson==>javaBean
    //@Query 直接拼接get的请求参数
    @GET("get")
    public Call<Result> get(@Query("name")String name,@Query("age") int age );

//===========================================================================================
//post请求比get请求 post对参数进行网络编码
    //参数较多时,使用集合传入参数
    @GET("get")
    public Call<Result> getMap(@QueryMap Map<String,String> map);

//=========================================================================================
    //post请求
    @FormUrlEncoded  //url编码
    @POST("post")  //post--普通方式提交表单参数
    public Call<Login> post(@Field("name") String username, @Field("pwd")int pwd);

    @FormUrlEncoded  //url编码
    @POST("post")  //post--Map集合方式提交表单参数
    public Call<Login> postMap(@FieldMap Map<String,String> map);

    //@FormUrlEncoded  //url编码  这种方式不能加编码!!!
    @POST("post")  //post--JavaBean方式提交表单参数
    public Call<Login> postBean(@Body User user);


//=================================================================

    //http://192.168.90.88:8080/webapi/360zhushou.apk
    //360zhushou.apk还要求
    //什么是ResponseBody?服务端把数据返回的封装
    //@Path根据占位符替换值
    @GET("file/{filename}")
    public Call<ResponseBody> download(@Path("filename")String filename);

    //360zhushou.apk  file/360zhushou.apk

    //http://192.168.90.88:8080/webapi/upload
//    Multipart提交一个二进制文件/
    //mnt/sdcard/1.jpg
    //mnt/sdcard/2.jpg
    //mnt/sdcard/3.jpg
    //file字段
    // RequestBody封装了二进制文件 与ResponseBody区别 前者提交 后者返回
    //@PartMap可以指定Map集合放入表单

    @Multipart
    @POST("upload")
    public Call<ResultUpload> uploadFiles(@PartMap Map<String, RequestBody> fileMap);
}
