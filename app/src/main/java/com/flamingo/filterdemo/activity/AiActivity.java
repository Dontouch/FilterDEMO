package com.flamingo.filterdemo.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.flamingo.filterdemo.R;
import com.flamingo.filterdemo.view.TitleBar;

import org.w3c.dom.Text;

/**
 * Created by Dontouch on 16/6/15.
 */
public class AiActivity extends BaseActivity {

    private TitleBar titleBar;

    private TextView smstall,timestall,smstext,phonetext,timetext;
    private LinearLayout smslinear,phonelinear,timelinear;

    private String []smstime=new String[10];
    private String []phonetime=new String[10];

    private int n=0;

    

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.setting_ai);

        initViews();
        getText();
        initData();
        onClick();

    }


    @Override
    public void initViews() {
        super.initViews();

        titleBar = (TitleBar) findViewById(R.id.setting_ai_title);

        titleBar.showLeft("智能陌生人拦截", getResources().getDrawable(R.drawable.app_back),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SkipActivityforClass(AiActivity.this,HomeActivity.class);
                    }
                });

        smstall =(TextView) findViewById(R.id.strange_sms);
        timestall =(TextView) findViewById(R.id.strange_time);

        smstext =(TextView) findViewById(R.id.strange_smss);
        phonetext =(TextView) findViewById(R.id.strange_phone);
        timetext=(TextView) findViewById(R.id.strange_timespane);

        smslinear =(LinearLayout) findViewById(R.id.strange_smslinear);
        phonelinear =(LinearLayout) findViewById(R.id.strange_phonelinear);
        timelinear =(LinearLayout) findViewById(R.id.strange_timelinear);




    }

    @Override
    public void initData() {
        super.initData();

        int i;

        for(i=0;i<smstime.length;i++){
            smstime[i]="第"+(i+1)+" 次后发送短信";
        }
        for(i=0;i<phonetime.length;i++){
            phonetime[i]="第"+(i+1)+"次后可以接通";
        }
    }

    private void getText(){

        int n;

        SharedPreferences sp = getSharedPreferences("rule_record", MODE_PRIVATE);

        n=sp.getInt("smscount", 0);
        String str=""+n+"次";
        smstext.setText(str);

        n=sp.getInt("phonecount", 0);
        str=str.replace(str, ""+n+"次");
        phonetext.setText(str);

        n=sp.getInt("phonebreak", 0);
        str=str.replace(str, ""+n+"分钟");
        timetext.setText(str);
    }

    private void  onClick(){

        smstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Stranger_smsmanager(AiActivity.this).Dialog(); //一对话框的形式出现
            }
        });

        timestall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkipActivityforClass(AiActivity.this,TimeActivity.class);
            }
        });

        smslinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int z = 1;
                AlertDialog.Builder builer = new AlertDialog.Builder(AiActivity.this);

                builer.setTitle("请选择")
                        .setIcon(getResources().getDrawable(R.drawable.edit))
                        .setSingleChoiceItems(smstime, z, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                n=which+1;
                            }
                        });

                builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences sp=getSharedPreferences("rule_record", MODE_PRIVATE);
                        SharedPreferences.Editor edit=sp.edit();
                        edit.putInt("smscount", n);
                        edit.commit();
                        getText();;

                    }
                });

                builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builer.create().show();
            }
        });

        phonelinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int z = 1;
                AlertDialog.Builder builer = new AlertDialog.Builder(AiActivity.this);

                builer.setTitle("请选择")
                        .setIcon(getResources().getDrawable(R.drawable.edit))
                        .setSingleChoiceItems(smstime, z, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                n=which+1;
                            }
                        });

                builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences sp=getSharedPreferences("rule_record", MODE_PRIVATE);
                        SharedPreferences.Editor edit=sp.edit();
                        edit.putInt("phonecount", n);
                        edit.commit();
                        getText();;

                    }
                });

                builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builer.create().show();
            }
        });

        timelinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog=new TimePickerDialog(AiActivity.this,new TimePickerDialog.OnTimeSetListener() {


                    @Override
                    public void onTimeSet(TimePicker arg0, int arg1, int arg2) {
                        n=arg1*60+arg2;
                        SharedPreferences sp=getSharedPreferences("rule_record", MODE_PRIVATE);
                        SharedPreferences.Editor edit=sp.edit();
                        edit.putInt("phonebreak", n);
                        edit.commit();
                        getText();
                    }
                },0,0,true);
                dialog.show();

            }
        });



    }


}
