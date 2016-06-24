package com.flamingo.filterdemo.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.flamingo.filterdemo.Bean.BaiBean;
import com.flamingo.filterdemo.R;
import com.flamingo.filterdemo.adapter.BaiListAdapter;
import com.flamingo.filterdemo.db.MyDbHelper;
import com.flamingo.filterdemo.view.RefreshableView;
import com.flamingo.filterdemo.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dontouch on 16/6/19.
 */
public class BaiActivity extends BaseActivity {


    private TitleBar titleBar;
    private RefreshableView refreshableView;
    private ListView listView;

    private MyDbHelper myDbHelper;
    private List<BaiBean> list = new ArrayList<BaiBean>();
    private BaiBean baiBean;

    private BaiListAdapter adapter;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.add_bai);

        titleBar = (TitleBar) findViewById(R.id.add_bai_title);
        titleBar.showLeftAndRight("白名单管理",
                getResources().getDrawable(R.drawable.app_back),
                getResources().getDrawable(R.drawable.add),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SkipActivityforClass(BaiActivity.this,
                                AddActivity.class);
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SkipActivityforClass(BaiActivity.this,
                                choseaddBai.class);
                    }
                });

        refreshableView = (RefreshableView) findViewById(R.id.bai_refreshable_view);
        listView =(ListView) findViewById(R.id.bai_listview);

        getData();
        initViews();
    }

    @Override
    public void initViews() {
        super.initViews();



         adapter= new BaiListAdapter(this,list);
        //dapter listview.setAdapter(list)

        synchronized(BaiActivity.this){
            listView.setAdapter(adapter);
        }



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final String number = list.get(position).getNumber();

                //删除黑名单 更新黑名单的数据
                final AlertDialog.Builder dialog = new AlertDialog.Builder(BaiActivity.this);

                dialog.setTitle("请选择对操作");
                dialog.setItems(new String[]{"修改备注", "删除改白名单"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which){
                            case 0:
                                AlertDialog.Builder builder=new AlertDialog.Builder(BaiActivity.this);

                                LayoutInflater factory=LayoutInflater.from(BaiActivity.this);

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

                                        myDbHelper=new MyDbHelper(BaiActivity.this);
                                        myDbHelper.open();
                                        myDbHelper.updataData(number, str, "white");
                                        myDbHelper.close();

                                        fresh();


                                        dialog.dismiss();

                                    }
                                });

                                builder.create().show();
                                break;

                            case 1:
                                AlertDialog delDialog = new AlertDialog.Builder(
                                        BaiActivity.this)
                                        .setTitle("确认删除")
                                        .setMessage("确认删除?")
                                        .setPositiveButton("确定",
                                                new DialogInterface.OnClickListener() {

                                                    @Override
                                                    public void onClick(
                                                            DialogInterface dialog,
                                                            int which) {

                                                        myDbHelper=new MyDbHelper(BaiActivity.this);
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

        myDbHelper = new MyDbHelper(BaiActivity.this);
        myDbHelper.open();
        Cursor cursor = myDbHelper.querData("white");

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            baiBean = new BaiBean();
            baiBean.setName(cursor.getString(1));
            baiBean.setNumber(cursor.getString(0));
            list.add(baiBean);
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


}
