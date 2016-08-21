package com.itheima87.android5060demo;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
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
public class EmptyFragment extends Fragment {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(),R.layout.fragment_empty,null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,RecyclerView.VERTICAL));
        //2.填充数据
        recyclerView.setAdapter(new MyAdapter());
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
            View itemView = View.inflate(getActivity(), R.layout.adapter_recycle, null);
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
