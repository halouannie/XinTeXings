package com.itheima.http.demoretrofit.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/10.
 */
public class CarNewsResult {

    public Data data;
    public String retcode;

    public static  class Data
    {
        public String countcommenturl;
        public String more;
        public List<CarNews> news;
        public String title;
        public List<TopicNews>topic;
        public List<Topnews>topnews;

    }
    public static class CarNews
    {
        public String comment;
        public String commentlist;
        public String commenturl;
        public String id;
        public String listimage;
        public String pubdate;
        public String title;
        public String type;
        public String url;

    }

    public static  class TopicNews
    {

        public String description;
        public String id;
        public String listimage;
        public String sort;
        public String title;
        public String url;

    }

    public static  class Topnews
    {

        public String comment;
        public String commentlist;
        public String commenturl;
        public String id;
        public String pubdate;
        public String title;
        public String topimage;
        public String type;
        public String url;

    }

}
