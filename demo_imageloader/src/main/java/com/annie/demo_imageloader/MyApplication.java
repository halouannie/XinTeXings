package com.annie.demo_imageloader;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by ma on 2016/7/14.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //初始化Fresco
        Fresco.initialize(this);
    }
}
