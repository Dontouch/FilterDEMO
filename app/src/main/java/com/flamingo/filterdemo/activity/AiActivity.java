package com.flamingo.filterdemo.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flamingo.filterdemo.view.TitleBar;

/**
 * Created by Dontouch on 16/6/15.
 */
public class AiActivity extends BaseActivity {

    private TitleBar titleBar;

    TextView smstall,timestall,smstext,phonetext,timetext,quit;
    LinearLayout smslinear,phonelinear,timelinear;
    String []smstime=new String[10];
    String []phonetime=new String[10];
    int n=0;

    

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
    }


    @Override
    public void initViews() {
        super.initViews();
    }

    @Override
    public void initData() {
        super.initData();
    }
}
