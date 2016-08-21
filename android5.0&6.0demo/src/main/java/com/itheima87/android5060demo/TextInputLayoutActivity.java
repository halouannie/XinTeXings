package com.itheima87.android5060demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lxj on 2016/7/7.
 */
public class TextInputLayoutActivity extends AppCompatActivity {
    @Bind(R.id.et_username)
    EditText etUsername;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.inputLayout)
    TextInputLayout inputLayout;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.frameLayout)
    FrameLayout frameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textinput);
        ButterKnife.bind(this);

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 9) {
                    inputLayout.setErrorEnabled(true);
                    inputLayout.setError("密码长度不能超过9位");
                } else {
                    inputLayout.setErrorEnabled(false);//禁用错误提示
                }
            }
        });


        //
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(frameLayout,"俺是SnackBar",Snackbar.LENGTH_LONG)
                        .setAction("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(TextInputLayoutActivity.this,"取消了",0).show();
                            }
                        })
                        .show();
            }
        });


    }
}
