package com.flamingo.filterdemo.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.flamingo.filterdemo.constants.DbConstants;

/**
 * Created by Dontouch on 16/6/15.
 */
public class SharedPreferenceDb {

    private Context context;

    public SharedPreferenceDb(Context context)
    {
        this.context = context;
    }


    public void setCloseClose() {
        SharedPreferences sp = context.getSharedPreferences(DbConstants.DB_ClOSE, Context.MODE_PRIVATE);
        sp.edit().putBoolean(DbConstants.KEY_CLOSE, false).commit();
    }


    public void setOpenClose()
    {
        SharedPreferences sp = context.getSharedPreferences(DbConstants.DB_ClOSE, Context.MODE_PRIVATE);
        sp.edit().putBoolean(DbConstants.KEY_CLOSE, true).commit();
    }


    public void setOpenHei() {

        SharedPreferences sp = context.getSharedPreferences(DbConstants.DB_HEI, Context.MODE_PRIVATE);
        sp.edit().putBoolean(DbConstants.KEY_HEI, true).commit();
    }

    public void setCloseHei() {

        SharedPreferences sp = context.getSharedPreferences(DbConstants.DB_HEI, Context.MODE_PRIVATE);
        sp.edit().putBoolean(DbConstants.KEY_HEI, false).commit();
    }


    public void setOpenBai() {

        SharedPreferences sp = context.getSharedPreferences(DbConstants.DB_BAI, Context.MODE_PRIVATE);
        sp.edit().putBoolean(DbConstants.KEY_BAI, true).commit();
    }

    public void setCloseBai() {
        SharedPreferences sp = context.getSharedPreferences(DbConstants.DB_BAI, Context.MODE_PRIVATE);
        sp.edit().putBoolean(DbConstants.KEY_BAI, false).commit();
    }

    public void setOpenAi() {
        SharedPreferences sp = context.getSharedPreferences(DbConstants.DB_AI, Context.MODE_PRIVATE);
        sp.edit().putBoolean(DbConstants.KEY_AI, true).commit();
    }

    public void setCloseAi() {
        SharedPreferences sp = context.getSharedPreferences(DbConstants.DB_AI, Context.MODE_PRIVATE);
        sp.edit().putBoolean(DbConstants.KEY_AI, false).commit();
    }

    public void setOpenTime() {
        SharedPreferences sp = context.getSharedPreferences(DbConstants.DB_TIME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(DbConstants.KEY_TIME, true).commit();
    }

    public void setCloseTime() {
        SharedPreferences sp = context.getSharedPreferences(DbConstants.DB_TIME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(DbConstants.KEY_TIME, false).commit();
    }

    public void setOpenLocation() {
        SharedPreferences sp = context.getSharedPreferences(DbConstants.DB_LOCATION, Context.MODE_PRIVATE);
        sp.edit().putBoolean(DbConstants.KEY_LOCATIOIN, true).commit();
    }

    public void setCloseLocation() {
        SharedPreferences sp = context.getSharedPreferences(DbConstants.DB_LOCATION, Context.MODE_PRIVATE);
        sp.edit().putBoolean(DbConstants.KEY_LOCATIOIN, false).commit();
    }

    public void setOpenAll() {
        SharedPreferences sp = context.getSharedPreferences(DbConstants.DB_ALL, Context.MODE_PRIVATE);
        sp.edit().putBoolean(DbConstants.KEY_ALL, true).commit();
    }

    public void setCloseAll() {
        SharedPreferences sp = context.getSharedPreferences(DbConstants.DB_ALL, Context.MODE_PRIVATE);
        sp.edit().putBoolean(DbConstants.KEY_ALL, false).commit();
    }

    public boolean getClose() {
        SharedPreferences sp = context.getSharedPreferences(DbConstants.DB_ClOSE, Context.MODE_PRIVATE);
        return sp.getBoolean(DbConstants.KEY_CLOSE, false);
    }

    public boolean getHei() {

        SharedPreferences sp = context.getSharedPreferences(DbConstants.DB_HEI, Context.MODE_PRIVATE);
        return sp.getBoolean(DbConstants.KEY_HEI, false);
    }

    public boolean getBai() {
        SharedPreferences sp = context.getSharedPreferences(DbConstants.DB_BAI, Context.MODE_PRIVATE);
        return sp.getBoolean(DbConstants.KEY_BAI, false);
    }

    public boolean getAi() {
        SharedPreferences sp = context.getSharedPreferences(DbConstants.DB_AI, Context.MODE_PRIVATE);
        return sp.getBoolean(DbConstants.KEY_AI, false);
    }

    public boolean getTime() {
        SharedPreferences sp = context.getSharedPreferences(DbConstants.DB_TIME, Context.MODE_PRIVATE);
        return sp.getBoolean(DbConstants.KEY_TIME, false);
    }

    public boolean getLocation() {
        SharedPreferences sp = context.getSharedPreferences(DbConstants.DB_LOCATION, Context.MODE_PRIVATE);
        return sp.getBoolean(DbConstants.KEY_LOCATIOIN, false);
    }

    public boolean getAll() {
        SharedPreferences sp = context.getSharedPreferences(DbConstants.DB_ALL, Context.MODE_PRIVATE);
        return sp.getBoolean(DbConstants.KEY_ALL, false);
    }
}
