package com.annie.demo_imageloader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //Glige
    public void glide(View view) {
        Intent intent = new Intent(this,GligeActivity.class);
        startActivity(intent);
    }

    //Picasso
    public void picasso(View view) {
        Intent intent = new Intent(this,PicassoActivity.class);
        startActivity(intent);

    }

    //Fresco
    public void fresco(View view) {
        Intent intent = new Intent(this,FrescoActivity.class);
        startActivity(intent);

    }

}
