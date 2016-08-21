package com.itheima87.android5060demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lxj on 2016/7/7.
 */
public class TabLayoutActivity extends AppCompatActivity {
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.toolBar)
    Toolbar toolBar;

    ArrayList<Fragment> list = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout);
        ButterKnife.bind(this);

        //使用ToolBar
        setSupportActionBar(toolBar);
        toolBar.setTitleTextColor(Color.WHITE);

        //单独使用TabLayout
        //1.添加tab选项卡
       /* tabLayout.addTab(tabLayout.newTab().setText("tab1"));
        tabLayout.addTab(tabLayout.newTab().setText("tab2"));
        tabLayout.addTab(tabLayout.newTab().setText("tab3"));
        tabLayout.addTab(tabLayout.newTab().setText("tab4"));
        tabLayout.addTab(tabLayout.newTab().setText("tab5"));
        tabLayout.addTab(tabLayout.newTab().setText("tab6"));
        tabLayout.addTab(tabLayout.newTab().setText("tab7"));
        tabLayout.addTab(tabLayout.newTab().setText("tab8"));
        //2.给tab设置选中的监听器
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(TabLayoutActivity.this, tab.getText(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });*/

        //2.让TabLayout结合ViewPager一起使用
        list.add(new EmptyFragment());
        list.add(new EmptyFragment());
        list.add(new EmptyFragment());

        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        //绑定TabLayout和ViewPager
        tabLayout.setupWithViewPager(viewPager);



    }

    class MyAdapter extends FragmentPagerAdapter{
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = null;
            switch (position){
                case 0:
                    title = "标题1";
                    break;
                case 1:
                    title = "标题2";
                    break;
                case 2:
                    title = "标题3";
                    break;
            }
            return title;
        }
    }
}
