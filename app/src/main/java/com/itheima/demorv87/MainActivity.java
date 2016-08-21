package com.itheima.demorv87;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.itheima.demorv87.adapter.AppQuickAdapter;
import com.itheima.demorv87.adapter.CategoryAdapter;
import com.itheima.demorv87.adapter.ChannelAdapter;
import com.itheima.demorv87.bean.AppInfo;
import com.itheima.demorv87.bean.SectionInfo;
import com.itheima.demorv87.net.WebServer;

import java.nio.channels.Channel;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<AppInfo> mAppList;
    private RecyclerView mRecyclerView;

    private SwipeRefreshLayout mSwipeLayout;
    private AppQuickAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull2refresh_loadmore);

        // 》》1。List
        mAppList = WebServer.getAppList();
        //》》2。Adapter+ViewHolder
        mRecyclerView = (RecyclerView) findViewById(R.id.main_rl);
        mSwipeLayout= (SwipeRefreshLayout) findViewById(R.id.main_srl);
         // 》》3。创建布局管理者LayoutManager:创建排列方式
        GridLayoutManager glm=new GridLayoutManager(this,1);
        mRecyclerView.setLayoutManager(glm);
        // 》》4。设置
        mAdapter = new AppQuickAdapter(mAppList);

        //属性打开
        mAdapter.openLoadMore(20,true);
        //滚动加载有footerView
        View footerView=infalteView(R.layout.footer_view);
        mAdapter.addFooterView(footerView);
        //监听什么时候最后一条
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener(){

            @Override
            public void onLoadMoreRequested() {
                //加载更多数据
                getDataForNextPage();
            }
        });



        mRecyclerView.setAdapter(mAdapter);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //等待1000
                getDataForFirstPage();
            }
        });



    }

    private View infalteView(int layoutId) {
       LayoutInflater inflater=LayoutInflater.from(this);
        //防止布局的match_parent被强改成wrap_content
       return  inflater.inflate(layoutId, (ViewGroup) mRecyclerView.getParent(),false);//参3prarent跟view没有添加关系.
    }


    public void getDataForFirstPage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                List<AppInfo> newList = WebServer.getNewAppList();

                //clear
                mAdapter.setNewData(newList);
                mSwipeLayout.setRefreshing(false);//关闭刷新中的loadingView
                mAdapter.openLoadMore(20,true);
                //allAll
                //notifyDataSetChanged
               // mAdapter.addData(newList);
            }
        }, 3000);
    }

    public void getDataForNextPage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<AppInfo> nextPage = WebServer.getLoadmoreData();
               // mAdapter.addData(nextPage);

                //在滚动加载以后重新刷新。
                mAdapter.notifyDataChangedAfterLoadMore(nextPage,true);
            }
        }, 3000);
    }
}
