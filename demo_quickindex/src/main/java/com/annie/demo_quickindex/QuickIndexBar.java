package com.annie.demo_quickindex;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Switch;

/**
 * Created by ma on 2016/7/15.
 */
public class QuickIndexBar extends View {

    private Paint paint;
    private String[] indexArr = {"A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"};
    private float x;
    private float cellHeight;
    private int index = -1; //当前触摸的索引,注意不能初始为0

    public QuickIndexBar(Context context) {
        super(context);
        init();
    }

    public QuickIndexBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public QuickIndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //在这里计算宽高
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        x = getMeasuredWidth()/2;
        cellHeight = getMeasuredHeight() *1f/indexArr.length;//计算每个格子的高度
    }

    //初始化
    public void init() {
        //画出26个字母,需要画笔,所以此处先初始化画笔
        paint = new Paint(Paint.ANTI_ALIAS_FLAG); //设置抗锯齿
        paint.setTextSize(16); //设置所画字体的大小
        paint.setColor(Color.WHITE);
        //设置文字绘制的起点，默认是文字的左下角
        //设置文字的起点为底边的中心
        paint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i= 0;i<indexArr.length;i++) {
            //y坐标：格子高度一半 + 文字高度一半 + i*格子高度
            float Y =cellHeight/2 +getTextHeight(indexArr[i])/2 + i*cellHeight;

            //改变画笔的颜色,如果正在绘制的等于当前触摸的则将文字颜色更改为黑色
            paint.setColor(i == index?Color.BLACK:Color.WHITE);
            canvas.drawText(indexArr[i],x,Y,paint);
        }
    }

    //处理触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                //因为在按下和移动的过程中都需要获取字母值,代码重复,所以使用case穿透
                float downY = event.getY();
                if (index != (int) (downY/cellHeight)) {
                    index = (int) (downY/cellHeight);

                    //这里需要对索引index进行安全性检查
                    if (index>=0 && index<indexArr.length) {
                        String letter = indexArr[index];
                        //当字母发生改变的时候,调用回调方法
                        if (listener != null) {
                            listener.onLetterChanged(letter);
                        }
                       // System.out.println(letter);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                //抬起的时候需要将索引index重置为-1,否则index总会记录下之前按下的位置,
                //这时抬起后再次按下该位置时会没有反应
                index = -1;
                break;
        }

        //因为文字的颜色是使用画笔画出的,所以如果想要改变文字的颜色就必须要重新绘制文字
        //调用invalidate() 方法,会自动再次执行onDraw方法,进行重绘
        invalidate();

        return true;
    }

    //当计算出当前触摸的字母之后，需要将触摸字母改变的事件暴露给外界,让外界可以进行相应的处理
    //所以定义监听器,并在字母改变时进行回调
    private OnTouchLetterChanged listener;
    public void setOnTouchLetterChanged(OnTouchLetterChanged listener) {
        this.listener = listener;
    }
    public interface OnTouchLetterChanged {
        void onLetterChanged(String letter);
    }

    //获取文字的高度
    public int getTextHeight(String text) {
        Rect bounds = new Rect();
        //该方法执行完，bounds就有值了
        paint.getTextBounds(text,0,text.length(),bounds );
        return  bounds.height();
    }

}
