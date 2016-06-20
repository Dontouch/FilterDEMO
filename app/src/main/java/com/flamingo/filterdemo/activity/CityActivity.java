package com.flamingo.filterdemo.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.flamingo.filterdemo.R;
import com.flamingo.filterdemo.util.LocationUtil;
import com.flamingo.filterdemo.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dontouch on 16/6/19.
 */
public class CityActivity extends BaseActivity{

    private TitleBar titleBar;
    private ListView province_list;

    List<ArrayList<String>> list = LocationUtil.getCity();
    List<String> item = LocationUtil.getProvice();


    int n=0;//确定省市

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_city);

        initViews();


    }



    @Override
    public void initViews() {
        super.initViews();

        titleBar = (TitleBar) findViewById(R.id.city_title);
        titleBar.showLeft("选择地区", getResources().getDrawable(R.drawable.app_back),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SkipActivityforClass(CityActivity.this,LocationActivity.class);
                    }
                });

        province_list = (ListView) findViewById(R.id.city_list);

        SharedPreferences sp=getSharedPreferences("rule_record", MODE_PRIVATE);
        n=sp.getInt("provinceid", 0);




        BaseAdapter adapter = new BaseAdapter() {


            @Override
            public int getCount() {
                return list.get(n).size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv=new TextView(CityActivity.this);
                tv.setText(list.get(n).get(position));
                tv.setTextSize(18);
                tv.setPadding(5, 7, 0, 7);
                tv.setTextColor(getResources().getColor(R.color.black));
                return tv;

            }
        };

        province_list.setAdapter(adapter);

        province_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences sp = getSharedPreferences("rule_record", MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putInt("cityid", position);
                edit.putString("cityname", list.get(n).get(position));
                edit.putString("provincename", item.get(n) + "-");
                edit.commit();
                finish();
            }
        });


    }

    @Override
    public void initData() {
        super.initData();
    }
}
