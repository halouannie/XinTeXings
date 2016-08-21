package com.itheima87.android5060demo;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lxj on 2016/7/7.
 */
public class RecycleViewActivity extends AppCompatActivity {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.toolBar)
    Toolbar toolBar;
    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        ButterKnife.bind(this);

        //1.设置布局模式
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));//竖直方向的
//        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));//横向的列表

//        recyclerView.setLayoutManager(new GridLayoutManager(this,2));//垂直方向的网格布局
//        recyclerView.setLayoutManager(new GridLayoutManager(this,2,RecyclerView.HORIZONTAL,false));//横向方向的网格布局

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,RecyclerView.VERTICAL));

        //2.填充数据
        recyclerView.setAdapter(new MyAdapter());
//        useSwipeRefresLayout();

        //让toolBar代替ActionBar
        setSupportActionBar(toolBar);

        //添加汉堡包按钮
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolBar,0,0);
        drawerToggle.syncState();
        //添加drawer滑动的监听器
        drawerLayout.addDrawerListener(drawerToggle);
    }

    /**
     * SwipeRefreshLayout的使用
     */
    private void useSwipeRefresLayout() {
        //设置一开始就刷新
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //结束刷新
                refreshLayout.setRefreshing(true);
            }
        },1000);
        //设置刷新的颜色进度条
        refreshLayout.setColorSchemeColors(Color.BLUE,Color.RED,Color.GREEN);
        //设置圆形进度条的背景色
        refreshLayout.setProgressBackgroundColorSchemeColor(Color.GRAY);
        //设置下拉刷新的监听器
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                 new Handler().postDelayed(new Runnable() {
                     @Override
                     public void run() {
                         //结束刷新
                         refreshLayout.setRefreshing(false);
                     }
                 },3000);
            }
        });
    }

    class MyAdapter extends RecyclerView.Adapter<MyHolder> {
        /**
         * 返回holder对象
         * @param parent
         * @param viewType
         * @return
         */
        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = View.inflate(RecycleViewActivity.this, R.layout.adapter_recycle, null);
            return new MyHolder(itemView);
        }
        /**
         * 将数据设置给holder的控件
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(final MyHolder holder, int position) {
            //1.准备数据
            String data = position+"碗牛肉面";

            //2.绑定数据
            holder.iv_image.setImageResource(Constant.icons[position]);
            holder.tvText.setText(data);


            //从图片中拾取颜色，设置给cardView的bg:
            BitmapDrawable drawable = (BitmapDrawable) holder.iv_image.getDrawable();
            Palette.from(drawable.getBitmap()).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    //先获取亮色
                    Palette.Swatch swatch = palette.getLightVibrantSwatch();
                    if(swatch!=null){
                        holder.cardView.setCardBackgroundColor(swatch.getRgb());
                        holder.tvText.setTextColor(swatch.getTitleTextColor());
                    }else {
                        //如果亮色为空，则获取暗色
                        swatch = palette.getDarkMutedSwatch();
                        holder.cardView.setCardBackgroundColor(swatch.getRgb());
                        holder.tvText.setTextColor(swatch.getTitleTextColor());
                    }
                }
            });

            //3.设置点击事件
            holder.tvText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(RecycleViewActivity.this,"haha",0).show();
                }
            });
        }
        @Override
        public int getItemCount() {
            return Constant.icons.length;
        }
    }
    class MyHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_text)
        TextView tvText;
        @Bind(R.id.iv_image)
        ImageView iv_image;
        @Bind(R.id.cardView)
        CardView cardView;

        public MyHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
