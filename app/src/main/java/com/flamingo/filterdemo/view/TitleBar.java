package com.flamingo.filterdemo.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flamingo.filterdemo.R;

/**
 * Created by Dontouch on 16/6/15.
 */
public class TitleBar extends RelativeLayout {

    private View titleBarView;
    private LayoutInflater layoutInflater;
    private ImageView leftImage;
    private ImageView rightImage;
    private TextView centerTitle;
    private TextView rightStr;
    private RelativeLayout allView;

    //三个回调函数
    public TitleBar(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);

        initTitleBarView(context);
    }

    public TitleBar(Context context,AttributeSet attrs)
    {
        super(context,attrs);

        initTitleBarView(context);
    }

    public TitleBar(Context context)
    {
        super(context);

        initTitleBarView(context);
    }

    //初始化TitleBar
    private void initTitleBarView(Context context) {
        // TODO Auto-generated method stub
        layoutInflater=LayoutInflater.from(context);
        titleBarView=layoutInflater.inflate(R.layout.view_titlebar,this);
        leftImage=(ImageView)titleBarView.findViewById(R.id.titleBarLeftImage);
        rightImage=(ImageView)titleBarView.findViewById(R.id.titleBarRightImage);
        centerTitle=(TextView)titleBarView.findViewById(R.id.title);
        allView=(RelativeLayout)titleBarView.findViewById(R.id.titleBarView);
        rightStr=(TextView)titleBarView.findViewById(R.id.titleBarRightStr);

    }


    //显示右边的图
    public void showRight(String title,int rightImages,OnClickListener onclick){
        centerTitle.setText(title);
        centerTitle.setVisibility(View.VISIBLE);
        rightImage.setImageResource(rightImages);
        rightImage.setVisibility(View.VISIBLE);
        rightImage.setOnClickListener(onclick);
    }

    //显示左边的图
    public void showLeft(String title, Drawable leftImages, OnClickListener onclick){
        centerTitle.setText(title);
        centerTitle.setVisibility(View.VISIBLE);
        leftImage.setImageDrawable(leftImages);
        leftImage.setVisibility(View.VISIBLE);
        leftImage.setOnClickListener(onclick);
    }

    //显示左右两边
    public void showLeftAndRight(String title,Drawable leftImages,Drawable rightImages,OnClickListener leftClick,OnClickListener rightClick){
        centerTitle.setText(title);
        centerTitle.setVisibility(View.VISIBLE);
        leftImage.setImageDrawable(leftImages);
        leftImage.setVisibility(View.VISIBLE);
        leftImage.setOnClickListener(leftClick);
        rightImage.setImageDrawable(rightImages);
        rightImage.setVisibility(View.VISIBLE);
        rightImage.setOnClickListener(rightClick);
    }

    //显示左边的图 及右边的文字
    public void showLeftImageAndRightStr(String title,String rightStrs,Drawable leftImages,OnClickListener leftClick,OnClickListener rightClick){
        centerTitle.setVisibility(View.VISIBLE);
        centerTitle.setText(title);
        leftImage.setImageDrawable(leftImages);
        leftImage.setVisibility(View.VISIBLE);
        rightStr.setText(rightStrs);
        rightStr.setVisibility(View.VISIBLE);
        leftImage.setOnClickListener(leftClick);
        rightStr.setOnClickListener(rightClick);
        rightStr.setTextSize(16);
    }


    //设置背景的颜色
    public void setBgColor(int color)
    {
        allView.setBackgroundColor(color);
    }

    //显示中间的标题
    public void showCenterTitle(String title)
    {
        centerTitle.setText(title);
        centerTitle.setVisibility(View.VISIBLE);
    }

}
