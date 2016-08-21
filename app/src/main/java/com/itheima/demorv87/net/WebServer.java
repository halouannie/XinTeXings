package com.itheima.demorv87.net;

import android.os.SystemClock;

import com.itheima.demorv87.bean.AppInfo;
import com.itheima.demorv87.bean.Category;
import com.itheima.demorv87.bean.SectionInfo;
import com.itheima.demorv87.bean.MultiAppInfo;
import com.itheima.demorv87.bean.MultiNewInfo;

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
    public static List<MultiAppInfo> getMultiAppList() {
        List<MultiAppInfo> list=new ArrayList<>();

        MultiAppInfo item1=new MultiAppInfo();
//        item1.isHeader=true;
        item1.type="人气下载";

        item1.setItemType(MultiAppInfo.TYPE_ISHEADER);
        list.add(item1);
        for(int i=0;i<4;i++)
        {
            MultiAppInfo info=new MultiAppInfo();
            info.setItemType(MultiAppInfo.TYPE_ISAPPINFO);;
            info.appName="人气下载"+i;
            info.url="http://www.itheima.com/image/1.jpg"+i;
            list.add(info);
        }

        MultiAppInfo item2=new MultiAppInfo();
        item2.setItemType(MultiAppInfo.TYPE_ISHEADER);
        item2.type="视频音乐";
        list.add(item2);
        for(int i=0;i<4;i++)
        {
            MultiAppInfo info=new MultiAppInfo();
            info.setItemType(MultiAppInfo.TYPE_ISAPPINFO);;
            info.appName="视频音乐"+i;
            info.url="http://www.itheima.com/image/1.jpg"+i;
            list.add(info);
        }

        MultiAppInfo item3=new MultiAppInfo();
        item3.setItemType(MultiAppInfo.TYPE_ISHEADER);
        item3.type="新闻阅读";
        list.add(item3);
        for(int i=0;i<3;i++)
        {
            MultiAppInfo info=new MultiAppInfo();
            info.setItemType(MultiAppInfo.TYPE_ISAPPINFO);;
            info.appName="新闻阅读"+i;
            info.url="http://www.itheima.com/image/1.jpg"+i;
            list.add(info);
        }
        return list;
    }

    public static List<MultiNewInfo> getMultiNewsList() {

        String imageurl="http://www.itheima.com/image";
        List<MultiNewInfo> list=new ArrayList<>();
        for(int  i=0;i<3;i++)
        {
            MultiNewInfo item1=new MultiNewInfo();
            item1.newTitle="新闻 x3--"+i;
            item1.images.add(imageurl);
            item1.images.add(imageurl);
            item1.images.add(imageurl);
            item1.setItemType(MultiNewInfo.TYPE_IMG_THREE_SAMLL);
            list.add(item1);
        }
        for(int  i=0;i<2;i++)
        {
            MultiNewInfo item1=new MultiNewInfo();
            item1.newTitle="新闻 x1"+i;
            item1.images.add(imageurl);
            item1.setItemType(MultiNewInfo.TYPE_IMG_ONE_SMALL);
            list.add(item1);
        }
        for(int  i=0;i<2;i++)
        {
            MultiNewInfo item1=new MultiNewInfo();
            item1.newTitle="新闻 x3--"+i;
            item1.images.add(imageurl);
            item1.images.add(imageurl);
            item1.images.add(imageurl);
            item1.setItemType(MultiNewInfo.TYPE_IMG_THREE_SAMLL);
            list.add(item1);
        }
        MultiNewInfo item1=new MultiNewInfo();
        item1.newTitle="新闻 x0--";
        item1.setItemType(MultiNewInfo.TYPE_IMG_NO);
        list.add(item1);

        MultiNewInfo item2=new MultiNewInfo();
        item2.newTitle="新闻 xbig one--";
        item2.images.add(imageurl);
        item2.setItemType(MultiNewInfo.TYPE_IMG_ONE_BIG);
        list.add(item2);
        return list;
    }

    public static List<SectionInfo> getAppCategory() {
        List<SectionInfo> list=new ArrayList<>();

        //一级分类 isheader==true
        list.add(new SectionInfo(true,"游戏"));
        //一级分类下面的分类是二级分类 休闲 棋牌 益智...
        String url="http://www.itheima.com/1.jpg";
        for(int i=0;i<11;i++)
        {

            list.add(new SectionInfo(new Category("游戏二级分类"+i, url)));
        }

        list.add(new SectionInfo(true,"金融工具"));
        //一级分类下面的分类是二级分类 休闲 棋牌 益智...

        for(int i=0;i<7;i++)
        {

            list.add(new SectionInfo(new Category("金融二级"+i, url)));
        }

        list.add(new SectionInfo(true,"阅读"));
        //一级分类下面的分类是二级分类 休闲 棋牌 益智...

        for(int i=0;i<12;i++)
        {

            list.add(new SectionInfo(new Category("阅读二级"+i, url)));
        }

        return list;
    }

    public static List<String> getNewsChannels() {
        List<String> list=new ArrayList<>();
        for(int i=0;i<20;i++)
        {
            list.add("频道"+i);
        }
        return list;
    }
}
