package com.itheima.demorv87.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.itheima.demorv87.R;
import com.itheima.demorv87.bean.MultiAppInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/7/21.
 */
public class MultiAppInfoAdpater extends BaseMultiItemQuickAdapter<MultiAppInfo> {
    public MultiAppInfoAdpater(List<MultiAppInfo> data) {
        super(data);
        //类型注册
        super.addItemType(MultiAppInfo.TYPE_ISHEADER, R.layout.item_group);//参1类型 参2 视图
        super.addItemType(MultiAppInfo.TYPE_ISAPPINFO, R.layout.item_app);//参1类型 参2 视图

    }
    @Override
    protected void convert(BaseViewHolder helper, MultiAppInfo item) {

        switch (item.getItemType())
        {
            case MultiAppInfo.TYPE_ISHEADER:
                helper.setText(R.id.item_group_tv_title,item.type);
                break;
            case MultiAppInfo.TYPE_ISAPPINFO:
                helper.setText(R.id.item_tv_appname,item.appName)//
                        .setText(R.id.item_tv_desc,item.url)
                        .linkify(R.id.item_tv_desc);
                break;
        }

    }
}
