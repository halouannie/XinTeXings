package com.annie.demo_slidingmenu;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.menu_listview)
    ListView menuListview;
    @Bind(R.id.iv_head)
    ImageView ivHead;
    @Bind(R.id.main_listview)
    ListView mainListview;
    @Bind(R.id.my_linearlayout)
    MyLinearLayout myLinearlayout;
    private SlideMenu slideMenu;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取slidemenu 对象
        slideMenu = (SlideMenu) View.inflate(this, R.layout.activity_main, null);
        listView = (ListView) slideMenu.findViewById(R.id.main_listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,"haha",Toast.LENGTH_SHORT).show();
            }
        });

        setContentView(slideMenu);
        ButterKnife.bind(this);

        //打开侧滑菜单时,拦截主界面的事件,让listview不能滑动
        myLinearlayout.setSlideMenu(slideMenu);

        //填充数据
        mainListview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Constant.NAMES));
        menuListview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1
                , Constant.sCheeseStrings) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(Color.WHITE);
                return textView;
            }
        });

        //为slidemenu设置监听器
        slideMenu.setDragListener(new SlideMenu.DragListener() {
            @Override
            public void open() {
                Toast.makeText(MainActivity.this, "open", Toast.LENGTH_SHORT).show();
                //打开侧滑菜单时随机刷新下listview,随机滚动到指定的位置
                int i = new Random().nextInt(Constant.sCheeseStrings.length);
                menuListview.smoothScrollToPosition(i);
            }

            @Override
            public void close() {
                Toast.makeText(MainActivity.this, "close", Toast.LENGTH_SHORT).show();
                //打开侧滑菜单时让头像抖动
               /* //平移动画
                ViewCompat.animate(ivHead)
                        .setInterpolator(new CycleInterpolator(4)) //来回执行的差值器
                        .translationXBy(21)
                        .setDuration(1000)
                        .start();*/
                //旋转动画
                ViewCompat.animate(ivHead)
                        .rotationBy(360)
                        .setInterpolator(new OvershootInterpolator(2)) //超过一点在回来
                        .setDuration(1000)
                        .start();
            }

            @Override
            public void draging(float percent) {
                System.out.println("percent: " + percent);

                //滑动过程中让头像渐渐消失
                /*ViewCompat.animate(ivHead)
                        .alpha(1f-percent);*/
               /* ViewCompat.animate(ivHead)
                        .rotation(360*percent); //让头像随滑动而缓慢旋转,伴随动画*/
            }
        });
    }
}
