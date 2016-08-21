package com.annie.demo_imageloader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PicassoActivity extends AppCompatActivity {

    @Bind(R.id.listview)
    ListView listview;
    private String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picasso);
        ButterKnife.bind(this);

        // 1.加载数据
        data = getData();
        // 2.设置适配器
        listview.setAdapter(new MyAdapter());
    }

    //listview适配器
    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public Object getItem(int position) {
            return data[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null){
                convertView = View.inflate(PicassoActivity.this, R.layout.item_image, null);
                holder = new ViewHolder();

                holder.iv = (ImageView) convertView.findViewById(R.id.item_iv_icon);
                holder.tv = (TextView) convertView.findViewById(R.id.item_tv_url);

                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            //加载网络图片
            holder.tv.setText(data[position]);

            Picasso
                    .with(PicassoActivity.this)//指定页面上下文
                    .load(data[position])//指定网络图片的路径
                    .centerCrop()//scaletype设置缩放,scaleType="centerCrop":先放大在剪裁,同时必须重新制定宽高
                    .placeholder(R.drawable.placeholder)//placeholder占位图 加载前的显示图片,就是先把位置占了
                    .resize(110,90)//设置缩放必须指定宽高  注意如果设置大小，性能会提升,因为会对原图进行压缩处理
                    .error(R.mipmap.error)//加载出错的时候展示给用户的图片
                    .noFade()//设置不需要渐渐显示的动画效果
                    .into(holder.iv); //展示给哪个控件

            /**
             * 小结:load()中也可以使用资源或文件的地址
             * Picasso.with(context).load(R.drawable.landing_screen).into(imageView1);
             * Picasso.with(context).load("file:///android_asset/DvpvklR.png").into(imageView2);
             * Picasso.with(context).load(new File(...)).into(imageView3);
             */

            return convertView;
        }
    }

    public static  class ViewHolder
    {

        public ImageView iv;
        public TextView tv;
    }
    //设置数据
    private String[] getData() {
        return new String[]{
                "http://10.0.2.2:hah.jpg",
                "http://10.0.2.2:8080/hah.jpg",
                "http://img2.imgtn.bdimg.com/it/u=2897351070,2483429160&fm=21&gp=0.jpg",
                "http://img3.imgtn.bdimg.com/it/u=273589938,3254308965&fm=21&gp=0.jpg",
                "http://img4.imgtn.bdimg.com/it/u=3663725821,1758763471&fm=15&gp=0.jpg",
                "http://img4.imgtn.bdimg.com/it/u=3598461135,406107685&fm=21&gp=0.jpg",
                "http://img4.imgtn.bdimg.com/it/u=565758384,3198652654&fm=21&gp=0.jpg",
                "http://img2.imgtn.bdimg.com/it/u=439641664,2822628958&fm=21&gp=0.jpg",
                "http://img5.imgtn.bdimg.com/it/u=1940183546,4195527565&fm=21&gp=0.jpg",
                "http://img1.imgtn.bdimg.com/it/u=3225255375,1325995730&fm=21&gp=0.jpg",
                "http://img1.imgtn.bdimg.com/it/u=881723566,3180104955&fm=11&gp=0.jpg",
                "http://img0.imgtn.bdimg.com/it/u=3639439277,1341742535&fm=21&gp=0.jpg",
                "http://img1.imgtn.bdimg.com/it/u=3974445714,251639485&fm=21&gp=0.jpg",
                "http://img3.imgtn.bdimg.com/it/u=621080307,1729495524&fm=21&gp=0.jpg"
        };
    }

}
