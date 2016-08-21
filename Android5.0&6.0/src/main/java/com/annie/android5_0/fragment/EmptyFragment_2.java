package com.annie.android5_0.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ma on 2016/7/19.
 */
public class EmptyFragment_2 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText("Annie2");
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(21);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
