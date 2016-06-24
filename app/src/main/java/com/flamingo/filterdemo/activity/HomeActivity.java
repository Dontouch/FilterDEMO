package com.flamingo.filterdemo.activity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.flamingo.filterdemo.Bean.BaiBean;
import com.flamingo.filterdemo.R;
import com.flamingo.filterdemo.core.AbsHandler;
import com.flamingo.filterdemo.core.AbsTrigger;
import com.flamingo.filterdemo.core.BlockerBuilder;
import com.flamingo.filterdemo.core.IBlocker;
import com.flamingo.filterdemo.core.IFilter;
import com.flamingo.filterdemo.db.MyDbHelper;
import com.flamingo.filterdemo.db.SharedPreferenceDb;
import com.flamingo.filterdemo.impl.InCallingHandler;
import com.flamingo.filterdemo.impl.InCallingTrigger;
import com.flamingo.filterdemo.impl.NumeralFilter;
import com.flamingo.filterdemo.impl.PrefixFilter;
import com.flamingo.filterdemo.util.MainUtil;
import com.flamingo.filterdemo.view.DragLayout;
import com.flamingo.filterdemo.view.SwitchButton;
import com.flamingo.filterdemo.view.TitleBar;
import com.nineoldandroids.view.ViewHelper;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
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

    private TextView setting_ai;
    private TextView setting_time;
    private TextView setting_location;


    private ListView filter_listview;
    BaseAdapter adapter;
    List<String> phones = new ArrayList<String>();
    List<String> dates = new ArrayList<String>();

    //数据库
    MyDbHelper myDbHelper;


    private IBlocker mBlocker;
    private AbsTrigger mTrigger = new InCallingTrigger();
    private AbsHandler mHandler = new InCallingHandler();

    private Handler mUIHandler;



    @SuppressLint("HandlerLeak")
    Handler hand=new Handler(){  //刷新页面

        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                update();//本方法重复刷新页面
            }
            super.handleMessage(msg);
        }

    };

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_home);


        titleBar = (TitleBar) findViewById(R.id.main_title);

        sClose = (SwitchButton) findViewById(R.id.filter_close);
        sHei = (SwitchButton) findViewById(R.id.filter_hei);
        sBai = (SwitchButton) findViewById(R.id.filter_bai);
        sAi = (SwitchButton) findViewById(R.id.filter_ai);
        sTime = (SwitchButton) findViewById(R.id.filter_time);
        sLocation = (SwitchButton) findViewById(R.id.filter_location);
        sAll = (SwitchButton) findViewById(R.id.filter_all);

        main_layout = (LinearLayout) findViewById(R.id.filter_layout_one);
        main_dl = (DragLayout) findViewById(R.id.main_dl);
        filter_listview = (ListView) findViewById(R.id.filter_listview);

        setting_ai=(TextView) findViewById(R.id.setting_ai);
        setting_time=(TextView) findViewById(R.id.setting_time);
        setting_location=(TextView) findViewById(R.id.setting_location);

        setting_ai.setOnClickListener(this);
        setting_time.setOnClickListener(this);
        setting_location.setOnClickListener(this);



        initViews();

        initDragLayout();

        update();

    }


    @Override
    public void initData() {
        super.initData();
    }


    private void initDragLayout(){


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


    private void update(){

        // 通过sharepreferenceDb进行设置
        if (new SharedPreferenceDb(HomeActivity.this).getClose() == true) {
            sClose.setChecked(true);
        } else {
            sClose.setChecked(false);
        }

        sClose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    new SharedPreferenceDb(HomeActivity.this)
                            .setOpenClose();

                    hand.sendEmptyMessage(1);
                }else{
                    new SharedPreferenceDb(HomeActivity.this)
                            .setCloseClose();
                    hand.sendEmptyMessage(1);
                }

            }
        });

        if (new SharedPreferenceDb(HomeActivity.this).getHei() == true) {
            sHei.setChecked(true);
        } else {
            sHei.setChecked(false);
        }

        sHei.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    new SharedPreferenceDb(HomeActivity.this)
                            .setOpenHei();
                    hand.sendEmptyMessage(1);
                }else{
                    new SharedPreferenceDb(HomeActivity.this)
                            .setCloseHei();
                    hand.sendEmptyMessage(1);
                }

            }
        });

        if (new SharedPreferenceDb(HomeActivity.this).getBai() == true) {
            sBai.setChecked(true);
        } else {
            sBai.setChecked(false);
        }


        sBai.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    new SharedPreferenceDb(HomeActivity.this)
                            .setOpenBai();
                    hand.sendEmptyMessage(1);
                }else{
                    new SharedPreferenceDb(HomeActivity.this)
                            .setCloseBai();
                    hand.sendEmptyMessage(1);
                }

            }
        });

        if (new SharedPreferenceDb(HomeActivity.this).getAi() == true) {
            sAi.setChecked(true);
        } else {
            sAi.setChecked(false);
        }


        sAi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    new SharedPreferenceDb(HomeActivity.this)
                            .setOpenAi();
                    hand.sendEmptyMessage(1);
                }else{
                    new SharedPreferenceDb(HomeActivity.this)
                            .setCloseAi();
                    hand.sendEmptyMessage(1);
                }

            }
        });

        if (new SharedPreferenceDb(HomeActivity.this).getTime() == true) {
            sTime.setChecked(true);
        } else {
            sTime.setChecked(false);
        }


        sTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    new SharedPreferenceDb(HomeActivity.this)
                            .setOpenTime();
                    hand.sendEmptyMessage(1);
                }else{
                    new SharedPreferenceDb(HomeActivity.this)
                            .setCloseTime();
                    hand.sendEmptyMessage(1);
                }

            }
        });

        if (new SharedPreferenceDb(HomeActivity.this).getLocation() == true) {
            sLocation.setChecked(true);
        } else {
            sLocation.setChecked(false);
        }


        sLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    new SharedPreferenceDb(HomeActivity.this)
                            .setOpenLocation();
                    hand.sendEmptyMessage(1);

                }else{
                    new SharedPreferenceDb(HomeActivity.this)
                            .setCloseLocation();
                    hand.sendEmptyMessage(1);
                }

            }
        });


        if (new SharedPreferenceDb(HomeActivity.this).getAll() == true) {
            sAll.setChecked(true);
        } else {
            sAll.setChecked(false);
        }

        sAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    new SharedPreferenceDb(HomeActivity.this)
                            .setOpenAll();
                    hand.sendEmptyMessage(1);
                }else{
                    new SharedPreferenceDb(HomeActivity.this)
                            .setCloseAll();
                    hand.sendEmptyMessage(1);
                }

            }
        });


        loadData();

        setupBlocker();

        //UI刷新线程
        mUIHandler = new Handler(){

            @Override
            public void handleMessage(Message msg) {

                String phonestr = msg.getData().getString("phone");
                String datestr = msg.getData().getString("date");

                HashMap<String, String> item = new HashMap<String, String>();
                item.put("phone", phonestr);
                item.put("date", datestr);

                //需要将其写入到数据库
                boolean bool = false;
                int frequency = 0;

                myDbHelper = new MyDbHelper(HomeActivity.this);
                myDbHelper.open();

                Cursor cur=myDbHelper.querData("record");
                cur.moveToFirst();
                while (!cur.isAfterLast()){
                    if(phonestr.equals(cur.getString(0))){
                        frequency=cur.getInt(1);
                        frequency++;
                        bool=true;
                        break;
                    }

                    cur.moveToNext();
                }

                if(bool){
                    myDbHelper.updataData(datestr,frequency,phonestr,"record");
                }else{
                    myDbHelper.insertData(phonestr,1,datestr,"record");
                }

                myDbHelper.close();

                //刷新  或者将adpter 写到loadData的外面
                loadData();
                filter_listview.invalidate();


                // 通知栏提示
                Context context = HomeActivity.this;
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                Notification.Builder builder = new Notification.Builder(context);
                Notification notification = builder
                        .setContentTitle(phonestr)
                        .setContentText(datestr)
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .build();

                notificationManager.notify(22, notification);
            }
        };




    }


    @Override
    public void initViews() {
        super.initViews();

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

                        update();

                    }
                });

                return view;
            }
        };

        filter_listview.setAdapter(adapter);

    }


    private void setupBlocker()
    {
        BlockerBuilder builder = new BlockerBuilder();


        //代码修改
        builder.setTrigger(mTrigger);
        builder.setHandler(mHandler);

        builder.addFilters(new PrefixFilter(IFilter.OP_BLOCKED, "156", "10086", "134")); //前缀拦截

        if(new SharedPreferenceDb(HomeActivity.this).getBai()){
            builder.addFilters(new NumeralFilter(IFilter.OP_PASS,getBais()));
        }
        if(new SharedPreferenceDb(HomeActivity.this).getHei()){
            builder.addFilters(new NumeralFilter(IFilter.OP_BLOCKED,getHeis()));
        }
        if(new SharedPreferenceDb(HomeActivity.this).getAi()){

        }
        if(new SharedPreferenceDb(HomeActivity.this).getAll()){

        }
        if(new SharedPreferenceDb(HomeActivity.this).getLocation()){

        }
        if(new SharedPreferenceDb(HomeActivity.this).getTime()){

        }

        mBlocker = builder.create();

        if(new SharedPreferenceDb(HomeActivity.this).getClose()){
            mBlocker.disable();
        }else{
            mBlocker.enable();
        }

    }

    private String[] getBais(){

        myDbHelper = new MyDbHelper(HomeActivity.this);
        myDbHelper.open();
        Cursor cursor = myDbHelper.querData("white");

        int size = cursor.getCount();

        String[] bais = new String[size];
        int i=0;

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){

            bais[i] = cursor.getString(0);
            i++;
            cursor.moveToNext();
        }

        myDbHelper.close();

        return bais;

    }


    private String[] getHeis(){

        myDbHelper = new MyDbHelper(HomeActivity.this);
        myDbHelper.open();
        Cursor cursor = myDbHelper.querData("black");

        int size = cursor.getCount();

        String[] heis = new String[size];
        int i=0;

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){

            heis[i] = cursor.getString(0);
            i++;
            cursor.moveToNext();
        }

        myDbHelper.close();

        return heis;

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.setting_ai:
                SkipActivityforClass(HomeActivity.this,AiActivity.class);
                break;
            case R.id.setting_time:
                SkipActivityforClass(HomeActivity.this,TimeActivity.class);
                break;
            case R.id.setting_location:
                SkipActivityforClass(HomeActivity.this,LocationActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
