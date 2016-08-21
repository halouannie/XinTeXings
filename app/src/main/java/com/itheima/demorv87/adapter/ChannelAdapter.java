package com.itheima.demorv87.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.itheima.demorv87.R;

import java.util.List;

/**
 * Created by Administrator on 2016/7/21.
 */
public class ChannelAdapter extends BaseQuickAdapter<String> {
    public ChannelAdapter(List<String> data) {
        super(R.layout.item_grid_channel,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.item_grid_channel_tv,item);
    }
}
