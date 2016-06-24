package com.flamingo.filterdemo.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Dontouch on 16/6/16. 本广播完成来电监听
 */
public class PhoneBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int i=2;

        if(intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)){ //去电操作

        }else{
            Intent intent1=new Intent(context,PhoneService.class);
            intent1.putExtra("bool", i);
            context.startService(intent1);
        }

    }
}
