package com.annie.android5_0.activity;

import android.app.ActionBar;
import android.app.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.annie.android5_0.R;
import com.annie.android5_0.adapter.leftMenuAdapter;
import com.annie.android5_0.fragment.PictureAndColorFragment;
import com.annie.android5_0.fragment.RecyclerViewFragment;
import com.annie.android5_0.fragment.ShadowFragment;
import com.annie.android5_0.fragment.TabLayoutFragment;
import com.annie.android5_0.fragment.TabLayoutFragment_2;
import com.annie.android5_0.fragment.ThemeFragment;
import com.annie.android5_0.fragment.newAnimatorFragment;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements  AdapterView.OnItemClickListener {

    private DrawerLayout drawlayout;
    private ListView listView;
    private String[] str;
    private FrameLayout frameLayout;
    private ArrayList<Fragment> list;

    //主题的静态变量,用于判断是否需要切换主题
    public static int THEME = R.style.AppTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(THEME);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化控件
        initView();

        //设置actionbar
        setActionbar();

        //默认显示第一个界面
        switchPager(0);

        //获取数组数据
        str = getResources().getStringArray(R.array.item_array);
        //为listview设置adapter展示数据
        listView.setAdapter(new leftMenuAdapter(str,this));
        //为listview条目设置点击事件
        listView.setOnItemClickListener(this);

    }

    /**
     *  初始化控件
     */
    private void initView() {
        drawlayout = (DrawerLayout) findViewById(R.id.drawlayout);
        listView = (ListView) findViewById(R.id.listview);
        frameLayout = (FrameLayout) findViewById(R.id.fragment);
    }

    /**
     *  设置actionbar
     */
    private void setActionbar() {
        // 1.获取actionbar,进行相关设置
        ActionBar actionbar = getActionBar();
       // actionbar.setIcon(R.mipmap.ic_launcher);
        actionbar.setTitle(getResources().getString(R.string.app_name));

        // 2.设置home按钮可被点击
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);

        // 3.设置点击事件,onOptionsItemSelected方法中实现
        // 4.关联actionbar和drawlayout,5.0不用再设置汉堡包图片,这两行代码之后,汉堡包图片自动就出来了
        final ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drawlayout,0,0);
        drawerToggle.syncState();

        // 5.添加DrawerLayout的滑动监听，让ActionBarDrawerToggle进行滑动动画
        //翻看源码得知ActionBarDrawerToggle已经实现了DrawerListener监听
        drawlayout.setDrawerListener(drawerToggle);
    }

    /**
     *  界面切换
     */
    public void switchPager(int position) {

        //加载要展示的界面
        list = new ArrayList<>();
        list.add(new ThemeFragment());
        list.add(new ShadowFragment());
        list.add(new PictureAndColorFragment());
        list.add(new newAnimatorFragment());
        list.add(new RecyclerViewFragment());
        list.add(new TabLayoutFragment());
        list.add(new TabLayoutFragment_2());


        //设置Fragment展示界面
        FragmentManager fm = getSupportFragmentManager();
        //FragmentManager fm = getFragmentManager(); //获取fragment管理器
        FragmentTransaction ft = fm.beginTransaction(); //开启服务
        ft.replace(R.id.fragment, list.get(position));//指定要切换的界面
        ft.commit();//提交服务
    }

    //actionbar的点击事件

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (drawlayout.isDrawerOpen(Gravity.LEFT)) {
                    drawlayout.closeDrawer(Gravity.LEFT);
                }else {
                    drawlayout.openDrawer(Gravity.LEFT);
                }
        }
        return super.onOptionsItemSelected(item);
    }

    //listview的条目点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //跳转到点击的界面
        switchPager(position);
        //关闭左侧侧滑菜单
        drawlayout.closeDrawer(Gravity.LEFT);
    }
}
