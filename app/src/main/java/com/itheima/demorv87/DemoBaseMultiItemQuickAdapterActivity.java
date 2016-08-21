package com.itheima.demorv87;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itheima.demorv87.adapter.MultiAppInfoAdpater;
import com.itheima.demorv87.bean.MultiAppInfo;
import com.itheima.demorv87.net.WebServer;

import java.util.List;

public class DemoBaseMultiItemQuickAdapterActivity extends AppCompatActivity {

    private List<MultiAppInfo> mApps;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 》》1。List
        mApps = WebServer.getMultiAppList();
        //》》2。Adapter+ViewHolder
        mRecyclerView = (RecyclerView) findViewById(R.id.main_rl);
         // 》》3。创建布局管理者LayoutManager:创建排列方式
        LinearLayoutManager lm=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(lm);
        // 》》4。设置
        MultiAppInfoAdpater adapter=new MultiAppInfoAdpater(mApps);
        mRecyclerView.setAdapter(adapter);
    }

    private View infalteView(int layoutId) {
       LayoutInflater inflater=LayoutInflater.from(this);
        //防止布局的match_parent被强改成wrap_content
       return  inflater.inflate(layoutId, (ViewGroup) mRecyclerView.getParent(),false);//参3prarent跟view没有添加关系.
    }


}
