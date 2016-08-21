package com.annie.android5_0.fragment;

import android.animation.Animator;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;

import com.annie.android5_0.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ma on 2016/7/9.
 */
public class newAnimatorFragment extends android.support.v4.app.Fragment {
    @Bind(R.id.button1)
    Button button1;
    @Bind(R.id.button2)
    Button button2;
    @Bind(R.id.button3)
    Button button3;
    @Bind(R.id.button4)
    Button button4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.layout_new_animator, null);
        ButterKnife.bind(this, view);

        //创建水波纹动画
        exeCircleAnim();

     return view;
    }

    /**
     *  创建水波纹动画
     */
    private void exeCircleAnim() {
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //参1: 设置水波纹的控件  参2,参3:表示水波纹动画开始的圆心坐标,这里从控件的右上角开始
                //参4: 动画开始时的半径()不宜过大  参5: 动画结束时的半径(本应该大于控件的斜对角线)
                Animator animator = ViewAnimationUtils.createCircularReveal(button4, button4.getWidth(), 0, 2, button4.getWidth() * 2);
                animator.setDuration(1500);
                animator.start();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
