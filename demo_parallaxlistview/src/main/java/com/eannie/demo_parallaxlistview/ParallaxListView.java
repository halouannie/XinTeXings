package com.eannie.demo_parallaxlistview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by ma on 2016/7/16.
 */
public class ParallaxListView extends ListView {

    private int imageHeight;
    private int imageWidth;
    private int downY;

    public ParallaxListView(Context context) {
        super(context);
    }

    public ParallaxListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallaxListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int originalHeight;
    private int originalWidth;
    //让外界传入imageview对象,并通过全局的布局监听器来获取imageview最初的高度
    private  ImageView imageView;
    private  LinearLayout container;
    public void setImageview (final ImageView imageview) {
        this.imageView = imageview;


        //获取imageview最初的高度
        imageview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            //当imageview在父view中执行完layout之后调用
            @Override
            public void onGlobalLayout() {
                //用完立即移除
                imageview.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                originalHeight = imageview.getHeight(); //获取imageview最初的高度
                originalWidth = imageview.getWidth(); //获取imageview最初的宽度
            }
        });
    }

    /**
     * 当listview滑动到头，继续滑动的时候会调用该方法，在该方法中可以获取到手指继续滑动的距离
     * 所有能滑动的listview滑动到头的时候都会调用该方法
     * 因为如果重写ontouchevent方法的话,可能会导致listview不能滑动,所以这里使用overScrollBy方法
     * @param deltaY: 手指在y方向滑动的距离,负值表示顶部到头; 正值表示底部到头
     * @param scrollY: 没啥用,一般为0
     * @param scrollRangeY: 没啥用,一般为0
     * @param maxOverScrollY:最大可以超过滚动的Y 方向的距离,即到头后还可以继续滑动距离
     * @param isTouchEvent: 表示是否是触摸滑动到头; true: 表示是触摸滑动到头;  false: 表示是惯性滑动到头
     * @return
     */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY,
                                   int scrollX, int scrollY,
                                   int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        if (deltaY<0 && isTouchEvent) {
            //如果是顶部滑动到头,并且是触摸滑动的时候,增大imageview的高度
            int newHeight = imageView.getHeight() - deltaY;
            int newWidth = imageView.getWidth() - deltaY;

            //获取imageview中图片的最大高度,超出顶部的滑动距离不能超出图片的高度
            imageHeight = imageView.getDrawable().getIntrinsicHeight();
           // imageWidth = imageView.getDrawable().getIntrinsicWidth();
            if (newHeight > imageHeight) {
                    newHeight = imageHeight;
            }

           // LinearLayout.LayoutParams params  = (LinearLayout.LayoutParams) imageView.getLayoutParams();
            //ViewGroup.LayoutParams params = imageView.getLayoutParams();

            //params.height = imageView.getBottom()-deltaY;
           // imageView.setLayoutParams(params);

            imageView.layout(imageView.getLeft()+deltaY,0,imageView.getRight()-deltaY,imageView.getBottom()-deltaY);
            System.out.println(imageView.getLeft()+","+imageView.getRight()+","+imageView.getTop()+","+imageView.getBottom());
            imageView.requestLayout();  //获将注释解开。将该句注释掉也可以
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
                scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final ValueAnimator animator;
         switch (ev.getAction()) {
              case MotionEvent.ACTION_UP:
                  //通过值动画让imageview的高度一点点的回到最初的位置高度
                  animator = ValueAnimator.ofInt(imageView.getHeight(),originalHeight);
                  //监听动画值的改变实现自己的动画
                  animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                      @Override
                      public void onAnimationUpdate(ValueAnimator animation) {
                          //获取动画的值，
                          int animateValue = (int) animator.getAnimatedValue();
                          //将动画的值设置给imageview的高度
                          ViewGroup.LayoutParams params = imageView.getLayoutParams();
                          params.height = animateValue;
                          //params.width = imageView.getWidth() - (imageView.getHeight() - animateValue);

                          imageView.setLayoutParams(params);

                      }
                  });
                  animator.setInterpolator(new OvershootInterpolator(3));
                  animator.setDuration(400).start();
                  break;
            /* case MotionEvent.ACTION_MOVE:
                 int moveY = (int) ev.getY();
                 int distY = moveY - downY;
                 //通过值动画让imageview的高度一点点的回到最初的位置高度
                 animator = ValueAnimator.ofInt(moveY,downY);
                 //监听动画值的改变实现自己的动画
                 animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                     @Override
                     public void onAnimationUpdate(ValueAnimator animation) {
                         //获取动画的值，
                         int animateValue = (int) animator.getAnimatedValue();imageView.setScaleY(1 + (animateValue - originalHeight)/originalHeight);
                         ViewGroup.LayoutParams params = imageView.getLayoutParams();
                         params.height = animateValue;
                         //params.width = imageView.getWidth() - (imageView.getHeight() - animateValue);

                         imageView.setLayoutParams(params);
                     }
                 });
                 //animator.setInterpolator(new OvershootInterpolator(3));
                 animator.start();
                 System.out.println("哈哈哈哈哈啊哈哈哈哈哈");
                 break;*/
                  /*animator.setInterpolator(new OvershootInterpolator(3));
                  animator.setDuration(400).start();*/
                 // break;
             case MotionEvent.ACTION_DOWN:
                 downY = (int) getY();
                 break;
          }
        return super.onTouchEvent(ev);
    }
}
