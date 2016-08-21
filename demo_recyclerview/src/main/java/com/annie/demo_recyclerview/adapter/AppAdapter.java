package com.annie.demo_recyclerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.demo_recyclerview.R;
import com.annie.demo_recyclerview.bean.AppInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/7/21.
 */
public class AppAdapter extends RecyclerView.Adapter<AppAdapter.AppHolder>
{
    private List<AppInfo> mApps;

    public AppAdapter(List<AppInfo> apps) {
        this.mApps = apps;
    }

    //Item个数
    @Override
    public int getItemCount() {
        return mApps.size();
    }
    //创建holder初化里面的控件
    @Override
    public AppHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=View.inflate(parent.getContext(), R.layout.item_app, null);
        AppHolder holder=new AppHolder(itemView);
        holder.iconIv= (ImageView) itemView.findViewById(R.id.item_iv_icon);
        holder.appnameTv= (TextView) itemView.findViewById(R.id.item_tv_appname);
        holder.descTv= (TextView) itemView.findViewById(R.id.item_tv_desc);
        return holder;
    }
    //赋值
    @Override
    public void onBindViewHolder(AppHolder holder, int position) {
        AppInfo appInfo = mApps.get(position);
        holder.appnameTv.setText(appInfo.appName);
    }
    public static class AppHolder extends RecyclerView.ViewHolder
    {
        public ImageView iconIv;
        public TextView appnameTv;
        public TextView descTv;

        public AppHolder(View itemView) {
            super(itemView);
        }
    }

}
