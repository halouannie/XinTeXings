package com.example.demo_http_volley;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageListener;

/**
 *  volley的二次封装
 */
/*
public class VolleyHelper {
	RequestQueue queue;

	private static VolleyHelper mInstance;
	VolleyMemeryCache memeryCache = new VolleyMemeryCache();
	ImageLoader loader = new ImageLoader(queue, memeryCache);
	private VolleyHelper(Context context) {
		queue = Volley.newRequestQueue(context);
	}
	

	public static VolleyHelper getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new VolleyHelper(context);
		}
		return mInstance;
	}

	*/
/**
	 * 执行Gson的请求
	 * 
	 * @param url
	 * @param clazz
	 * @param listener
	 * @param errorListener
	 *//*

	public <T> void executeRequest(String url, Class<T> clazz,
			Listener<T> listener, ErrorListener errorListener) {
		GsonRequest<T> gsonRequest = new GsonRequest<T>(url, clazz, listener,
				errorListener);
		queue.add(gsonRequest);
	}

	public <T> void executeRequest(Object tag,String url, Class<T> clazz,
			Listener<T> listener, ErrorListener errorListener) {
		GsonRequest<T> gsonRequest = new GsonRequest<T>(url, clazz, listener,
				errorListener);
		gsonRequest.setTag(tag);
		queue.add(gsonRequest);
	}
	
	*/
/**
	 * 加载图片的封装
	 * @param url
	 * @param imageView
	 *//*

	public void loadImage(String url, ImageView imageView) {
		ImageListener imageListener = ImageLoader.getImageListener(imageView,
				R.drawable.ic_launcher, R.drawable.ic_launcher);
		// 加载图片
		loader.get(url, imageListener);
	}
	
	public void cancelRequest(Object tag){
		queue.cancelAll(tag);
	}
}
*/
