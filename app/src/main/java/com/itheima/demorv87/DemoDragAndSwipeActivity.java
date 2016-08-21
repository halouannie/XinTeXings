package com.itheima.demorv87;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.itheima.demorv87.adapter.ChannelAdapter;
import com.itheima.demorv87.net.WebServer;

import java.util.List;

public class DemoDragAndSwipeActivity extends AppCompatActivity {

    private List<String> mChannels;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 》》1。List
        mChannels = WebServer.getNewsChannels();
        //》》2。Adapter+ViewHolder
        mRecyclerView = (RecyclerView) findViewById(R.id.main_rl);
         // 》》3。创建布局管理者LayoutManager:创建排列方式
        GridLayoutManager glm=new GridLayoutManager(this,4);
        mRecyclerView.setLayoutManager(glm);
        // 》》4。设置
        ChannelAdapter adapter=new ChannelAdapter(mChannels);
        //滑动删除
        adapter.enableSwipeItem();//滑动删除属性
        //框架已经将touch拖动事件的处理写好了封装ItemTouchHelper.
        //callback并不是代表回调而只是一个配置
        ItemDragAndSwipeCallback config=new ItemDragAndSwipeCallback(adapter);
        //配置滑动方向
        config.setSwipeMoveFlags(ItemTouchHelper.START|ItemTouchHelper.END);
        //默认的touch事件处理实现
        ItemTouchHelper helper=new ItemTouchHelper(config);
        helper.attachToRecyclerView(mRecyclerView);
        adapter.enableDragItem(helper);//拖动重摆
        mRecyclerView.setAdapter(adapter);



    }

    private View infalteView(int layoutId) {
       LayoutInflater inflater=LayoutInflater.from(this);
        //防止布局的match_parent被强改成wrap_content
       return  inflater.inflate(layoutId, (ViewGroup) mRecyclerView.getParent(),false);//参3prarent跟view没有添加关系.
    }


}
