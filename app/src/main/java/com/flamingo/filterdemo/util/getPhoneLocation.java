package com.flamingo.filterdemo.util;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dontouch on 16/6/20.
 */
public class getPhoneLocation {

    private static RequestQueue queues;


    private String jsonString = null;
    private JSONArray array = null;
    private JSONObject jsonObject = null;


    private Context mcontext;

    String place;


    public getPhoneLocation(Context context){
        this.mcontext = context;
    }


    public static RequestQueue getHttpQueues() {
        return queues;
    }




    public String calcPhoneCity(String phoneNumber)
    {
        queues = Volley.newRequestQueue(mcontext.getApplicationContext());

        String url = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel="+phoneNumber;

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        jsonString = response;

                        // 替换掉“__GetZoneResult_ = ”，让它能转换为JSONArray对象
                        jsonString = jsonString.replaceAll(
                                "^[__]\\w{14}+[_ = ]+", "[");
                        // System.out.println(jsonString+"]");
                        String jsonString2 = jsonString + "]";

                        // location.setText(jsonString2);

                        try {
                            array = new JSONArray(jsonString2);
                            jsonObject = array.getJSONObject(0);

                            // System.out.println(jsonObject.toString());


                            place = jsonObject.getString("province");


                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("GetLocation", error.toString());

            }
        });

        request.setTag("GetLocation");

        getPhoneLocation.getHttpQueues().add(request);

        return place;
    }

}
