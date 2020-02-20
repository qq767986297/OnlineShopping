package com.bawei.onlineshopping.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        //找控件
        initView();
        //处理数据
        getData();
    }




    protected abstract int getLayoutRes();
    protected abstract void initView();
    protected abstract void getData();
}
