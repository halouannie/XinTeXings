package com.eannie.demo_parallaxlistview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private LinearLayout container;

    /**
     *  在onCreate方法中任何时候调用getHeight方法都获取不到宽高
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParallaxListView listView = (ParallaxListView) findViewById(R.id.ParallaxListView);
        View headView = View.inflate(this,R.layout.head,null);
        imageView = (ImageView) headView.findViewById(R.id.head);
        //container = (LinearLayout) headView.findViewById(R.id.container);
        listView.addHeaderView(headView);//需要在setAdapter之前调用

        //将imageview对象传给ParallaxListView
        listView.setImageview(imageView);
        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Constant.sCheeseStrings));

    }
}
