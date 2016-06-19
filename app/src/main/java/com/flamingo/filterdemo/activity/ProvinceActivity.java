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
import com.flamingo.filterdemo.util.ProvinceUtil;
import com.flamingo.filterdemo.view.TitleBar;

import java.util.List;

/**
 * Created by Dontouch on 16/6/19.
 */
public class ProvinceActivity extends BaseActivity {

    private TitleBar titleBar;
    private ListView province_list;


    List<String> list= ProvinceUtil.getProvice();

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_province);

        initViews();


    }



    @Override
    public void initViews() {
        super.initViews();

        titleBar = (TitleBar) findViewById(R.id.province_title);
        titleBar.showLeft("选择地区", getResources().getDrawable(R.drawable.app_back),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SkipActivityforClass(ProvinceActivity.this,LocationActivity.class);
                    }
                });

        province_list = (ListView) findViewById(R.id.province_list);

        BaseAdapter adapter = new BaseAdapter() {


            @Override
            public int getCount() {
                return list.size();
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
                TextView tv=new TextView(ProvinceActivity.this);
                tv.setText(list.get(position));
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
                SharedPreferences sp=getSharedPreferences("rule_record", MODE_PRIVATE);
                SharedPreferences.Editor edit=sp.edit();
                edit.putInt("provinceid", position);
                if(position<list.size()-4){
                    //edit.putString("provincename", list.get(arg2)+"-");
                }else{
                    edit.putString("provincename", list.get(position));
                    edit.putString("cityname","");
                    ProvinceActivity.this.finish();
                }
                edit.commit();
                if(position<list.size()-4){
                    SkipActivityforClass(ProvinceActivity.this,CityActivity.class);
                    finish();
                }
            }
        });


    }

    @Override
    public void initData() {
        super.initData();
    }
}
