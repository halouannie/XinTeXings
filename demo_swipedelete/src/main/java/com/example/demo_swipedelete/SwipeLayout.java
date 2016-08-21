package com.example.demo_swipedelete;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.Currency;

/**
 * Created by ma on 2016/7/13.
 */
public class SwipeLayout extends FrameLayout {

    private View contentView;
    private View deleteView;
    private int contentWith;
    private int contentHeight;
    private int deleteWidth;
    private int deleteHeight;
    private ViewDragHelper viewDragHelper;
    private float downX;
    private float downY;
    private long time;
    private int touchSlop;

    public SwipeLayout(Context context) {
        super(context);
        init();
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //定义记录滑动删除状态的变量
    public enum swipeState{
        open,close;
    }
    //记录当前状态的变量
    private swipeState mState = swipeState.close;

    /**
     * 当前完成布局填充之后调用，该方法调用的时候就知道自己有几个子View了,
     * 所以通常在该方法中获取子view的引用!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * 但是注意，该方法在onMeasure之前执行，所以在该方法中是获取不到子View的
     * 宽高
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //获取子view的引用,即子view的初始化
        contentView = getChildAt(0);
        deleteView = getChildAt(1);

    }

    /**
     * 当onMeasure执行之后执行，所以一般可以在该方法中获取view宽高
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        contentWith = contentView.getMeasuredWidth();
        contentHeight = contentView.getMeasuredHeight();
        deleteWidth = deleteView.getMeasuredWidth();
        deleteHeight = deleteView.getMeasuredHeight();

    }

    /**
     *  因为在这里布局不是覆盖的关系,所以不能使用framelayout默认的onlayout方法
     *  需要自己重写
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //super.onLayout(changed, l, t, r, b);
        //摆放contentView
        contentView.layout(0,0,contentWith,contentHeight);
        //摆放deleteView
        deleteView.layout(contentWith,0,contentWith+deleteWidth,deleteHeight);
    }

    /**
     * ViewDragHelper只是一个触摸事件的解析类(如GestureDecetor)，所以需要我们传递给它触摸事件，它才能工作;
     * 传递触摸事件需要两个方法  onInterceptTouchEvent, onTouchEvent
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //让viewDragHelper帮我们判断是否应该拦截
        boolean result = viewDragHelper.shouldInterceptTouchEvent(ev);
        if (!SwipeLayoutManager.getmInstance().shouldSwipe(this)) {//判断是否可以滑动
            //需要关闭已打开的条目
           SwipeLayoutManager.getmInstance().closeSwipeLayout();
            result = true;//拦截
        }
        return result;
    }

    /**
     *  不能滑动的时候,我就将事件拦截下来,将该事件给消费掉,但我消费(处理)这个事件的时候进行空处理,啥也不干,
     *  就是单纯的将这个事件给消费掉!!!
     * @param event
     * @return
     */
    //处理触摸事件,getX(): 根据子view自身来获取坐标; getRawX():根据屏幕来获取坐标
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //在每次滑动的时候需要先判断是否可以滑动
        if (!SwipeLayoutManager.getmInstance().shouldSwipe(this)) {
            //子view请求父view不拦截,不让listview滑动
            requestDisallowInterceptTouchEvent(true);
            //SwipeLayoutManager.getmInstance().closeSwipeLayout();
            return true;//消费掉事件,不让滑动
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //获取按下的坐标值
                downX = event.getX();
                downY = event.getY();
                time = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_MOVE:
                //获取按下的坐标值
                float moveX = event.getX();
                float moveY = event.getY();

                //计算距离
                float descX = moveX - downX;
                float descY = moveY - downY;
                if (Math.abs(descX) > Math.abs(descY)) {
                    //说明用户是想滑动删除的,不能让listview拦截事件
                    getParent().requestDisallowInterceptTouchEvent(true); //子view请求父view不拦截
                }
                break;
            case MotionEvent.ACTION_UP:
                //记录抬起的左边
                float upX = event.getX();
                float upY = event.getY();
                //计算按下和抬起的距离
                float dx = upX -downX;
                float dy = upY - downY;
                //计算按下和抬起两点之间的直线距离
                double distance = Math.sqrt(Math.pow(dx,2) + Math.pow(dy,2));
                //计算按下和抬起的时间差
                long times = System.currentTimeMillis() - time;
                //判断是否是点击事件
                if (distance < touchSlop && times<400) {
                    //说明是点击事件
                    if (listener != null) {
                        //如果监听器不为空,回调监听方法
                        listener.onClick();
                    }

                }
                break;
        }
        //将触摸事件传递该viewdraghelper解析,帮我们处理触摸事件
        viewDragHelper.processTouchEvent(event);
        return true;
    }



