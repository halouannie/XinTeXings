package com.itheima.demorv87.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/21.
 */
public class MultiNewInfo extends MultiItemEntity {
    public static  final int TYPE_IMG_ONE_BIG=1;
    public static  final int TYPE_IMG_ONE_SMALL=2;
    public static  final int TYPE_IMG_THREE_SAMLL=3;
    public static  final int TYPE_IMG_NO=4;

    public String newTitle;
    public List<String> images=new ArrayList<>();
}
