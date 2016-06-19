package com.flamingo.filterdemo.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.flamingo.filterdemo.R;
import com.flamingo.filterdemo.view.TitleBar;

/**
 * Created by Dontouch on 16/6/15.
 */
public class LocationActivity extends BaseActivity{

    private TitleBar titleBar;

    Spinner spinner;
    EditText rigon;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        initViews();
        initData();
        OnClick();
    }


    @Override
    public void initViews() {
        super.initViews();

        titleBar = (TitleBar) findViewById(R.id.setting_location_title);
        titleBar.showLeft("位置设置", getResources().getDrawable(R.drawable.app_back),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SkipActivityforClass(LocationActivity.this,HomeActivity.class);
                    }
                });


        spinner=(Spinner) findViewById(R.id.setting_spinner);
        rigon=(EditText) findViewById(R.id.sett_local);

    }

    @Override
    public void initData() {
        super.initData();

        SharedPreferences sp=getSharedPreferences("rule_record", MODE_PRIVATE);

        spinner.setSelection(sp.getInt("spinner", 0));
        String address=sp.getString("provincename", "请输入地区")+sp.getString("cityname", "");
        rigon.setText(address);
    }

    private void OnClick(){

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){//选择地区拦截模式如只拦截摸个地区或只接听
            //莫个地区

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                SharedPreferences sp=getSharedPreferences("rule_record", MODE_PRIVATE);
                SharedPreferences.Editor edit=sp.edit();
                edit.putInt("spinner", arg2);
                edit.commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}
        });

        rigon.setOnClickListener(new View.OnClickListener() { //拦截地区选择如可选择江苏--苏州

            @Override
            public void onClick(View arg0) {
                SkipActivityforClass(LocationActivity.this,ProvinceActivity.class);
            }
        });




    }
}
