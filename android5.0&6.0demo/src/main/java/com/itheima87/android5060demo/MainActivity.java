package com.itheima87.android5060demo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.text1)
    TextView text1;
    @Bind(R.id.text2)
    TextView text2;
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.vibrant)
    TextView vibrant;
    @Bind(R.id.vibrantLight)
    TextView vibrantLight;
    @Bind(R.id.vibrantDark)
    TextView vibrantDark;
    @Bind(R.id.muted)
    TextView muted;
    @Bind(R.id.mutedLight)
    TextView mutedLight;
    @Bind(R.id.mutedDark)
    TextView mutedDark;
    @Bind(R.id.btn_circle)
    Button btn_circle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


//        changeEvelation();

//        changeTranslationZ();

//        customOutline();

//        paletteColor();

        exeCircleAnim();

    }

    /**
     * 主动执行水波纹动画
     */
    private void exeCircleAnim() {
        btn_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //对api进行适配
                if(Build.VERSION.SDK_INT>=21){
                    Animator circularReveal = ViewAnimationUtils.createCircularReveal(btn_circle,  btn_circle.getWidth(), 0, 1,
                            btn_circle.getWidth() * 2);
                    circularReveal.setDuration(2000);
                    circularReveal.start();
                }else {
                    //使用其他方法解决，比如自定义View,或者第三方库
                }

            }
        });
    }

    private void paletteColor() {
        //1.获取图片
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.runtu);
        //2.使用palette从图片中拾取颜色
//        Palette palette = Palette.from(bitmap).generate();//该方式的同步获取，会阻塞UI
        //推荐使用异步方式
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            /**
             * 当拾取到颜色后会执行回调方法
             * @param palette
             */
            @Override
            public void onGenerated(Palette palette) {
//                showAllColor(palette);

                //通过获取采样对象来获取其中的颜色
                Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();//有活力颜色的采样对象
                vibrant.setTextColor(vibrantSwatch.getBodyTextColor());
                vibrant.setBackgroundColor(vibrantSwatch.getRgb());
            }
        });

    }

    /**
     * 显示所有的颜色
     */
    private void showAllColor(Palette palette){
        vibrant.setBackgroundColor(palette.getVibrantColor(Color.YELLOW));
        vibrantLight.setBackgroundColor(palette.getLightVibrantColor(Color.YELLOW));
        vibrantDark.setBackgroundColor(palette.getDarkVibrantColor(Color.YELLOW));

        muted.setBackgroundColor(palette.getMutedColor(Color.YELLOW));
        mutedLight.setBackgroundColor(palette.getLightMutedColor(Color.YELLOW));
        mutedDark.setBackgroundColor(palette.getDarkMutedColor(Color.YELLOW));

    }

    /**
     * 自定义轮廓
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void customOutline() {
        text2.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                //设置圆形的轮廓
//                outline.setOval(0,0,view.getWidth(),view.getHeight());

                //设置三角形的轮廓
//                Path path = new Path();
//                path.moveTo(view.getWidth()/2,0);//指订path的起点
//                path.lineTo(0,view.getHeight());//连到某个点
//                path.lineTo(view.getWidth(),view.getHeight());
//                path.close();//闭合，会将终点和起点连线
//                outline.setConvexPath(path);

                //设置圆角矩形的轮廓
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 15);
            }
        });

        //设置view按照轮廓进行裁剪
        text2.setClipToOutline(true);
    }

    private void changeTranslationZ() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(text1, "translationZ", 5, 45);
        animator.setDuration(3000);
        animator.start();
    }

    private void changeEvelation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(text1, "elevation", 5, 45);
        animator.setDuration(3000);
        animator.start();
    }
}
