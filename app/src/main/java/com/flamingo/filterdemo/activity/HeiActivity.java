package com.flamingo.filterdemo.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.flamingo.filterdemo.Bean.HeiBean;
import com.flamingo.filterdemo.R;
import com.flamingo.filterdemo.adapter.HeiListAdapter;
import com.flamingo.filterdemo.db.MyDbHelper;
import com.flamingo.filterdemo.view.TitleBar;
import com.flamingo.filterdemo.view.XListView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dontouch on 16/6/19.
 */
public class HeiActivity extends BaseActivity implements XListView.IXListViewListener {

    private TitleBar titleBar;
    private XListView hei_listview;

    private MyDbHelper myDbHelper;

    private List<HeiBean> list = new ArrayList<HeiBean>();
    private HeiBean heiBean;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.add_hei);

        titleBar = (TitleBar) findViewById(R.id.add_hei_title);
        titleBar.showLeftAndRight("黑名单管理",
                getResources().getDrawable(R.drawable.app_back),
                getResources().getDrawable(R.drawable.add),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SkipActivityforClass(HeiActivity.this,
                                AddActivity.class);
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog dialog = new AlertDialog.Builder(HeiActivity.this).create();

                        dialog.setTitle("请选择添加方式");
                        dialog.setButton("通过通讯录添加", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent();
                                intent.putExtra("key", "0");
                                intent.setClass(HeiActivity.this, ContactListActivity.class);
                                startActivity(intent);

                            }
                        });

                        dialog.setButton("通过通讯记录添加", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent();
                                intent.putExtra("key", "0");
                                intent.setClass(HeiActivity.this, ContactRecordListActivity.class);
                                startActivity(intent);
                            }
                        });

                        dialog.setButton("手动输入", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                AlertDialog.Builder builder=new AlertDialog.Builder(HeiActivity.this);
                                LayoutInflater factory = LayoutInflater.from(HeiActivity.this);

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

                                        myDbHelper  =new MyDbHelper(HeiActivity.this);
                                        myDbHelper.open();
                                        EditText name=(EditText) view.findViewById(R.id.edit_whilename);
                                        EditText number=(EditText) view.findViewById(R.id.edit_whilenumber);

                                        final String strname=name.getText().toString();
                                        final String strnumber=number.getText().toString();

                                        if(strname.equals("")||strnumber.equals("")){

                                            Toast toast=Toast.makeText(HeiActivity.this,"姓名与号码不能为空",Toast.LENGTH_LONG);
                                            toast.setGravity(Gravity.CENTER, 0, 0);
                                            toast.show();
                                            myDbHelper.close();
                                            dialog.dismiss();
                                        }else {
                                            if(judge(strnumber)){
                                                Toast toast=Toast.makeText(HeiActivity.this,"该号码已存在黑名单中",Toast.LENGTH_LONG);
                                                toast.setGravity(Gravity.CENTER, 0, 0);
                                                toast.show();
                                                dialog.dismiss();
                                            }else{
                                                myDbHelper.insertData(strnumber, strname, "black");
                                                myDbHelper.close();

                                                loadData();
                                                Toast toast=Toast.makeText(HeiActivity.this,"添加成功",Toast.LENGTH_LONG);
                                                toast.setGravity(Gravity.CENTER, 0, 0);
                                                toast.show();
                                                dialog.dismiss();

                                            }
                                        }

                                    }
                                });

                                builder.create().show();


                            }
                        });

                        dialog.setButton2("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }
                });


        initViews();
    }

    @Override
    public void initViews() {
        super.initViews();


        hei_listview =(XListView) findViewById(R.id.hei_listview);
        hei_listview.setPullLoadEnable(true);
        loadData();// 刷新

        hei_listview.setXListViewListener(this);

        hei_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final String number = list.get(position).getNumber();

                //删除黑名单 更新黑名单的数据
                final AlertDialog dialog = new AlertDialog.Builder(HeiActivity.this).create();

                dialog.setTitle("请选择对操作");
                dialog.setButton("修改备注", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                        AlertDialog.Builder builder=new AlertDialog.Builder(HeiActivity.this);

                        LayoutInflater factory=LayoutInflater.from(HeiActivity.this);

                        final View view=factory.inflate(R.layout.comment, null);

                        builder.setView(view);
                        TextView tv=(TextView)view.findViewById(R.id.coment_text);
                        tv.setText(number);

                        builder.setIcon(R.drawable.edit);
                        builder.setTitle("输出新的备注");


                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                EditText et=(EditText) view.findViewById(R.id.comment_edit);
                                String str=et.getText().toString();

                                myDbHelper=new MyDbHelper(HeiActivity.this);
                                myDbHelper.open();
                                myDbHelper.updataData(number, str, "black");
                                myDbHelper.close();

                                loadData();

                                dialog.dismiss();

                            }
                        });

                        builder.create().show();



                    }
                });

                dialog.setButton("删除改黑名单", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        AlertDialog delDialog = new AlertDialog.Builder(
                                HeiActivity.this)
                                .setTitle("确认删除")
                                .setMessage("确认删除?")
                                .setPositiveButton("确定",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {

                                                myDbHelper=new MyDbHelper(HeiActivity.this);
                                                myDbHelper.open();
                                                myDbHelper.deleteData(number, "black");
                                                myDbHelper.close();

                                                loadData();

                                                dialog.dismiss();

                                            }
                                        })
                                .setNegativeButton("取消",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                                dialog.dismiss();
                                            }
                                        }).create();
                        delDialog.show();




                    }
                });


                dialog.setButton2("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });


    }

    private void loadData(){

        //加载黑名单的数据 从数据库中加载

        list.clear();

        myDbHelper = new MyDbHelper(HeiActivity.this);
        myDbHelper.open();
        Cursor cursor = myDbHelper.querData("black");

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            heiBean = new HeiBean();
            heiBean.setName(cursor.getString(1));
            heiBean.setNumber(cursor.getString(0));
            heiBean.setFrequency(cursor.getString(2));
            list.add(heiBean);
            cursor.moveToNext();
        }

        myDbHelper.close();

        HeiListAdapter adapter = new HeiListAdapter(this,list);
        //dapter listview.setAdapter(list)
        hei_listview.setAdapter(adapter);

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

    @Override
    public void initData() {
        super.initData();
    }


    //添加菜单事件
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.heimenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int item_id = item.getItemId();
        switch (item_id) {
            case R.id.hbdrop: // 退出程序
                HeiActivity.this.finish();
                break;
            case R.id.clearhei: // 清空黑名单表
                myDbHelper = new MyDbHelper(HeiActivity.this);
                myDbHelper.open();
                myDbHelper.clearData("black");
                myDbHelper.close();
                break;

            case R.id.updatebei: // 更新通讯录
                new Thread() {

                    @Override
                    public void run() {
                        loadData();
                    }

                }.start();
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }


    @Override
    public void onRefresh() {
        loadData();
    }

    @Override
    public void onLoadMore() {

    }


}
