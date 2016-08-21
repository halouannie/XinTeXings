package com.example.demo_swipedelete;

/**
 * 负责记录打开的条目，
 * 负责关闭已经打开的条目，
 * 负责判断当前是否有打开的条目
 * Created by ma on 2016/7/13.
 */
public class SwipeLayoutManager {
    private SwipeLayout currentSwipeLayout; //用来记录当前已经打开的SwipeLayout

    //单例,对象只有一个,那么对象中的变量肯定只有一个
    private static SwipeLayoutManager mInstance = new SwipeLayoutManager();
    private SwipeLayoutManager() {}
    public static SwipeLayoutManager getmInstance() {
        return mInstance;
    }

    /**
     * 记录打开的条目
     * @param swipeLayout
     */
    public void setSwipeLayout (SwipeLayout swipeLayout) {
        this.currentSwipeLayout = swipeLayout;
    }

    /**
     * 是否满足滑动条目的条件
     */
    public boolean shouldSwipe(SwipeLayout touchSwipeLayout) {
        if (currentSwipeLayout == null) {
            //说明当前没有打开的条目,可以滑动
            return true;
        }else {
            //否则说明有打开的条目,这时需要判断
            //如果是同一个条目则可以滑动,否则不苦役滑动
            return  currentSwipeLayout == touchSwipeLayout;
        }
    }

    /**
     * 关闭已经打开的条目
     */
    public void closeSwipeLayout() {
        if (currentSwipeLayout != null) {
            //调用swipelayout中的close方法关闭条目
            currentSwipeLayout.close();
        }
    }

    /**
     * 关闭时清空当前条目是否打开的信息
     */
    public void clearSwpiLayout() {
        //将currentswipeLayout置为null
        currentSwipeLayout = null;
    }
}
