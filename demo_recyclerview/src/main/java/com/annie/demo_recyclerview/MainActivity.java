package com.annie.demo_recyclerview;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.annie.demo_recyclerview.adapter.AppQuickAdapter;
import com.annie.demo_recyclerview.bean.AppInfo;
import com.annie.demo_recyclerview.net.WebServer;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<AppInfo> mAppList;
    private RecyclerView mRecyclerView;

    private SwipeRefreshLayout mSwipeLayout;
    private AppQuickAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*LooperThread looperThread = new LooperThread();
        looperThread.start();

        looperThread.handler.sen*/

        // 》》1。List
        mAppList = WebServer.getAppList();
        //》》2。Adapter+ViewHolder
        mRecyclerView = (RecyclerView) findViewById(R.id.main_rl);
       // mSwipeLayout= (SwipeRefreshLayout) findViewById(R.id.main_rl);
        // 》》3。创建布局管理者LayoutManager:创建排列方式
        //GridLayoutManager glm=new GridLayoutManager(this,1);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 》》4。设置
        mAdapter = new AppQuickAdapter(mAppList);

        //设置条目监听事件
        /*mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // 获取指定位置的条目信息
                AppInfo appInfo = mAppList.get(position);
                Toast.makeText(MainActivity.this,"点中条目了: " ,Toast.LENGTH_SHORT).show();;

            }
        });*/
        //设置条目中子view的点击事件
        mAdapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //获取点击的条目信息,通过它可以得到具体的子view的信息,
                AppInfo appinfo  = (AppInfo) adapter.getItem(position);
                //获取点中条目的具体信息
                String appname = appinfo.appName;

                switch (view.getId()) {
                      case R.id.item_bnt_download:
                          Toast.makeText(MainActivity.this,"点中子view,开始下载",Toast.LENGTH_SHORT).show();
                          break;
                  }
            }
        });
        mRecyclerView.setAdapter(mAdapter);

    }

    /*class LooperThread extends Thread {
         Handler handler;

        @Override
        public void run() {
            // TODO Auto-generated method stub
            super.run();
            Looper.prepare();
            handler = new Handler() {

                @Override
                public void handleMessage(Message msg) {
                    // TODO Auto-generated method stub
                    super.handleMessage(msg);
                    System.out.println("The msg" + msg.arg1);
                    Log.i("Mytag", "LooperThread 消息是：" + msg.arg1);
                }

            };
            Looper.loop();
        }}
*/
}
