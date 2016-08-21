package com.annie.android5_0.fragment;

import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.graphics.Outline;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.android5_0.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ma on 2016/7/8.
 */
public class ShadowFragment extends android.support.v4.app.Fragment {
    @Bind(R.id.text3)
    TextView text3;
    @Bind(R.id.text4)
    TextView text4;
    @Bind(R.id.imageview1)
    ImageView imageview1;
    @Bind(R.id.imageview2)
    ImageView imageview2;
    @Bind(R.id.imageview3)
    TextView imageview3;
    @Bind(R.id.imageview4)
    ImageView imageview4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.layout_shadow, null);
        ButterKnife.bind(this, view);

        //动态改变控件的高度,形成控件冉冉升起的效果
        setAnimation();

        imageview4.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                //设置圆形轮廓
                 outline.setOval(0,0,view.getWidth(),view.getHeight());

                //设置圆角矩形轮廓
                //outline.setRoundRect(0,0,view.getWidth(),view.getHeight(),21);
                //outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 21);

                /*//设置三角形轮廓,用path理论上可以勾勒出任何形状,只是大多数不支持,无法显示
                Path path = new Path();
                path.moveTo(view.getWidth() / 2, 0);//指定path的起点
                path.lineTo(0, view.getHeight());//连接到某个点
                path.lineTo(view.getWidth(), view.getHeight());
                path.close();//闭合,会将终点和起点连线
                outline.setConvexPath(path); //设置勾勒出的形状*/

                //通过剪裁实现圆形头像的设置
                view.setClipToOutline(true);
            }
        });
        return view;
    }

    /**
     * 动态改变控件的高度,形成控件冉冉升起的效果
     */
    private void setAnimation() {
        //动态改变控件的高度,形成控件冉冉升起的效果
        //参数1: 要改变的控件    参数2: 要改变的属性名
        ObjectAnimator ob = ObjectAnimator.ofFloat(text4, "elevation", 6, 45);
        ob.setDuration(3000);
        ob.start();
        ; //开始动画
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
