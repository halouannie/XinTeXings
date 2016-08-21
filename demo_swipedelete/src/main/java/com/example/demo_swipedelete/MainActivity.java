package com.example.demo_swipedelete;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listview);

        //准备数据
        for (int i = 0; i < 52; i++) {
            list.add("Annie +" + i);
        }
        //设置适配器展示数据
        listView.setAdapter(new MyAdapter());
    }

    public class MyAdapter extends BaseAdapter {

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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null){
                convertView = View.inflate(MainActivity.this, R.layout.item_list, null);
                holder = new ViewHolder(convertView);

                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvName.setText(list.get(position));
            //设置条目点击事件!!!!!!!
            holder.swipelayout.setonSwipeLayoutClickListener(new SwipeLayout.onSwipeLayoutClickListener() {
                @Override
                public void onClick() {
                    Toast.makeText(MainActivity.this,"Annie + "+position,Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;
        }
    }

    static class ViewHolder {
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_delete)
        TextView tvDelete;
        @Bind(R.id.swipelayout)
        SwipeLayout swipelayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
