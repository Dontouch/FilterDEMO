package com.flamingo.filterdemo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.flamingo.filterdemo.R;
import com.flamingo.filterdemo.view.TitleBar;

/**
 * Created by Dontouch on 16/6/15.
 */
public class AddActivity extends BaseActivity {


    private TitleBar titleBar;
    private ImageView add_hei;
    private ImageView add_bai;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.add_heibai);

        titleBar = (TitleBar) findViewById(R.id.add_heibai_title);
        titleBar.showLeft("来电拦截",
                getResources().getDrawable(R.drawable.app_back),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SkipActivityforClass(AddActivity.this,
                                HomeActivity.class);
                    }
                });

        initViews();
    }


    @Override
    public void initViews() {
        super.initViews();

        add_hei =(ImageView) findViewById(R.id.add_hei);
        add_bai =(ImageView) findViewById(R.id.add_bai);

        add_hei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkipActivityforClass(AddActivity.this,
                        HeiActivity.class);
            }
        });

        add_bai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkipActivityforClass(AddActivity.this,
                        BaiActivity.class);
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
    }
}
