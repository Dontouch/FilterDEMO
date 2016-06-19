package com.flamingo.filterdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.flamingo.filterdemo.Bean.HeiBean;
import com.flamingo.filterdemo.R;

import java.util.List;


/**
 * Created by Dontouch on 16/6/14.
 */
public class HeiListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<HeiBean> list = null;
    private Context mContext;


    public HeiListAdapter(Context context, List<HeiBean> list){
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    public void updateListView(List<HeiBean> list) {
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
            view = inflater.inflate(R.layout.item_hei_list, null);
            holder = new ViewHolder();

            holder.name =(TextView) view.findViewById(R.id.item_hei_name);
            holder.number = (TextView) view.findViewById(R.id.item_hei_number);
            holder.fequency = (TextView) view.findViewById(R.id.item_hei_frequency);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        HeiBean hei = list.get(i);


        String name = hei.getName();
        String number = hei.getNumber();
        String frequency = hei.getFrequency();

        holder.name.setText(name);
        holder.number.setText(number);
        holder.number.setText(frequency);


        return view;
    }


    private static class ViewHolder {
        TextView name;
        TextView number;
        TextView fequency;
    }
}
