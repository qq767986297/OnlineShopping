package com.bawei.onlineshopping.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.onlineshopping.R;
import com.bawei.onlineshopping.base.BaseActivity;
import com.bawei.onlineshopping.bean.Bean;
import com.bawei.onlineshopping.utils.NetUtils;
import com.google.gson.Gson;

import java.util.HashMap;

public class MainActivity extends BaseActivity {

    private Button bt_login;
    private EditText et_pass;
    private EditText et_name;
    private CheckBox cb;
    private TextView tv_reg;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        et_name = findViewById(R.id.et_name);
        et_pass = findViewById(R.id.et_pass);
        bt_login = findViewById(R.id.bt_login);
        cb = findViewById(R.id.cb);
        tv_reg = findViewById(R.id.tv_reg);
    }

    @Override
    protected void getData() {
        boolean wifi = NetUtils.getInstance().isWifi(this);
        if(wifi){
            final String path = "http://mobile.bwstudent.com/small/user/v1/register?";
            bt_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phone = et_name.getText().toString();
                    String pwd = et_pass.getText().toString();
                    HashMap<String, String> map = new HashMap<>();
                    map.put("phone",phone);
                    map.put("pwd",pwd);
                    NetUtils.getInstance().getUserReg(path,map, new NetUtils.ICallBack() {
                        @Override
                        public void onSuccess(String json) {
                            Gson gson = new Gson();
                            Bean bean = gson.fromJson(json, Bean.class);
                            String message = bean.getMessage();
                            Toast.makeText(MainActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                            Log.i("xxx",""+message);
                        }

                        @Override
                        public void onError(String mgs) {

                        }
                    });
                }
            });
        }else {
            Toast.makeText(this, "网络错误", Toast.LENGTH_SHORT).show();
        }
//为什么你点击没有任何效果 我用的buttn
    }
}
