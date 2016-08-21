package com.annie.android5_0.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.annie.android5_0.R;

/**
 * TabLyout的单独使用
 * Created by ma on 2016/7/19.
 */
public class TabLayoutFragment extends android.support.v4.app.Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.layout_tablayout,null);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        // 1.添加Tab选项卡
        tabLayout.addTab(tabLayout.newTab().setText("Annie1"));
        tabLayout.addTab(tabLayout.newTab().setText("Annie2"));
        tabLayout.addTab(tabLayout.newTab().setText("Annie3"));
        tabLayout.addTab(tabLayout.newTab().setText("Annie4"));
        tabLayout.addTab(tabLayout.newTab().setText("Annie5"));


        // 2.给tablayout设置选中监听器
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            //被选中时调用
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(getActivity(),"被选中了",Toast.LENGTH_SHORT).show();
            }
            //被取消选中时调用
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Toast.makeText(getActivity(),"被取消了",Toast.LENGTH_SHORT).show();
            }
            //被重新选中时调用
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //当前处在被选中状态的时候又被选中,就会调用该方法
                Toast.makeText(getActivity(),"再次被选中了",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
