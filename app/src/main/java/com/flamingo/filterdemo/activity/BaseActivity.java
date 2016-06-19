package com.flamingo.filterdemo.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.flamingo.filterdemo.core.BaseInterfaces;

/**
 * Created by Dontouch on 16/6/15.
 */
public class BaseActivity extends FragmentActivity implements BaseInterfaces {


    private Dialog loadbar = null;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        // 去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    // 页面的跳转 content获取到的是这个类的信息
    public void SkipActivityforClass(Context context, Class<?> cla) {
        Intent intent = new Intent();
        intent.setClass(context, cla);
        startActivity(intent);
    }



    @Override
    public void initViews() {

    }

    @Override
    public void initData() {

    }
}
