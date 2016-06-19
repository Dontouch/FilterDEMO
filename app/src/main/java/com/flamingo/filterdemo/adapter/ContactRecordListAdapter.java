package com.flamingo.filterdemo.adapter;

import android.content.Context;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.flamingo.filterdemo.Bean.ContactRecordBean;
import com.flamingo.filterdemo.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by Dontouch on 16/6/14.
 */
public class ContactRecordListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<ContactRecordBean> list = null;
    private Context mContext;


    static String api = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=";


    public ContactRecordListAdapter(Context context, List<ContactRecordBean> list){
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    public void updateListView(List<ContactRecordBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.item_contact_record_list, null);
            holder = new ViewHolder();
            holder.add =(ImageView) view.findViewById(R.id.record_add);
            holder.name = (TextView) view.findViewById(R.id.record_name);
            holder.type =(TextView) view.findViewById(R.id.record_type);
            holder.date =(TextView) view.findViewById(R.id.record_time);
            holder.number = (TextView) view.findViewById(R.id.record_number);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        ContactRecordBean contactRecord = list.get(i);

        if (contactRecord.isAdd())
            holder.add.setImageResource(R.drawable.round_checked);
        else
            holder.add.setImageResource(R.drawable.round_unchecked);


        String name = contactRecord.getName();
        String number = contactRecord.getNumber();

        String location = "";

//...

        if("".equals(name)){
            holder.name.setText(number);
            holder.number.setText(location);
        }else{
            holder.name.setText(name);
            holder.number.setText(number);
        }



        String type;
        switch (Integer.parseInt(contactRecord.getType())) {
            case CallLog.Calls.INCOMING_TYPE:
                type = "呼入";
                break;
            case CallLog.Calls.OUTGOING_TYPE:
                type = "呼出";
                break;
            case CallLog.Calls.MISSED_TYPE:
                type = "未接";
                break;
            default:
                type = "挂断";//应该是挂断.根据我手机类型判断出的
                break;
        }


        holder.type.setText(type);

        SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(Long.parseLong(contactRecord.getDate()));

        String time = sfd.format(date) + "  " + contactRecord.getDuration()+ "s";

        holder.date.setText(time);

        return view;
    }

    private static class ViewHolder {
        ImageView add;
        TextView name;
        TextView type;
        TextView date;
        TextView number;
    }
}
