package com.annie.demo_recyclerview.net;

import android.os.SystemClock;

import com.annie.demo_recyclerview.bean.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/21.
 */
public class WebServer {
    public static List<AppInfo> getAppList() {
        //get请求服务
        List<AppInfo> list=new ArrayList<>();
        for(int i=0;i<20;i++)
        {
            AppInfo info=new AppInfo();
            info.appName="应用"+ SystemClock.uptimeMillis()+i;
            info.url="http://www.itheima.com/image/1.jpg"+i;
            list.add(info);
        }
        return list;
    }
    public static List<AppInfo> getNewAppList() {
        //get请求服务
        List<AppInfo> list=new ArrayList<>();
        for(int i=0;i<20;i++)
        {
            AppInfo info=new AppInfo();
            info.appName="刷新获取的数据"+ SystemClock.uptimeMillis()+i;
            info.url="http://www.itheima.com/image/1.jpg"+i;
            list.add(info);
        }
        return list;
    }
    public static List<AppInfo> getLoadmoreData() {
        //get请求服务
        List<AppInfo> list=new ArrayList<>();
        for(int i=0;i<20;i++)
        {
            AppInfo info=new AppInfo();
            info.appName="滚动加载出来的"+ SystemClock.uptimeMillis()+i;
            info.url="http://www.itheima.com/image/1.jpg"+i;
            list.add(info);
        }
        return list;
    }

}
