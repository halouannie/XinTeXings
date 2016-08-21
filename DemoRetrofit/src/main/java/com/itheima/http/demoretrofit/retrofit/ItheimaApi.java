package com.itheima.http.demoretrofit.retrofit;

import com.itheima.http.demoretrofit.bean.CarNewsResult;
import com.itheima.http.demoretrofit.bean.NewsResult;
import com.itheima.http.demoretrofit.bean.Result;
import com.itheima.http.demoretrofit.bean.ResultResponse;
import com.itheima.http.demoretrofit.bean.ResultUpload;
import com.itheima.http.demoretrofit.bean.User;

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
 * Created by Administrator on 2016/7/10.
 */
public interface ItheimaApi {
public  static final String BASEURL="http://192.168.90.88:8080/webapi/";
    //public  static final String BASEURL="http://192.168.90.55:8080/zhbj/";
    @GET("get?name=黑马&age=1")
    public Call<Result> getJson();
    //Call:可以送异步请求,
    //@GET标注在方法表示这个方法将来向服务器发起get请求
    //String url="http://192.168.90.88:8080/webapi/get?name=黑马&age=1";
    //{"msg":"成功收到信息","data":{"name":"黑马","age":"1"}}==>Gson==>javaBean
    //@Query 直接拼接get的请求参数
    @GET("get")
    public Call<Result> getQuery(@Query("name")String name,@Query("age") int age);
    @GET("get")
    public Call<Result> getQueryMap(@QueryMap Map<String,String> map);
    //name 白龙马  age 11 sex male ==> get?name=白马龙&age=1&sex=male
    @GET("10105/list_1.json")
    public Call<CarNewsResult> getCarNewList();
    @GET("10105/list_1.json")
    public Call<NewsResult> getNewList();

    //http://192.168.90.88:8080/webapi/post?username=%E7%BF%81&pwd=1
    //post请求比get请求 post对参数进行网络编码
    //{"msg":"成功收到信息","data":"246cb629-b460-4f35-b995-667da868cf36"}

    //方式@Field
    @FormUrlEncoded
    @POST("post")
    public Call<ResultResponse> login(@Field("username") String username, @Field("pwd")String pwd);

    //方式 FieldMap提交表单集合
    @FormUrlEncoded
    @POST("post")
    public Call<ResultResponse> login(@FieldMap Map<String,String> map);

    //@Body把变量以json的方式提交给服务端
    @POST("post")
    public Call<ResultResponse> login(@Body User user);


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
