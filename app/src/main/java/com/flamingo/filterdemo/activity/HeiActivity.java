package com.flamingo.filterdemo.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import android.widget.EditText;

import android.widget.ListView;
import android.widget.TextView;


import com.flamingo.filterdemo.Bean.HeiBean;
import com.flamingo.filterdemo.R;
import com.flamingo.filterdemo.adapter.HeiListAdapter;
import com.flamingo.filterdemo.db.MyDbHelper;
import com.flamingo.filterdemo.view.RefreshableView;
import com.flamingo.filterdemo.view.TitleBar;


import org.apache.http.impl.conn.tsccm.WaitingThreadAborter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dontouch on 16/6/19.
 */
public class HeiActivity extends BaseActivity {


    private TitleBar titleBar;
    private RefreshableView refreshableView;
    private ListView listView;

    private MyDbHelper myDbHelper;
    private List<HeiBean> list = new ArrayList<HeiBean>();
    private HeiBean heiBean;

    private HeiListAdapter adapter;


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
                        SkipActivityforClass(HeiActivity.this,
                                choseaddHei.class);
                    }
                });

        refreshableView = (RefreshableView) findViewById(R.id.hei_refreshable_view);
        listView =(ListView) findViewById(R.id.hei_listview);

        getData();
        initViews();
    }

    @Override
    public void initViews() {
        super.initViews();



         adapter= new HeiListAdapter(this,list);
        //dapter listview.setAdapter(list)

        synchronized(HeiActivity.this){
            listView.setAdapter(adapter);
        }



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final String number = list.get(position).getNumber();

                //删除黑名单 更新黑名单的数据
                final AlertDialog.Builder dialog = new AlertDialog.Builder(HeiActivity.this);

                dialog.setTitle("请选择对操作");
                dialog.setItems(new String[]{"修改备注", "删除改黑名单"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which){
                            case 0:
                                AlertDialog.Builder builder=new AlertDialog.Builder(HeiActivity.this);

                                LayoutInflater factory=LayoutInflater.from(HeiActivity.this);

                                final View view=factory.inflate(R.layout.comment, null);
                                view.setPadding(36,12,12,12);

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

                                        fresh();


                                        dialog.dismiss();

                                    }
                                });

                                builder.create().show();
                                break;

                            case 1:
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

                                                        fresh();

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

                                break;

                        }

                    }
                });



                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });


        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {

                // 下拉刷新的监听
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshableView.finishRefreshing();

            }
        },0);

    }

    private  void  getData(){

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


    }



    @Override
    public void initData() {
        super.initData();
    }

    private void fresh(){
        getData();
        initViews();

    }


    @Override
    protected void onResume() {
        super.onResume();
        fresh();
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
                       fresh();
                    }

                }.start();
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }




}