    /**
     * 初始化方法
     */
    public void init() {
        viewDragHelper = ViewDragHelper.create(this,callback);

        //获取触摸的结余触摸和点击之间的界限值
        touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    //回调方法
    ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        // 判断是否捕获当前所触摸的child的触摸事件,child: 当前所触摸的子view,pointerId: 触摸点的索引
        //返回true,就是需要捕获, 否则就是不需要捕获
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child ==contentView || child == deleteView;
        }

        //鸡肋的方法,但注意：该方法的返回值不要小于0，其返回值用来作为是否是水平方向滑动的条件之一,
        //并且还会用在计算松开手指平滑动画执行的动画时间上
        @Override
        public int getViewVerticalDragRange(View child) {
            return deleteWidth;
        }

        //控制子View在水平方向的移动,child : 当前触摸的子View; dx: 表示手指水平移动的距离
        //left: 表示ViewDragHelper认为你想让child的left变成的值,
        //返回的值，表示你真正想让child的left变成的值。返回的值才真正起决定作用
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == contentView) {
                //限制contenview,的最大最小left
                if (left>0){
                    left = 0;
                }else if (left<-deleteWidth) {
                    left = -deleteWidth;
                }
            }else if (child == deleteView) {
                //限制contenview,的最大最小left
                if (left>contentWith) {
                    left =contentWith;
                }else  if (left < (contentWith - deleteWidth)) {
                    left = contentWith - deleteWidth;
                }
            }
            return left;
        }

        //当child的位置改变之后调用,一般用让子View进行伴随移动,你动我也动
        //left: 移动之后最新的left
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            //System.out.println("contentView.getLeft(): " +deleteView.getLeft());
            if (changedView == contentView) {
                //让deleteView进行伴随移动
                int newLeft = deleteView.getLeft() + dx;
                deleteView.layout(newLeft,deleteView.getTop(),newLeft+deleteWidth,deleteView.getBottom());
            }else if (changedView == deleteView) {
                //让contentView进行伴随移动
                int  newLeft = contentView.getLeft() + dx;
                contentView.layout(newLeft,contentView.getTop(),newLeft+contentWith,contentView.getBottom());
            }

            //判断滑动删除 打开和关闭的逻辑
            if (contentView.getLeft() == -deleteWidth && mState != swipeState.open) {
                mState = swipeState.open; //这是为了避免重复打开重复关闭
                //说明此时是打开状态,应该记录一下当前状态
                SwipeLayoutManager.getmInstance().setSwipeLayout(SwipeLayout.this);
            }else if (contentView.getLeft() == 0 && mState != swipeState.close){
                mState = swipeState.close;
                //说明此时滑动删除关闭了,应该清空一下当前状态的记录
                SwipeLayoutManager.getmInstance().clearSwpiLayout();
            }

        }

        //手指抬起的时候会执行,但这个方法单独执行没有用,必须配合computeScroll()方法使用
        //releasedChild: 抬起的子View;  xvel: x方向滑动的速度
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            //System.out.println("contentView.getLeft(): " +contentView.getLeft());
            if (contentView.getLeft() > -deleteWidth/2) {
               // 需要关闭
                close();
            }else {
                // 需要打开
                open();
            }
        }
    };

    //打开侧滑菜单的方法
    public  void open() {
        viewDragHelper.smoothSlideViewTo(contentView,-deleteWidth,0);
        //单独执行不生效,需要配合下面的代码,这种方法更省内存
        //ViewCompat.postInvalidateOnAnimation(this); //v4包的不存在兼容问题
        postInvalidateOnAnimation(); //API需要为16以上,才能使用
    }

    //关闭侧滑菜单的方法
    public  void close() {
        viewDragHelper.smoothSlideViewTo(contentView,0,0);
        //单独执行不生效,需要配合下面的代码,这种方法更省内存
       // ViewCompat.postInvalidateOnAnimation(this); //v4包的不存在兼容问题
        postInvalidateOnAnimation(); //API需要为16以上,才能使用
    }

    //执行invalidate() 或ViewCompat.postInvalidateOnAnimation(),方法后都会自动调用该方法
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (viewDragHelper.continueSettling(true)){  //如果返回true表示操作还没有结束
            //继续执行操作
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private onSwipeLayoutClickListener listener;
    public void setonSwipeLayoutClickListener(onSwipeLayoutClickListener listener) {
        this.listener = listener;
    }
    //定义监听器
    public interface onSwipeLayoutClickListener{
        void onClick();
    }
}
