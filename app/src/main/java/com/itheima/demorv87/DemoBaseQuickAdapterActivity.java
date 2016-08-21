package com.itheima.demorv87;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.animation.BaseAnimation;
import com.itheima.demorv87.adapter.AppQuickAdapter;
import com.itheima.demorv87.bean.AppInfo;
import com.itheima.demorv87.net.WebServer;

import java.util.List;

public class DemoBaseQuickAdapterActivity extends AppCompatActivity {

    private List<AppInfo> mApps;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 》》1。List
        mApps = WebServer.getAppList();
        //》》2。Adapter+ViewHolder
        mRecyclerView = (RecyclerView) findViewById(R.id.main_rl);
         // 》》3。创建布局管理者LayoutManager:创建排列方式
        LinearLayoutManager lm=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(lm);
        // 》》4。设置
        AppQuickAdapter adapter=new AppQuickAdapter(mApps);
        //支持动画 BaseQuickAdapter动画
        //adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.openLoadAnimation(new BaseAnimation() {
            @Override
            public Animator[] getAnimators(View view) {
                return new Animator[]{
                        ObjectAnimator.ofFloat(view, "scaleY", 1, 1.1f, 1),
                        ObjectAnimator.ofFloat(view, "scaleX", 1, 1.1f, 1),
                        ObjectAnimator.ofFloat(view,"translationX",0,100,0)
                };
            }
        });

        //自己定义
        adapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                AppInfo appInfo = mApps.get(position);
                Toast.makeText(DemoBaseQuickAdapterActivity.this, "进入详情"+appInfo.appName, Toast.LENGTH_SHORT).show();
            }
        });

        //mApps.clear();
        if (mApps.size() == 0) {
            View emptyView = infalteView(R.layout.empty_view);
            //LayoutInflater.
            adapter.setEmptyView(emptyView);
        }

        //添加顶部视图
        View headView=infalteView(R.layout.head_view);
        adapter.addHeaderView(headView);


        //添加加载更多
        View footerview=infalteView(R.layout.footer_view);
        adapter.addFooterView(footerview);
        mRecyclerView.setAdapter(adapter);
    }

    private View infalteView(int layoutId) {
       LayoutInflater inflater=LayoutInflater.from(this);
        //防止布局的match_parent被强改成wrap_content
       return  inflater.inflate(layoutId, (ViewGroup) mRecyclerView.getParent(),false);//参3prarent跟view没有添加关系.
    }


}
