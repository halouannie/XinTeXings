package com.annie.demo_slidingmenu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * 当SlideMenu处于打开的状态的时候，能够拦截并消费掉所有的触摸事件
 * Created by ma on 2016/7/13.
 */
public class MyLinearLayout extends LinearLayout {
    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //获取slidemenu对象
    private SlideMenu slideMenu;
    public void setSlideMenu(SlideMenu slideMenu) {
        this.slideMenu = slideMenu;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //如果slideMenu不为空并且侧滑菜单处于打开状态时,拦截事件,让主菜单的listview不能滑动!!!
        if (slideMenu != null && slideMenu.getmState() == SlideMenu.Drag.open) {
            //拦截事件
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     *  拦截之后之所以还要消费掉是因为,如果这里不消费的话,事件还会向上回传
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //如果Slidemenu处于打开的状态,则消费掉事件,让主菜单的listview不能滑动!!!
        if (slideMenu != null && slideMenu.getmState() == SlideMenu.Drag.open) {
            //需要消费掉事件
            return true;
        }
        return super.onTouchEvent(event);
    }
}
