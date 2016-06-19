package com.flamingo.filterdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.flamingo.filterdemo.Bean.BaiBean;
import com.flamingo.filterdemo.R;

import java.util.List;


/**
 * Created by Dontouch on 16/6/14.
 */
public class BaiListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<BaiBean> list = null;
    private Context mContext;


    public BaiListAdapter(Context context, List<BaiBean> list){
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    public void updateListView(List<BaiBean> list) {
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
            view.findViewById(R.id.item_hei_frequency).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.item_hei_frequency_title).setVisibility(View.INVISIBLE);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        BaiBean bai = list.get(i);


        String name = bai.getName();
        String number = bai.getNumber();


        holder.name.setText(name);
        holder.number.setText(number);


        return view;
    }


    private static class ViewHolder {
        TextView name;
        TextView number;

    }
}
