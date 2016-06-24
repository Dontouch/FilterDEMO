package com.flamingo.filterdemo.Bean;

import java.io.Serializable;

/**
 * Created by Dontouch on 16/6/14.
 */
public class ContactRecordBean implements Serializable {


    private String name;
    private String number;


    private boolean isAdd;

    private String date;
    private String duration;
    private String type;



    public ContactRecordBean(String name, boolean isAdd,
                             String number, String date, String duration , String type ) {
        super();
        this.name = name;
        this.isAdd = isAdd;
        this.number = number;
        this.date = date;
        this.duration = duration;
        this.type = type;

    }

    public ContactRecordBean() {
        super();
    }



    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean isAdd) {
        this.isAdd = isAdd;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getDate(){
        return date;
    }

    public void setDuration(String duration){
        this.duration = duration;
    }

    public String getDuration(){
        return duration;
    }


    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }








}
