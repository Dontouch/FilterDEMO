package com.flamingo.filterdemo.activity;

import android.os.Bundle;
import android.os.Handler;

import com.flamingo.filterdemo.R;

/**
 * Created by Dontouch on 16/6/15.
 */
public class WelcomeActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.activity_welcome);
        // 通过handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                // 跳转至登入界面
                SkipActivityforClass(WelcomeActivity.this, HomeActivity.class);
                finish();
            }
        }, 1000);
    }
}
