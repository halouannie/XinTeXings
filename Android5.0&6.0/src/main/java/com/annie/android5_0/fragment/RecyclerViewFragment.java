package com.annie.android5_0.fragment;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.android5_0.Constant;
import com.annie.android5_0.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerViewFragment extends android.support.v4.app.Fragment {

    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.layout_recyclerview, null);
        ButterKnife.bind(this, view);

        // 1.设置布局模式
        //recyclerview.setLayoutManager(new LinearLayoutManager(getActivity())); //竖直方向的listview
        // 水平方向的listview, 参3:设置是否反转
       // recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
      // recyclerview.setLayoutManager(new GridLayoutManager(getActivity(),2));  //设置两列的竖直方向的网格布局
        //recyclerview.setLayoutManager(new GridLayoutManager(getActivity(),2,RecyclerView.HORIZONTAL,false));
        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2,RecyclerView.VERTICAL));//两列三行的竖直方向的瀑布流

        // 2.设置适配器,展示数据
        recyclerview.setAdapter(new MyAdapter());

        return view;
    }

    //适配器
    public class MyAdapter extends RecyclerView.Adapter<MyHolder> {
        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(getActivity(),R.layout.item_recycler,null);

            //给子view设置监听事件
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(),"Hello,Annie",Toast.LENGTH_SHORT).show();
                }
            });
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyHolder holder, int position) {
            // 1.准备数据
            String data = "Annie " + position;
            // 2.绑定数据
            holder.imageView.setImageResource(Constant.icons[position]);
            holder.text.setText(data);

            //从图片中拾取颜色，设置给cardView的bg:
            BitmapDrawable drawable = (BitmapDrawable) holder.imageView.getDrawable();
            Palette.from(drawable.getBitmap()).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    //先获取亮色
                    Palette.Swatch swatch = palette.getLightVibrantSwatch();
                    if (palette.getLightVibrantSwatch() != null){
                        swatch = palette.getLightVibrantSwatch();
                    }else if (palette.getLightMutedSwatch() != null) {
                        swatch = palette.getLightMutedSwatch();
                    }else if (palette.getDarkVibrantSwatch() != null) {
                        swatch = palette.getDarkVibrantSwatch();
                    }else  if (palette.getMutedSwatch() != null) {
                        swatch = palette.getMutedSwatch();
                    }else if(palette.getDarkMutedSwatch() != null){
                        swatch = palette.getDarkMutedSwatch();
                    }else {
                        swatch = palette.getVibrantSwatch();
                    }
                    holder.cardview.setCardBackgroundColor(swatch.getRgb());
                    holder.text.setTextColor(swatch.getBodyTextColor());

                }
            });
        }

        @Override
        public int getItemCount() {
            return 32;
        }
    }

    static class MyHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView imageView;
        CardView cardview;

        public MyHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
            imageView = (ImageView) itemView.findViewById(R.id.iamgeview);
            cardview = (CardView) itemView.findViewById(R.id.cardview);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
