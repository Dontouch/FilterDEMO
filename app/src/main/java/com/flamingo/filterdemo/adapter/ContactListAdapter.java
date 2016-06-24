package com.flamingo.filterdemo.adapter;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;


import com.flamingo.filterdemo.R;
import com.flamingo.filterdemo.view.CircleImageView;

import java.io.InputStream;
import java.util.List;

import com.flamingo.filterdemo.Bean.ContactBean;


/**
 * Created by Dontouch on 16/6/14.
 */
public class ContactListAdapter extends BaseAdapter implements SectionIndexer {

    private LayoutInflater inflater;
    private List<ContactBean> list = null;
    private Context mContext;


    public ContactListAdapter(Context context, List<ContactBean> list){
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    public void updateListView(List<ContactBean> list) {
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

        final ContactBean mcontact = list.get(i);
        ViewHolder holder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.item_contact_list, null);
            holder = new ViewHolder();
            holder.circleImageView = (CircleImageView) view
                    .findViewById(R.id.circleImageView);
            holder.add =(ImageView) view.findViewById(R.id.add);
            holder.alpha = (TextView) view.findViewById(R.id.alpha);
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.number = (TextView) view.findViewById(R.id.number);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        int section = getSectionForPosition(i);

        if(i == getPositionForSection(section)){
            holder.alpha.setVisibility(View.VISIBLE);
            holder.alpha.setText(mcontact.getSortLetters());
        }else{
            holder.alpha.setVisibility(View.INVISIBLE);
        }

        ContactBean contact = list.get(i);

        if (contact.isAdd())
            holder.add.setImageResource(R.drawable.round_checked);
        else
            holder.add.setImageResource(R.drawable.round_unchecked);

        ContentResolver resolver = mContext.getContentResolver();

        Bitmap contactPhoto = null;



        String name = contact.getName();
        String number = contact.getNumber();
        holder.name.setText(name);
        holder.number.setText(number);

        if (contact.getPhoto() >0) {
            Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contact.getContactId());
            InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
            contactPhoto = BitmapFactory.decodeStream(input);
            holder.circleImageView.setImageBitmap(contactPhoto);
        } else {
            holder.circleImageView.setImageResource(R.drawable.default_round_head);
        }


        return view;
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }

    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    private static class ViewHolder {
        CircleImageView circleImageView;
        ImageView add;
        TextView alpha;
        TextView name;
        TextView number;
    }
}
