package com.itheima.demorv87.adapter;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.itheima.demorv87.R;
import com.itheima.demorv87.bean.SectionInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/7/21.
 */
public class CategoryAdapter extends BaseSectionQuickAdapter<SectionInfo> {

    public CategoryAdapter( List<SectionInfo> data) {
        super(R.layout.item_grid, R.layout.item_group, data);
        //参1 网格视图  参2标题视图
    }

    //赋值逻辑 isHeader==true
    @Override
    protected void convertHead(BaseViewHolder helper, SectionInfo item) {
        helper.setText(R.id.item_group_tv_title,item.header+"  "+item.isHeader);
    }

    //赋值逻辑 isHeader==false 满三格换一行
    @Override
    protected void convert(BaseViewHolder helper, SectionInfo item) {
        helper.setImageResource(R.id.item_iv_icon,R.mipmap.headview)
                .setText(R.id.item_tv_appname,item.t.content+"  "+item.isHeader);
    }
}
