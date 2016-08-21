package com.annie.demo_slidingmenu;

import android.animation.ArgbEvaluator;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SyncStatusObserver;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.GpsStatus;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * 一般情况下，我们要自定义ViewGroup的时候可以选择继承FrameLayout，而不是去继承ViewGroup，
 * 原因是FrameLayout已经帮我们实现了onMeasure，并且FrameLayout是最轻量级的
 * 注: onMeasure(), onLayout(), getChildCount, getChildAt(j), onTouchEvent()
 * onFinishInflate(), ViewDragHelper, measureChild
 * Created by Free on 2016/7/12.
 */
public class SlideMenu extends FrameLayout {

    private View menuView;
    private View mainView;
    private ViewDragHelper viewDragHelper;
    private int mainWidth;
    private int menuWidth;
    private int dragRange;
    private FloatEvaluator floatEvaluator;
    private ArgbEvaluator argbEvaluator;
    private Scroller scroller;

    public SlideMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlideMenu(Context context) {
        super(context);
        init();
    }

    public SlideMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //定义当前状态常量
    public enum Drag {
        open,close;
    }
    //定义当前状态常量
    private Drag mState = Drag.close;

    //向外界提供获取当前侧滑菜单状态的方法
    public Drag getmState() {
        return mState;
    }

    /**
     * 不能再构造中获取子view，因为这时根本不知道有几个子view
     * 当前完成布局填充之后调用，该方法调用的时候就知道自己有几个子View了,
     * 所以通常在该方法中获取子view的引用!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * 但是注意，该方法在onMeasure之前执行，所以在该方法中是获取不到子View的
     * 宽高
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //获取子view的引用,即子view的初始化
        menuView = getChildAt(0); //布局文件中的第一个子view
        mainView = getChildAt(1); //布局文件中的第二个子view
    }

    /**
     * 当onMeasure执行之后执行，所以一般可以在该方法中获取宽高
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mainWidth = mainView.getMeasuredWidth();
        menuWidth = menuView.getMeasuredWidth();

        //getMeasuredWidth(): 获取当前控件的宽（slidingmenu的宽）
        dragRange = (int) (getMeasuredWidth() * 0.6);
    }

    /**
     * 初始化方法
     */
    public void init() {
        //this: 父view,表示执行当前内部子view的拖拽
        viewDragHelper = ViewDragHelper.create(this,callback );
        //谷歌提供的计算器
        floatEvaluator = new FloatEvaluator();

        //颜色的计算器
        argbEvaluator = new ArgbEvaluator();

        scroller = new Scroller(getContext());
    }

    /**
     * ViewDragHelper只是一个触摸事件的解析类(如GestureDecetor)，所以需要我们传递给它触摸事件，它才能工作;
     * 传递触摸事件需要重写两个方法 onTouchEvent, onInterceptTouchEvent
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //让viewDragHelper帮我们判断是否应该拦截
        boolean result = viewDragHelper.shouldInterceptTouchEvent(ev);
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //将触摸事件传递给viewdraghelper解析,帮我们处理触摸事件，这时回调才能生效
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    //回调方法
    ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        /**
         *  判断是否捕获当前所触摸的child的触摸事件
         * @param child: 当前所触摸的子view
         * @param pointerId: 触摸点的索引
         * @return: 返回true,就是需要捕获, 否则就是不需要捕获
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mainView || child == menuView;
        }

        /**
         * 当view被捕获时的回调
         * @param capturedChild
         * @param activePointerId
         */
        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        /**
         * 鸡肋的方法
         * 注意：该方法的返回值不要小于0，其返回值用来作为是否是水平方向滑动的条件之一
         * 。并且还会用在计算松开手指平滑动画执行的动画时间上
         * @param child
         * @return
         */
        @Override
        public int getViewHorizontalDragRange(View child) {
            return dragRange;
        }

