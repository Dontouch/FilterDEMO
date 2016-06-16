package com.flamingo.filterdemo.activity;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.flamingo.filterdemo.R;
import com.flamingo.filterdemo.db.MyDbHelper;
import com.flamingo.filterdemo.db.SharedPreferenceDb;
import com.flamingo.filterdemo.util.MainUtil;
import com.flamingo.filterdemo.view.DragLayout;
import com.flamingo.filterdemo.view.SwitchButton;
import com.flamingo.filterdemo.view.TitleBar;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Dontouch on 16/6/15.
 */
public class HomeActivity extends  BaseActivity implements View.OnClickListener{

    private TitleBar titleBar;
    private DragLayout main_dl;
    private LinearLayout main_layout;

    private SwitchButton sClose;
    private SwitchButton sHei;
    private SwitchButton sBai;
    private SwitchButton sAi;
    private SwitchButton sTime;
    private SwitchButton sLocation;
    private SwitchButton sAll;


    private ListView filter_listview;
    BaseAdapter adapter;
    List<String> phones = new ArrayList<String>();
    List<String> dates = new ArrayList<String>();

    //数据库
    MyDbHelper myDbHelper;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_home);

        titleBar = (TitleBar) findViewById(R.id.main_title);
        titleBar.showLeftAndRight("来电拦截",
                getResources().getDrawable(R.drawable.menu),
                getResources().getDrawable(R.drawable.add),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        main_dl.open();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SkipActivityforClass(HomeActivity.this,
                                AddActivity.class);
                    }
                });

        initDragLayout();
        initViews();

    }

    @Override
    public void initData() {
        super.initData();
    }


    private void initDragLayout(){

        main_dl = (DragLayout) findViewById(R.id.main_dl);
        main_dl.setDragListener(new DragLayout.DragListener() {
            @Override
            public void onOpen() {

            }

            @Override
            public void onClose() {

            }

            @Override
            public void onDrag(float percent) {
                animate(percent);
            }
        });

    }

    private void animate(float percent){

        ViewGroup vg_left = main_dl.getVg_left();
        ViewGroup vg_main = main_dl.getVg_main();

        float f1 = 1 - percent * 0.3f;
        ViewHelper.setScaleX(vg_main, f1);
        ViewHelper.setScaleY(vg_main, f1);
        ViewHelper.setTranslationX(vg_left, -vg_left.getWidth() / 2.2f
                + vg_left.getWidth() / 2.2f * percent);
        ViewHelper.setScaleX(vg_left, 0.5f + 0.5f * percent);
        ViewHelper.setScaleY(vg_left, 0.5f + 0.5f * percent);
        ViewHelper.setAlpha(vg_left, percent);
        ViewHelper.setAlpha(titleBar, 1 - percent);

        int color = (Integer) MainUtil.evaluate(percent,
                Color.parseColor("#ff000000"), Color.parseColor("#00000000"));

        main_dl.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_OVER);

    }


    @Override
    public void initViews() {
        super.initViews();

        main_layout = (LinearLayout) findViewById(R.id.filter_layout_one);

        sClose = (SwitchButton) findViewById(R.id.filter_close);
        sHei = (SwitchButton) findViewById(R.id.filter_hei);
        sBai = (SwitchButton) findViewById(R.id.filter_bai);
        sAi = (SwitchButton) findViewById(R.id.filter_ai);
        sTime = (SwitchButton) findViewById(R.id.filter_time);
        sLocation = (SwitchButton) findViewById(R.id.filter_location);
        sAll = (SwitchButton) findViewById(R.id.filter_all);

        sClose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    new SharedPreferenceDb(HomeActivity.this)
                            .setOpenClose();
                }else{
                    new SharedPreferenceDb(HomeActivity.this)
                            .setCloseClose();
                }

            }
        });


        sHei.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    new SharedPreferenceDb(HomeActivity.this)
                            .setOpenHei();
                }else{
                    new SharedPreferenceDb(HomeActivity.this)
                            .setCloseHei();
                }

            }
        });


        sBai.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    new SharedPreferenceDb(HomeActivity.this)
                            .setOpenBai();
                }else{
                    new SharedPreferenceDb(HomeActivity.this)
                            .setCloseBai();
                }

            }
        });


        sAi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    new SharedPreferenceDb(HomeActivity.this)
                            .setOpenAi();
                    SkipActivityforClass(HomeActivity.this,AiActivity.class);
                }else{
                    new SharedPreferenceDb(HomeActivity.this)
                            .setCloseAi();
                }

            }
        });


        sTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    new SharedPreferenceDb(HomeActivity.this)
                            .setOpenTime();
                    SkipActivityforClass(HomeActivity.this,TimeActivity.class);
                }else{
                    new SharedPreferenceDb(HomeActivity.this)
                            .setCloseTime();
                }

            }
        });


        sLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    new SharedPreferenceDb(HomeActivity.this)
                            .setOpenLocation();

                    SkipActivityforClass(HomeActivity.this,LocationActivity.class);

                }else{
                    new SharedPreferenceDb(HomeActivity.this)
                            .setCloseLocation();
                }

            }
        });


        sAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    new SharedPreferenceDb(HomeActivity.this)
                            .setOpenAll();
                }else{
                    new SharedPreferenceDb(HomeActivity.this)
                            .setCloseAll();
                }

            }
        });

        filter_listview = (ListView) findViewById(R.id.filter_listview);

        loadData(); //加载拦截记录



    }

    private void loadData(){

        phones.clear(); //清容器中的数据
        dates.clear();

        myDbHelper=new MyDbHelper(HomeActivity.this);

        myDbHelper.open();         // 打开数据库
        Cursor cursor=myDbHelper.querData("record");

        while(cursor.moveToNext()){
            phones.add(cursor.getString(0));
            dates.add(cursor.getString(3)+"   "+cursor.getString(2));
        }
        cursor.close();
        myDbHelper.close();


        adapter = new BaseAdapter() {

            int count=0;

            @Override
            public int getCount() {
                return phones.size();
            }

            @Override
            public Object getItem(int position) {
                return phones.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                count = position;
                LayoutInflater inflate=LayoutInflater.from(HomeActivity.this);
                View view=inflate.inflate(R.layout.item_record, null);

                TextView phonenumber=(TextView) view.findViewById(R.id.item_record_number);
                TextView date=(TextView) view.findViewById(R.id.item_record_date);
                ImageButton del=(ImageButton) view.findViewById(R.id.item_record_del);

                phonenumber.setText(phones.get(position));
                date.setText(dates.get(position));

                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDbHelper=new MyDbHelper(HomeActivity.this);
                        myDbHelper.open();
                        myDbHelper.deleteData(phones.get(count), "record");
                        myDbHelper.close();

                        loadData();  //刷新页面

                    }
                });

                return view;
            }
        };

        filter_listview.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }



}
