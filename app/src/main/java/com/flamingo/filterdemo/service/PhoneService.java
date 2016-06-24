package com.flamingo.filterdemo.service;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.AudioManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import com.flamingo.filterdemo.R;
import com.flamingo.filterdemo.activity.HomeActivity;
import com.flamingo.filterdemo.db.MyDbHelper;


import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.android.internal.telephony.ITelephony;
import com.flamingo.filterdemo.db.SharedPreferenceDb;


/**
 * Created by Dontouch on 16/6/16.开机就进行来电的拦截的服务
 */
public class PhoneService extends Service {


    private TelephonyManager tManager;
    private MyDbHelper myDbHelper;

    int boolsms = 1;
    int bool = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolsms = intent.getIntExtra("bool", 2);
        bool = boolsms;
        this.tManager = (TelephonyManager) super.getSystemService(Context.TELEPHONY_SERVICE);
        this.tManager.listen(new PhoneStateListenerlmpl(), PhoneStateListener.LISTEN_CALL_STATE);
        return super.onStartCommand(intent, flags, startId);
    }

    private class PhoneStateListenerlmpl extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);

            AudioManager audioManager = (AudioManager) PhoneService.this.getSystemService(Context.AUDIO_SERVICE);

            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
                case TelephonyManager.CALL_STATE_RINGING: {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT); //静音处理
                    if (judge(incomingNumber)) {

                        try {
                            Method method = Class.forName("android.os.ServiceManager")
                                    .getMethod("getService", String.class);
                            // 获取远程TELEPHONY_SERVICE的IBinder对象的代理
                            IBinder binder = (IBinder) method.invoke(null,
                                    new Object[]{TELEPHONY_SERVICE});
                            // 将IBinder对象的代理转换为ITelephony对象
                            ITelephony telephony = ITelephony.Stub.asInterface(binder);
                            // 挂断电话
                            telephony.endCall();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        data(incomingNumber);
                        sendSMS(incomingNumber);
                        record(incomingNumber);
                        notification(incomingNumber);//状态栏提示

                    }

                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                }

                break;
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    //拦截指定的号码
    private boolean judge(String phonenumber) {
        SharedPreferences sp = getSharedPreferences("rule_record", MODE_PRIVATE);

        Calendar c = Calendar.getInstance();

        boolean stranbool = false;
        String numberphone = "";//手机号码
        String context = "";//短信内容

        int num = 0;  //拦截次数
        int startthour = 0;//第一次拦截时间
        int starttminute = 0;

        String answer = sp.getString("answer", "");

        int hour = c.get(Calendar.HOUR_OF_DAY);  //当前小时
        int minue = c.get(Calendar.MINUTE);      //当前分钟

        int starthour = sp.getInt("starthour", 0);//开始小时
        int startminue = sp.getInt("startminue", 0);//开始分钟
        int endhour = sp.getInt("endhour", 0);//结束时间
        int endminue = sp.getInt("endminue", 0);//结束分钟


        return false;

    }

    //陌生人电话拦截
    private void data(String phonenumber) {
        int ncount = 0;
        int max=0;


    }

    //陌生号码拦截发送短信
    private void sendSMS(String phonenumber) {

        int count = 0;
        SharedPreferences sp=this.getSharedPreferences("rule_record",MODE_PRIVATE);


        String quest=sp.getString("question", "你好！");
        count=sp.getInt("smscount", 0);

        myDbHelper=new MyDbHelper(PhoneService.this);
        myDbHelper.open();

        if(new SharedPreferenceDb(PhoneService.this).getAi()&&boolsms==2){ //是陌生模式时才启动

            Cursor cur=myDbHelper.querData("stranger");
            cur.moveToFirst();

            while(!cur.isAfterLast()){
                if(cur.getInt(2)==count&&phonenumber.equals(cur.getString(0))){ //此号码需在陌生号码表中，和此表中拦截次数向比较
                    PendingIntent pi=PendingIntent.getActivity(this,
                            0, new Intent(this,PhoneService.class),0);
                    SmsManager sms=SmsManager.getDefault();
                    sms.sendTextMessage(phonenumber, null, quest, pi, null);
                    break;
                }
                cur.moveToNext();
            }
            cur.close();
            boolsms=1;//防止多发

        }else{} ;
        myDbHelper.close();

    }

    private void record(String phonenumber){

        boolean bool=false;
        int frequency=0;

        myDbHelper=new MyDbHelper(PhoneService.this);
        myDbHelper.open();


        String datestr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",
				Locale.CHINA).format(new Date());

        Cursor cur=myDbHelper.querData("record");
        cur.moveToFirst();
        while(!cur.isAfterLast()){
            if(phonenumber.equals(cur.getString(0))){
                frequency=cur.getInt(1);
                frequency++;//统计拦截次数
                bool=true;break;//是拦截过
            }
            cur.moveToNext();
        }
        if(bool){    //此号码已拦截过
            myDbHelper.updataData(datestr,frequency, phonenumber, "record");
        }else{
            myDbHelper.insertData(phonenumber, 1, datestr, "record");
        }
        myDbHelper.close();
    }


    //状态栏

    private void notification(String phonenumber){
        Intent intent = new Intent(PhoneService.this, HomeActivity.class);
        PendingIntent pi = PendingIntent.getActivity(PhoneService.this,0,intent,0);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(PhoneService.this)
                .setAutoCancel(true)
                .setTicker("未接电话")
                .setSmallIcon(R.drawable.edit)
                .setContentText("一条新通知")
                .setContentText("终结者拦截号码为:"+phonenumber)
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setContentIntent(pi)
                .build();
        notificationManager.notify(0x123, notification);


    }





}
