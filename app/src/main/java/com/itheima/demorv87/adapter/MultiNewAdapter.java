package com.itheima.demorv87.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.itheima.demorv87.R;
import com.itheima.demorv87.bean.MultiNewInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/7/21.
 */
public class MultiNewAdapter extends BaseMultiItemQuickAdapter<MultiNewInfo> {

    public MultiNewAdapter(List<MultiNewInfo> data) {
        super(data);
        super.addItemType(MultiNewInfo.TYPE_IMG_THREE_SAMLL, R.layout.item_new_image_three);
        super.addItemType(MultiNewInfo.TYPE_IMG_ONE_SMALL, R.layout.item_new_image_one);
        super.addItemType(MultiNewInfo.TYPE_IMG_NO, R.layout.item_new_image_no);
        super.addItemType(MultiNewInfo.TYPE_IMG_ONE_BIG, R.layout.item_new_image_one_big);
    }

    //赋值
    @Override
    protected void convert(BaseViewHolder helper, MultiNewInfo item) {

        switch (item.getItemType()) {
            case MultiNewInfo.TYPE_IMG_THREE_SAMLL:
                helper.setText(R.id.item_tv_appname,item.newTitle+"三张小图");
                break;
            case MultiNewInfo.TYPE_IMG_ONE_SMALL:
                helper.setText(R.id.item_tv_appname,item.newTitle+"1张小图");
                break;
            case MultiNewInfo.TYPE_IMG_NO:
                helper.setText(R.id.item_tv_appname,item.newTitle+"  0张小图");

                //helper.display(icon1,url);
                break;
            case MultiNewInfo.TYPE_IMG_ONE_BIG:
                helper.setText(R.id.item_tv_appname,item.newTitle+"  1张大图");
                break;


        }
    }
}
