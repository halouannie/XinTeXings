package com.annie.android5_0.adapter;

import android.content.Context;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.annie.android5_0.R;

/**
 * Created by ma on 2016/7/8.
 */
public class leftMenuAdapter extends BaseAdapter {

    private String[] str ;
    private Context context;

    //通过构造方法来获取要展示的数据
    public leftMenuAdapter(String[] str, Context context) {
        this.str = str;
        this.context = context;
    }

    @Override
    public int getCount() {
        return str.length;
    }

    @Override
    public Object getItem(int position) {
        return str[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //System.out.println(str[position] + "dddddddddddddddddddd");
        if (convertView == null) {
            convertView =  View.inflate(context, R.layout.listview_items,null);
        }

        TextView textView =   (TextView) convertView.findViewById(R.id.listview_item);
        textView.setText(str[position]);
        return convertView;
    }
}
