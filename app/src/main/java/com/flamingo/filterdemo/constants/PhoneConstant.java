package com.flamingo.filterdemo.constants;

import android.provider.CallLog;
import android.provider.ContactsContract;

/**
 * Created by Dontouch on 16/6/19.
 */
public class PhoneConstant {

    public static final String[] PHONES_PROJECTION = new String[]
            {
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, //联系人姓名
                    ContactsContract.CommonDataKinds.Phone.NUMBER,       //电话号码
                    ContactsContract.Contacts.Photo.PHOTO_ID,     //联系人头像
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID    //ID
            };


    public static final  String[] CALLLOG_PROJECTION = new String[]{
            CallLog.Calls.NUMBER, // 电话号码
            CallLog.Calls.CACHED_NAME, // 联系人
            CallLog.Calls.TYPE, // 通话类型
            CallLog.Calls.DATE, // 通话时间
            CallLog.Calls.DURATION // 通话时长

    };

    public static final int PHONES_DISPLAY_NAME_INDEX = 0;

    public static final int PHONES_NUMBER_INDEX = 1;

    public static final int PHONES_PHOTO_ID_INDEX = 2;

    public static final int PHONES_CONTACT_ID_INDEX = 3;


    public static final int CALLLOG_NUMBER_INDEX = 0;

    public static final int CALLLOG_CACHED_NAME_INDEX = 1;

    public static final int CALLLOG_TYPE_INDEX = 2;

    public static final int CALLLOG_DATE_INDEX = 3;

    public static final int CALLLOG_DURATION_INDEX = 4;
}
