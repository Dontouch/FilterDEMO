package com.flamingo.filterdemo.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dontouch on 16/6/16.
 */
public class MyDbHelper {

    private Context mContext = null;


    private static final String DB_NAME="list.db";

    private static final String TABLE_RECORD=  //拦截记录
            "create table record(number text primary key, frquency int, time text,day text)";


    //数据库版本
    private static final int DB_VERSION=1;
    private SQLiteDatabase sqlitedatabase;
    private MySQLiteOpenHelper mysqliteopen;


    public MyDbHelper(Context context){
        this.mContext=context;
    }




    public class MySQLiteOpenHelper extends SQLiteOpenHelper {

        public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLE_RECORD);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists record");    //删除表
            onCreate(db);
        }
    }

    public void open(){         //打开数据库
        mysqliteopen=new MySQLiteOpenHelper(mContext,DB_NAME,null,DB_VERSION);
        sqlitedatabase=mysqliteopen.getWritableDatabase();
    }

    public void close(){       //删除数据库
        mysqliteopen.close();
    }


    public Cursor querData(String table) {
        Cursor cur=null;
        cur=sqlitedatabase.rawQuery("select * from "+table, null);
        return cur;
    }


    public void deleteData(String number, String table) {

        String str="delete from "+table+" where number=?";
        sqlitedatabase.execSQL(str,new Object[]{number});

    }






}
