package com.flamingo.filterdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.TelephonyManager;

import com.flamingo.filterdemo.db.MyDbHelper;

/**
 * Created by Dontouch on 16/6/16.
 */
public class PhoneService extends Service {

   //各个拦截的操作

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
