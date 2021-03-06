package com.flamingo.filterdemo.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.flamingo.filterdemo.R;
import com.flamingo.filterdemo.db.MyDbHelper;
import com.flamingo.filterdemo.view.TitleBar;

/**
 * Created by Dontouch on 16/6/22.
 */
public class choseaddHei extends BaseActivity{

    private TitleBar titleBar;
    private ImageView byList;
    private ImageView byRecord;
    private ImageView byInput;

    private MyDbHelper myDbHelper;

    @Override
    protected void onCreate(Bundle arg0) {

        super.onCreate(arg0);
        setContentView(R.layout.choose_addhei);

        titleBar = (TitleBar) findViewById(R.id.choose_addhei_title);
        titleBar.showLeft("选择添加方式",
                getResources().getDrawable(R.drawable.app_back),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SkipActivityforClass(choseaddHei.this,
                                HeiActivity.class);
                    }
                });

        initViews();
    }


    @Override
    public void initViews() {
        super.initViews();

        byList =(ImageView) findViewById(R.id.choose_addhei_contactlist);
        byRecord =(ImageView) findViewById(R.id.choose_addhei_contactrecord);
        byInput =(ImageView) findViewById(R.id.choose_addhei_input);

        byList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent();
                intent.putExtra("key","0");
                intent.setClass(choseaddHei.this,ContactListActivity.class);
                startActivity(intent);

            }
        });

        byRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent();
                intent.putExtra("key","0");
                intent.setClass(choseaddHei.this,ContactRecordListActivity.class);
                startActivity(intent);

            }
        });

        byInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(choseaddHei.this);
                LayoutInflater factory = LayoutInflater.from(choseaddHei.this);

                final View view = factory.inflate(R.layout.dialogpeoadd,null);

                builder.setView(view);
                builder.setTitle("添加号码到黑名单");
                builder.setIcon(R.drawable.edit);

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        myDbHelper  =new MyDbHelper(choseaddHei.this);
                        myDbHelper.open();
                        EditText name=(EditText) view.findViewById(R.id.edit_whilename);
                        EditText number=(EditText) view.findViewById(R.id.edit_whilenumber);

                        final String strname=name.getText().toString();
                        final String strnumber=number.getText().toString();

                        if(strname.equals("")||strnumber.equals("")){

                            Toast toast=Toast.makeText(choseaddHei.this,"姓名与号码不能为空",Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            myDbHelper.close();
                            dialog.dismiss();
                        }else {
                            if(judge(strnumber)){
                                Toast toast=Toast.makeText(choseaddHei.this,"该号码已存在黑名单中",Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                dialog.dismiss();
                            }else{
                                myDbHelper.insertData(strnumber, strname, "black");
                                myDbHelper.close();
                                Toast toast=Toast.makeText(choseaddHei.this,"添加成功",Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                                choseaddHei.this.finish();
                                dialog.dismiss();

                            }
                        }

                    }
                });

                builder.create().show();

            }
        });
    }

    @Override
    public void initData() {
        super.initData();
    }


        private boolean judge(String phone){  //判断是不在黑名单中

        Cursor cur=myDbHelper.querData("black");
        cur.moveToFirst();
        while(!cur.isAfterLast()){
            if(phone.equals(cur.getString(0))){
                myDbHelper.close();
                return true;
            }
            cur.moveToNext();
        }
        return false;
    }
}
