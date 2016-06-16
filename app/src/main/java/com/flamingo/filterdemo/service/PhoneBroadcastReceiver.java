package com.flamingo.filterdemo.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Dontouch on 16/6/16.
 */
public class PhoneBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int i=2;
        //Toast.makeText(context, "广播开启", Toast.LENGTH_SHORT).show();
        if(intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)){ //去电操作

        }else{
            Intent pit=new Intent(context,PhoneService.class);
            pit.putExtra("bool", i);
            //pit.putExtra("mIncomingNumber", mIncomingNumber);
            context.startService(pit);
        }

    }
}
