package com.annie.android5_0.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.annie.android5_0.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ma on 2016/7/9.
 */
public class PictureAndColorFragment extends android.support.v4.app.Fragment {
    @Bind(R.id.textview1)
    TextView textview1;
    @Bind(R.id.textview2)
    TextView textview2;
    @Bind(R.id.textview3)
    TextView textview3;
    @Bind(R.id.textview4)
    TextView textview4;
    @Bind(R.id.textview5)
    TextView textview5;
    @Bind(R.id.textview6)
    TextView textview6;
    @Bind(R.id.textview7)
    TextView textview7;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.layout_pictureandcolor, null);
        ButterKnife.bind(this, view);

        //从图片中提取颜色
        paletteColor();

        return view;
    }

    /**
     *  从图片中提取颜色
     */
    private void paletteColor() {
        // 1.获取图片
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.palette);
        // 2.使用palette从图片中提取颜色
       // Palette palette = Palette.from(bitmap).generate(); //该方法是同步获取,会阻塞UI
        //推荐使用异步方式
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            /**
             * 当提取到颜色后会回调此方法
             * @param palette
             */
            @Override
            public void onGenerated(Palette palette) {
                //Color.BLUE: 如提取不到颜色,默认显示的颜色
                textview2.setBackgroundColor(palette.getVibrantColor(Color.BLUE));
                textview3.setBackgroundColor(palette.getLightMutedColor(Color.BLUE));
                textview4.setBackgroundColor(palette.getDarkVibrantColor(Color.BLUE));
                textview5.setBackgroundColor(palette.getLightVibrantColor(Color.BLUE));
                textview6.setBackgroundColor(palette.getMutedColor(Color.BLUE));
                textview7.setBackgroundColor(palette.getDarkMutedColor(Color.BLUE));


                /*//通过获取采样对象来获取其中的颜色,推荐使用这种方式,因为我们可以在获取上面六中颜色的采样对象
                //后,系统会通过分析告诉我们,某一个对象中的哪些颜色适合用在什么地方,这里以获取有活力的颜色采\
                // 样对象为例
                Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                //使用有活力的颜色采样对象中适合文本的颜色
                textview2.setTextColor(vibrantSwatch.getBodyTextColor());
                //使用有活力的颜色采样对象中适合做背景的颜色
                textview2.setBackgroundColor(vibrantSwatch.getRgb());*/

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
