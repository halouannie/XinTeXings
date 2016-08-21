package com.annie.android5_0.fragment;


import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.android5_0.R;

import java.util.ArrayList;

/**
 * TabLayout结合ViewPager的使用
 * Created by ma on 2016/7/19.
 */
public class TabLayoutFragment_2 extends Fragment {
    ArrayList<Fragment> list = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.layout_tablayout,null);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        // a.准备数据,即要展示的界面
        list.add(new EmptyFragment_1());
        list.add(new EmptyFragment_2());
        list.add(new EmptyFragment_3());
        list.add(new EmptyFragment_4());
        list.add(new EmptyFragment_5());

        // b.为viewpager设置适配器
        viewPager.setAdapter(new MyAdapter(getActivity().getSupportFragmentManager()));
        // c.绑定TabLayout和ViewPager
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }


    //viewpager 适配器
    class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
           String[] str = new String[] {"标题1","标题2","标题3","标题4","标题5"};
            return str[position];
        }
    }
}
