package com.annie.demo_recyclerview.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by Administrator on 2016/7/21.
 */
public class MultiAppInfo  extends MultiItemEntity{

    public static final int TYPE_ISHEADER=1;
    public static final int TYPE_ISAPPINFO=2;
//    public boolean isHeader;
    public String type;

    //----
    public String url;
    public String appName;
    //实际项目还有很多个字段
}
