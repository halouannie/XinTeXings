package com.itheima.parallaxeffect87;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by lxj on 2016/7/15.
 */
public class ParallaxListView extends ListView {
    private static final String TAG = "ParallaxListView";

    public ParallaxListView(Context context) {
        super(context);
    }

    public ParallaxListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallaxListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int maxHeight;//最大高度，设定为图片本身的高度
    private int originalHeight;//最初的高度，其实就是120dp
    private ImageView parallaxImage;

    public void setParallaxImageView(ImageView imageView) {
        this.parallaxImage = imageView;

        //添加全局的布局监听器，来获取view的高度
        parallaxImage.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            /**
             * 当parallaxImage在父View中执行完layout之后调用
             */
            @Override
            public void onGlobalLayout() {
                parallaxImage.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                originalHeight = parallaxImage.getHeight();
//                Log.e(TAG, "originalHeight: "+originalHeight);
            }
        });

        maxHeight = parallaxImage.getDrawable().getIntrinsicHeight();
    }

    /**
     * 当listvieew滑动到头的时候，继续滑动会执行该方法；
     * 在该方法中可以获取到手指继续滑动的距离
     *
     * @param deltaY       手指在y方向滑动的距离,负值表示顶部到头，正值表示底部到头
     * @param isTouchEvent 是否是触摸滑动到头，true：是手指拖动到头，  false：惯性滑动到头
     * @return
     */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY,
                                   int scrollX, int scrollY,
                                   int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
//        Log.e(TAG, "deltaY: " +deltaY  +"  isTouchEvent:"+isTouchEvent);
        //如果是顶部到头，并且是手指拖动到头
        if (deltaY < 0 && isTouchEvent) {
            //根据deltaY让ImageView的高度增高
            if (parallaxImage != null) {
                int newHeight = parallaxImage.getHeight() - deltaY/2;
                //对newHeigh进行判断
                if (newHeight > maxHeight) newHeight = maxHeight;

                ViewGroup.LayoutParams params = parallaxImage.getLayoutParams();
                params.height = newHeight;
                parallaxImage.setLayoutParams(params);
//              parallaxImage.requestLayout();
            }
        }
        return super.overScrollBy(deltaX, deltaY, scrollX,
                scrollY, scrollRangeX, scrollRangeY,
                maxOverScrollX, maxOverScrollY, isTouchEvent);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                //让parallaxImage恢复到最初高度
                ValueAnimator animator = ValueAnimator.ofInt(parallaxImage.getHeight(),originalHeight);
                //监听动画值改变的进度，实现自己的动画逻辑
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int animatedValue = (int) animation.getAnimatedValue();
                        //将动画的值设置给imageView的高度
                        ViewGroup.LayoutParams params = parallaxImage.getLayoutParams();
                        params.height = animatedValue;
                        parallaxImage.setLayoutParams(params);
                    }
                });
                animator.setInterpolator(new OvershootInterpolator());
//                animator.setInterpolator(new BounceInterpolator());
                animator.setDuration(400).start();
                break;
        }
        return super.onTouchEvent(event);
    }
}
