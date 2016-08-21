package com.annie.demo_quickindex;

import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    ArrayList<Friend> friends = new ArrayList<>();
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.showWord);
        final ListView listView = (ListView) findViewById(R.id.listview);

        //通过动画的形式来显示/隐藏,开始先隐藏掉
        textView.setScaleX(0);
        textView.setScaleY(0);

        //初始化快速索引,并添加监听
        QuickIndexBar quickIndexBar = (QuickIndexBar) findViewById(R.id.quickindex);
        quickIndexBar.setOnTouchLetterChanged(new QuickIndexBar.OnTouchLetterChanged() {
            @Override
            public void onLetterChanged(String letter) {
                //当触摸字母改变的时候，需要找到集合中首字母和letter一样的条目，
                // 然后将其置顶
                for (int i = 0; i < friends.size(); i++) {
                    String firsrWord = friends.get(i).getPinyin().charAt(0) +"";
                    if (letter.equalsIgnoreCase(firsrWord)) {
                        //说明当前的i就是要找的条目的索引
                        //将其放置到listview的顶部
                        listView.setSelection(i);
                        //找到就立即停止
                       break;
                    }
                }
                //System.out.println(letter);

                //显示圆形的当前的字母
                showWord(letter);
            }
        });

        //准备数据
        prepareData();
        listView.setAdapter(new Myadapter(friends,this));
       // System.out.println(PinyinUtil.getPinyin("王焱"));

        //对集合进行排序
        Collections.sort(friends);
    }

    /**
     *  显示圆形的当前的字母
     */
    Handler handler = new Handler();
    public boolean isAnim = false;
    private void showWord(String letter) {
       // textView.setVisibility(View.VISIBLE);
        //设置动画,只有当 当前没有执行的动画的时候,才执行动画,有就等上一个动画执行结束
        if (!isAnim) {
            ViewCompat.animate(textView)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(400)  //设置动画监听,当有动画正在执行时,新的动画不能执行
                    .setListener(new ViewPropertyAnimatorListener() {
                        @Override
                        public void onAnimationStart(View view) {
                            isAnim = true;
                        }
                        @Override
                        public void onAnimationEnd(View view) {
                            isAnim = false;
                        }
                        @Override
                        public void onAnimationCancel(View view) {

                        }
                    })
                    .start();
        }
        textView.setText(letter);

        //先清除之前的延时任务
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /*//延时隐藏掉textview
                textView.setVisibility(View.GONE);*/
                //通过动画来隐藏
                ViewCompat.animate(textView)
                        .scaleX(0f)
                        .scaleY(0f)
                        .setDuration(400)
                        .start();
            }
        },1200);

    }

    private void prepareData() {
        // 虚拟数据
        friends.add(new Friend("李伟"));
        friends.add(new Friend("张三"));
        friends.add(new Friend("阿三"));
        friends.add(new Friend("阿四"));
        friends.add(new Friend("段誉"));
        friends.add(new Friend("段正淳"));
        friends.add(new Friend("张三丰"));
        friends.add(new Friend("陈坤"));
        friends.add(new Friend("林俊杰1"));
        friends.add(new Friend("陈坤2"));
        friends.add(new Friend("王二a"));
        friends.add(new Friend("林俊杰a"));
        friends.add(new Friend("张四"));
        friends.add(new Friend("林俊杰"));
        friends.add(new Friend("王二"));
        friends.add(new Friend("王二b"));
        friends.add(new Friend("赵四"));
        friends.add(new Friend("杨坤"));
        friends.add(new Friend("赵子龙"));
        friends.add(new Friend("杨坤1"));
        friends.add(new Friend("李伟1"));
        friends.add(new Friend("宋江"));
        friends.add(new Friend("宋江1"));
        friends.add(new Friend("李伟3"));
    }
}
