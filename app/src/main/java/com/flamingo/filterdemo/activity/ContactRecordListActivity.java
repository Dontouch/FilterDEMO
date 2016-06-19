package com.flamingo.filterdemo.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.flamingo.filterdemo.Bean.ContactRecordBean;
import com.flamingo.filterdemo.R;
import com.flamingo.filterdemo.adapter.ContactRecordListAdapter;
import com.flamingo.filterdemo.constants.PhoneConstant;
import com.flamingo.filterdemo.db.MyDbHelper;
import com.flamingo.filterdemo.view.TitleBar;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by Dontouch on 16/6/14.
 */
public class ContactRecordListActivity extends BaseActivity {

    private TitleBar titleBar;
    private String key;

    private ContactRecordListAdapter adapter;
    private ListView contactRecordList;
    private List<ContactRecordBean> list;

    private MyDbHelper myDbHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_contact_record_list);

        this.key = (String) getIntent().getSerializableExtra("key");

        initData();
        initViews();

    }

    @Override
    public void initData() {
        super.initData();

        Uri calllog_uri = CallLog.Calls.CONTENT_URI; //获得联系人默认uri
        ContentResolver resolver = this.getContentResolver();  //获得ContentResolver对象


        Cursor cursor = resolver.query(calllog_uri, PhoneConstant.CALLLOG_PROJECTION, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);

        list = new ArrayList<ContactRecordBean>();


        if (null != cursor) {
            while (cursor.moveToNext()) {
                ContactRecordBean contactRecord = new ContactRecordBean();

                String number = cursor.getString(PhoneConstant.CALLLOG_NUMBER_INDEX);
                String name = cursor.getString(PhoneConstant.CALLLOG_CACHED_NAME_INDEX);
                String type = cursor.getString(PhoneConstant.CALLLOG_TYPE_INDEX);
                String date = cursor.getString(PhoneConstant.CALLLOG_DATE_INDEX);
                String duration = cursor.getString(PhoneConstant.CALLLOG_DURATION_INDEX);


                if (name == null) {
                    name = "";
                }

                if ("".equals(name)) {
                    contactRecord.setName("");
                } else {
                    contactRecord.setName(name);
                }


                contactRecord.setNumber(number);
                contactRecord.setDate(date);
                contactRecord.setDuration(duration);
                contactRecord.setType(type);

                list.add(contactRecord);

            }

            cursor.close();
        }


    }

    @Override
    public void initViews() {
        super.initViews();


        if("0".equals(key)){
            titleBar = (TitleBar) findViewById(R.id.contact_title);
            titleBar.showLeftImageAndRightStr("请选择黑名单","完成",
                    getResources().getDrawable(R.drawable.app_back),
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SkipActivityforClass(ContactRecordListActivity.this,
                                    HeiActivity.class);
                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for(int i =0 ; i<list.size();i++){
                                if(list.get(i).isAdd()){
                                    myDbHelper  =new MyDbHelper(ContactRecordListActivity.this);
                                    myDbHelper.open();
                                    String strnumber = list.get(i).getNumber();
                                    String strname = list.get(i).getName();
                                    myDbHelper.insertData(strnumber, strname, "black");
                                    myDbHelper.close();

                                    Toast toast=Toast.makeText(ContactRecordListActivity.this,"添加成功",Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();

                                    SkipActivityforClass(ContactRecordListActivity.this,HeiActivity.class);

                                    finish();

                                }
                            }

                        }
                    });
        }

        if("1".equals(key)){

        }

        contactRecordList = (ListView) findViewById(R.id.call_log_list);

        contactRecordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                list.get(position).setAdd(
                        !list.get(position).isAdd());
                adapter.notifyDataSetChanged();
            }
        });

        adapter = new ContactRecordListAdapter(this, list);
        contactRecordList.setAdapter(adapter);

    }

    
}
