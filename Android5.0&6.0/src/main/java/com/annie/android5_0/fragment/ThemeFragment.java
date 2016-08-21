package com.annie.android5_0.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.annie.android5_0.R;
import com.annie.android5_0.activity.MainActivity;

/**
 * Created by ma on 2016/7/8.
 */
public class ThemeFragment extends android.support.v4.app.Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(),R.layout.layout_theme,null);
        Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.在MainActivity中定义一个主题的静态变量,用于判断是否需要切换主题
                //2.判断当前Activity的主题
                if (MainActivity.THEME != R.style.BLACK) {
                    //3.切换主题
                    MainActivity.THEME = R.style.BLACK;
                }else {
                    MainActivity.THEME = R.style.AppTheme;
                }
                //4.关闭当前Activity，去掉Activity默认的进出动画，重启Activity，setTheme使主题生效
                reLoadActivity();
            }
        });
        return view;
    }

    /**
     * 重新启动activity
     */
    private void reLoadActivity() {
        //销毁当前activity
        getActivity().finish();
        //重新启动activity
        Intent intent = new Intent(getActivity(),MainActivity.class);
        //取消切换动画,使切换效果更为平滑
        getActivity().overridePendingTransition(0,0);
        startActivity(intent);

    }
}