        /**
         * 控制子View在水平方向的移动
         * @param child 当前触摸的子View
         * @param left  表示ViewDragHelper认为你想让child的left变成的值，并且帮你事先计算好了，
         *              就是当你手指移动dx时,系统就认为你想让child移动dx距离,就通过公式:
         *              left=child.getLeft()+dx,帮你计算好了left的值,但用不用随你自己!!!!!!
         * @param dx    表示手指水平移动的距离
         * @return 返回的值，表示你真正想让child的left变成的值。返回的值才真正起决定作用!!!!!!!!
         * 正数:向右移动; 负数:向左移动
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == mainView) {  //让限制只对主界面生效,对侧滑菜单不生效
                // 对left的值进行判断,确定其最大的移动范围
                left = getNewLeft(left);
            }/*else if (child ==menuView) {
                left = 0;//对左侧菜单进行限制,让其原地不动
            }*/

            return left; //返回left，左右就可以随便移动了
        }

        /**
         * 控制子View在垂直方向的移动
         * @param child 当前触摸的子View
         * @param top  表示ViewDragHelper认为你想让child的top变成的值，并且帮你事先计算好了，
         *              top=child.getTop()+dy
         * @param dy    表示手指垂直移动的距离
         * @return 返回的值，表示你真正想让child的top变成的值。返回的值真正起决定作用
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return top;  //返回top上下就可以随便移动了
        }*/

        /**
         * 当child的位置改变之后调用（即子view移动之后调用）,一般用让子View进行伴随移动,你动我也动
         * @param changedView  移动的子View
         * @param left  移动之后最新的left
         * @param top   移动之后最新的top
         * @param dx    水平移动的距离
         * @param dy    垂直移动的距离
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            //System.out.println("left: " + left +" dx: "+dx);
            //根据menuview的滑动距离.让mainview进行伴随移动
            if (changedView == menuView){
                //固定住左侧菜单，滑动左侧菜单时，菜单不动，但主界面伴随移动
                menuView.layout(0,0,menuWidth,menuView.getBottom());
                //根据menuview的移动距离,获取mainview对应的left值
                int newLeft = mainView.getLeft() + dx;

                // 对left的值进行判断,确定其伴随移动最大的移动范围
                newLeft = getNewLeft(newLeft);
                //实现主界面的伴随移动
                mainView.layout(newLeft,mainView.getTop(),mainWidth+newLeft, mainView.getBottom());
            }

            //当view滑动的时候进行伴随动画
            // 1.因为动画时依据滑动进度来执行的所以要先计算mainView的滑动进度
            float percent = mainView.getLeft() * 1f / dragRange;
            // 2.执行动画
            execAnimation(percent);

            //回调接口的方法,mState != Drag.open: 是为了避免该方法被重复调用
            if (mainView.getLeft() == dragRange & mState != Drag.open) {
                //当view滑动到最大宽度是=时,说明菜单打开了,执行外界传进来的open方法
                mState = Drag.open;
                if (dragListener !=null) {
                    dragListener.open();
                }
            }else if (mainView.getLeft() == 0 & mState != Drag.close) {
                //当view滑动到最小宽度时,说明菜单关闭了,执行外界传进来的close方法
                mState = Drag.close;
                if (dragListener != null) {
                    dragListener.close();
                }
            }
            //如果监听不为空直接回调draging
            if (dragListener != null){
                dragListener.draging(percent);
            }

        }

        /**
         * 手指抬起的时候会执行,但这个方法单独执行没有用,必须配合computeScroll()
         * @param releasedChild 抬起的子View
         * @param xvel  x方向滑动的速度
         * @param yvel  y方向滑动的速度
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (mainView.getLeft() > dragRange/2) {
                open();
            }else {
                close();
            }
        }
    };

    //关闭侧滑菜单的方法
    private void close() {
        //这里如果用layout方法布下局的话,会瞬间关闭,缺少一种平滑的效果!!!
        viewDragHelper.smoothSlideViewTo(mainView,0,mainView.getTop());
        ViewCompat.postInvalidateOnAnimation(SlideMenu.this); //调用该方法自动执行computeScroll()!!!
    }

    //打开侧滑菜单的方法
    private void open() {
        //这里如果用layout方法布下局的话,会瞬间打开,缺少一种平滑的效果!!!
        viewDragHelper.smoothSlideViewTo(mainView,dragRange,mainView.getTop());
        ViewCompat.postInvalidateOnAnimation(SlideMenu.this); //这种方法更省内存

                /*//scroller的写法
                scroller.startScroll(X,Y 的起始位置，各自的移动距离，持续时间);
                invalidate();*/
    }

    /**
     * 执行invalidate() 或ViewCompat.postInvalidateOnAnimation(),方法后都会自动调用该方法
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        /*//scroller的写法
        if(scroller.computeScrollOffset()){ //判断动画是否结束
            int currX = scroller.getCurrX();
            int currY = scroller.getCurrY();
            scrollTo(currX,currY);
            invalidate();
        }*/

        //ViewDragHelper的写法
        if (viewDragHelper.continueSettling(true)) {  //如果返回true表示动画还没有结束
            //继续执行动画
            ViewCompat.postInvalidateOnAnimation(SlideMenu.this);
        }
    }

    /**
     * 执行动画
     */
    private void execAnimation(float percent) {
        // 2.根据滑动进度来执行一系列的动画,
        // 注:因为此处的代码会频繁执行,所以不能使用animation动画,因为这样会相当于创建了无数个动画!!!!!
        // 让mainview进行缩放
        // percent: 0-->1
        //scale: 1-->0.8
        //value: startValue + (endValue-startValue)*fraction
        /*float scale = 1f - (1f - 0.8f)*percent;
        mainView.setScaleY(scale); //将Y 根据滑动进度缩放为原先的0.8
        mainView.setScaleX(scale); //将X 根据滑动进度缩放为原先的0.8*/
//随着百分比进度一点点进行缩放，最终缩放到原先的0.8倍
        //也可以用谷歌提供的类来实现,参1:进度; 参2,参3:从哪到哪
        // 1.让mainview来实现缩放动画
        mainView.setScaleY(floatEvaluator.evaluate(percent,1f,0.8f));
        mainView.setScaleY(floatEvaluator.evaluate(percent,1f,0.8f));

        // 2.让menuView执行动画,缩放动画和平移动画
        menuView.setScaleY(floatEvaluator.evaluate(percent,0.3f,1f)); //缩放动画
        menuView.setScaleX(floatEvaluator.evaluate(percent,0.3f,1f));

        //去掉该平移动画，效果也很好看，随着主界面的缩小，菜单界面从主界面下慢慢增大出现（这里默认的缩放中心是view的中心）
        menuView.setTranslationX(floatEvaluator.evaluate(percent,-menuWidth/2,0f));//平移动画

        //实现3D旋转动画
        /*menuView.setRotationY(floatEvaluator.evaluate(percent,-90,0));
        mainView.setRotationY(floatEvaluator.evaluate(percent,0,90));*/

        // 3.给SlideMenu的背景图片添加颜色的遮罩效果,这个效果也可以用在清理垃圾时,随着清理过程颜色的一个渐变
        if (getBackground() != null){
            //颜色的计算器,PorterDuff.Mode.SRC_OVER:在背景上面加一层遮罩效果
            getBackground().setColorFilter((Integer) argbEvaluator.evaluate(percent, Color.BLACK,Color.TRANSPARENT)
                    ,PorterDuff.Mode.SRC_OVER);
        }
    }

    /**
     * 对left的值进行判断
     * @param newLeft
     * @return
     */
    private int getNewLeft(int newLeft) {
        if (newLeft > dragRange){
            newLeft = dragRange;
        }else if (newLeft < 0){
            newLeft = 0;
        }
        return newLeft;
    }


    private DragListener dragListener;
    public void setDragListener(DragListener dragListener) {
        this.dragListener = dragListener;
    }
    //设置侧滑菜单的回调接口
    public interface DragListener {
        void open(); //和上面的open,close方法没啥关系
        void close();

        /**
         * 滑动过程中的百分比回调的方法
         */
        void draging(float percent);
    }


//=======================================================================================
   /* //测量的方法,测量自己和子view的宽高,getChildCount   getChildAt
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0;i<getChildCount();i++) {
            View view = getChildAt(i);
            //测量子view
            measureChild(view,widthMeasureSpec,heightMeasureSpec);
        }
    }

    //摆放子view
    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        for (int j= 0;j<getChildCount();j++) {
            View view = getChildAt(j);
            view.layout(0,0,view.getMeasuredWidth(),view.getMeasuredHeight());
        }
    }*/

//==========================================================================================

}
