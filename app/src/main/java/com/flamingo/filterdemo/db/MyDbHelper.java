package com.flamingo.filterdemo.db;

import android.content.ContentValues;
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

    private static final String TABLE_BLACK=    //黑名单
            "create table black(number text primary key, name text,frequency text)";


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
            db.execSQL(TABLE_BLACK);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists record");    //删除表
            db.execSQL("drop table if exists black");
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

    //删除数据库  指定表
    public void deleteData(String number, String table) {

        String str="delete from "+table+" where number=?";
        sqlitedatabase.execSQL(str,new Object[]{number});

    }

    //清空数据库指定表
    public void clearData(String table){
        String str="delete from "+table;
        sqlitedatabase.execSQL(str);
    }

    //更新名单信息
    public boolean updataData(String number,String name,String table){
        String str="update "+table+" set name=? where number=?";
        sqlitedatabase.execSQL(str,new Object[]{name,number});
        return true;
    }

    //插入数据01 保存信息
    public long insertData(String number,String name,String table){
        ContentValues v=new ContentValues();
        v.put("number", number);
        v.put("name", name);
        return sqlitedatabase.insert(table, null, v);
    }






}
