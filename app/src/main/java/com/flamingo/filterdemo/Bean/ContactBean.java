package com.flamingo.filterdemo.Bean;

import java.io.Serializable;

/**
 * Created by Dontouch on 16/6/14.
 */
public class ContactBean implements Serializable {


    private String name;
    private String number;
    private Long contactId;
    private Long photoId;

    private boolean isAdd;
    private String sortLetters;





    public ContactBean(String name, String sortLetters, boolean isAdd,
                       String number, Long contactId, Long photoId) {
        super();
        this.name = name;
        this.sortLetters = sortLetters;
        this.isAdd = isAdd;
        this.number = number;
        this.contactId = contactId;
        this.photoId = photoId;

    }

    public ContactBean() {
        super();
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Long getPhoto() {
        return photoId;
    }

    public void setPhoto(Long photoId) {
        this.photoId = photoId;
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

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }



}
