package com.annie.demo_quickindex;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ma on 2016/7/15.
 */
public class Myadapter extends BaseAdapter {
    private Context context;
    ArrayList<Friend> list = new ArrayList<>();
    public  Myadapter(ArrayList<Friend> list,Context context) {
        this.list = list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context,R.layout.adapter_friend,null);
            holder = new ViewHolder();
            holder.letter = (TextView) convertView.findViewById(R.id.tv_letter);
            holder.name = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(list.get(position).getName());

        //判断如果当前的字母等于上一个字母则隐藏当前的字母条目
        //获取首字母!!!!!!!!!u  优化后!!!!
        String firstWord = list.get(position).getPinyin().charAt(0) +"";
        if (position >0) {
            String lastWord = list.get(position -1).getPinyin().charAt(0) +"";
            if (firstWord.equals(lastWord)) {
                //说明当前字母和上一个字母相同,需要隐藏当前的
                holder.letter.setVisibility(View.GONE);
            }else {
                //说明不相等。则显示
                //由于是复用的，所以要设置为可见
                holder.letter.setVisibility(View.VISIBLE);
                holder.letter.setText(firstWord);
            }
        }else {
            holder.letter.setVisibility(View.VISIBLE);
            holder.letter.setText(firstWord);
        }
        return convertView;
    }

    static class ViewHolder{
        TextView letter,name;
    }
}
