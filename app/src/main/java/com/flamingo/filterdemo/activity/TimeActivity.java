package com.flamingo.filterdemo.activity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.flamingo.filterdemo.R;
import com.flamingo.filterdemo.view.TitleBar;

import java.util.Calendar;

/**
 * Created by Dontouch on 16/6/15.
 */
public class TimeActivity extends BaseActivity{

    private TitleBar titleBar;
    private RadioGroup rg;
    private LinearLayout startlinear,stoplinear;
    private TextView starttime,starttitle,stoptime,stoptitle;
    private SharedPreferences sp;

    boolean kind=true; //判断时间模式


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            if(msg.what==1){
                String startime=""+sp.getInt("starthour", 00)+":"+sp.getInt("startminue", 00);
                String endtime=""+sp.getInt("endhour", 00)+":"+sp.getInt("endminue", 00);

                if(sp.getInt("starthour", 00)<25){
                    starttime.setText(startime);
                    stoptime.setText(endtime);
                }else if(sp.getInt("starthour", 00)==25){
                    starttime.setText("00:00");
                    stoptime.setText("00:00");
                }
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.setting_time);

        initViews();
        handler.sendEmptyMessage(1);//获得已经设置时间
        onClick();//事件监听
    }


    @Override
    public void initViews() {
        super.initViews();

        titleBar = (TitleBar) findViewById(R.id.setting_time_title);
        titleBar.showLeft("时间设置", getResources().getDrawable(R.drawable.app_back),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SkipActivityforClass(TimeActivity.this,HomeActivity.class);
                        finish();
                    }
                });

        rg=(RadioGroup) findViewById(R.id.timeRgroup);
        startlinear=(LinearLayout) findViewById(R.id.startLinear);
        stoplinear=(LinearLayout) findViewById(R.id.stopLinear);

        starttime=(TextView) findViewById(R.id.time_starttime);
        starttitle=(TextView) findViewById(R.id.time_starttimee);

        stoptime=(TextView) findViewById(R.id.time_stoptime);
        stoptitle=(TextView) findViewById(R.id.time_stoptimee);

        sp=getSharedPreferences("rule_record", MODE_PRIVATE);
    }

    @Override
    public void initData() {
        super.initData();
    }

    private void onClick(){

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){ //选择时间拦截模式

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                switch(arg1){

                    case R.id.time_day:{  //全天拦截
                        starttitle.setTextColor(getResources().getColor(R.color.gray));
                        stoptitle.setTextColor(getResources().getColor(R.color.gray));
                        kind=false;

                        SharedPreferences.Editor edit=sp.edit();
                        //edit.clear();
                        edit.putInt("starthour", 25);
                        edit.commit();
                        handler.sendEmptyMessage(1);
                    }
                    break;
                    case R.id.time_break: {  //指定拦截时间
                        starttitle.setTextColor(getResources().getColor(R.color.black));
                        stoptitle.setTextColor(getResources().getColor(R.color.black));
                        kind=true;
                        startlinear.setFocusable(true);
                        stoplinear.setFocusable(true);
                    }
                    break;
                }


            }
        });

        startlinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();

                if(kind){
                    new TimePickerDialog(TimeActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                            SharedPreferences.Editor editor = sp.edit();
                            editor.putInt("starthour", hourOfDay);
                            editor.putInt("startminue", minute);
                            editor.commit();
                            handler.sendEmptyMessage(1);

                        }
                    },c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),true).show();
                }
            }
        });


        stoplinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();

                if(kind){
                    new TimePickerDialog(TimeActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                            SharedPreferences.Editor editor = sp.edit();
                            editor.putInt("endhour", hourOfDay);
                            editor.putInt("endminue", minute);
                            editor.commit();
                            handler.sendEmptyMessage(1);

                        }
                    },c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),true).show();
                }
            }
        });

    }
}
