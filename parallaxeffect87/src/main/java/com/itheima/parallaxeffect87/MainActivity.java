package com.itheima.parallaxeffect87;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
//    ParallaxListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        listview = (ParallaxListView) findViewById(R.id.listview);
//
//        //去掉lisltview到头的时候蓝色阴影
//        listview.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
//
//        View headerView = View.inflate(this, R.layout.head, null);
//        ImageView parallaxImage = (ImageView) headerView.findViewById(R.id.parallaxImage);
//        listview.addHeaderView(headerView);//需要在setAdapter之前调用
//
//        //
//        listview.setParallaxImageView(parallaxImage);
//
//        listview.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Constant.sCheeseStrings));
    }
}
