package com.annie.demo_recyclerview.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.annie.demo_recyclerview.R;
import com.annie.demo_recyclerview.bean.AppInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/7/21.
 */
public class AppQuickAdapter extends BaseQuickAdapter<AppInfo> {
    public AppQuickAdapter(List<AppInfo> data) {
        super(R.layout.item_app,data);
    }


    //BaseViewHolder:对ViewHolder的封装 不同的页面这个条目 类型与数量==>使用集合封装
    //SparseArray:可以看成HashMap<Integer,Object>
    @Override
    protected void convert(BaseViewHolder helper, AppInfo item) {
         //项目编写展示逻辑 setText(参1 id 参2数值)
        helper.setText(R.id.item_tv_appname,item.appName)//
        .setText(R.id.item_tv_desc,item.url)
        //.linkify(R.id.item_tv_desc)//以超连接的方式来展示
        /*.setOnClickListener(R.id.item_bnt_download, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "点击下载", Toast.LENGTH_SHORT).show();
            }
        })*/
        .setImageResource(R.id.item_iv_icon,R.mipmap.circle);
      // ImageView icon= helper.getView(R.id.item_iv_icon);//item.url
       // new BitmapUtil().display(icon,url)

        // helper.convertView: 获取listview的条目;   设置条目监听事件
//        helper.convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext, "convertView OnClickListener ", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
