package com.example.demo_http_volley;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * 使用volley自定义request
 */
public class GsonRequest<T> extends Request<T> {
    private final Response.Listener<T> mListener;

    /**
     * Creates a new request with the given method.
     *
     * @param method the request {@link Method} to use
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public GsonRequest(int method, String url, Response.Listener<T> listener,
                         Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }

    //将javabean 传进来
    private Class<T> clazz;
    public GsonRequest(String url, Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        this(Method.GET, url, listener, errorListener);
        this.clazz = clazz;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        T t = null;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            //将字符串转成javabean对象
            t = new Gson().fromJson(parsed, clazz);
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return  Response.success(t, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }





}
