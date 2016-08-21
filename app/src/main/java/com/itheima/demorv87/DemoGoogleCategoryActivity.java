package com.itheima.demorv87;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itheima.demorv87.adapter.CategoryAdapter;
import com.itheima.demorv87.bean.SectionInfo;
import com.itheima.demorv87.net.WebServer;

import java.util.List;

public class DemoGoogleCategoryActivity extends AppCompatActivity {

    private List<SectionInfo> mCategoryInfos;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 》》1。List
        mCategoryInfos = WebServer.getAppCategory();
        //》》2。Adapter+ViewHolder
        mRecyclerView = (RecyclerView) findViewById(R.id.main_rl);
         // 》》3。创建布局管理者LayoutManager:创建排列方式
        StaggeredGridLayoutManager sglm=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(sglm);
        // 》》4。设置
        CategoryAdapter adpater=new CategoryAdapter(mCategoryInfos);
        mRecyclerView.setAdapter(adpater);


    }

    private View infalteView(int layoutId) {
       LayoutInflater inflater=LayoutInflater.from(this);
        //防止布局的match_parent被强改成wrap_content
       return  inflater.inflate(layoutId, (ViewGroup) mRecyclerView.getParent(),false);//参3prarent跟view没有添加关系.
    }


}
