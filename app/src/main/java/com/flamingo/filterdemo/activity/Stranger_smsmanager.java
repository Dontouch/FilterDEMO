package com.flamingo.filterdemo.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.AlteredCharSequence;
import android.widget.EditText;

import com.flamingo.filterdemo.R;

/**
 * Created by Dontouch on 16/6/22.
 */
public class Stranger_smsmanager {

    private Context context;
    private String question;
    private String answer;

    public Stranger_smsmanager(Context context){
        this.context = context;
    }

    public void Dialog(){
        getData();

        AlertDialog.Builder builder  = new AlertDialog.Builder(context);
        builder.setTitle("发送短信管理")
                .setMessage(question+"\n"+answer)
                .setIcon(context.getResources().getDrawable(R.drawable.edit));
        builder.setPositiveButton("答案设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                answer_set();
            }
        });

        builder.setNegativeButton("问题设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                question_set();
            }
        });

        builder.create().show();
    }

    private void getData(){
        SharedPreferences sp=context.getSharedPreferences("rule_record", Context.MODE_PRIVATE);
        question=sp.getString("question", "对不起，你的问题还没设置！");
        answer=sp.getString("answer", "对不起，你的答案还没设置！");
    }

    private void question_set(){
        final EditText tv = new EditText(context);
        tv.setTextColor(context.getResources().getColor(android.R.color.black));
        tv.setTextSize(20);

        AlertDialog.Builder builder  = new AlertDialog.Builder(context);

        builder.setTitle("问题设置")
                .setIcon(context.getResources().getDrawable(R.drawable.edit))
                .setView(tv);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sp=context.getSharedPreferences("rule_record", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit=sp.edit();
                edit.putString("question", tv.getText().toString());
                edit.commit();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();

    }


    private void answer_set(){
        final EditText tv = new EditText(context);
        tv.setTextColor(context.getResources().getColor(android.R.color.black));
        tv.setTextSize(20);

        AlertDialog.Builder builder  = new AlertDialog.Builder(context);

        builder.setTitle("答案设置")
                .setIcon(context.getResources().getDrawable(R.drawable.edit))
                .setView(tv);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sp=context.getSharedPreferences("rule_record", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit=sp.edit();
                edit.putString("answer", tv.getText().toString());
                edit.commit();

            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();

    }
}
