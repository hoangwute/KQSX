package com.wuochoang.kqsx.model;

import io.realm.RealmObject;

/**
 * Created by admin on 08-Mar-18.
 */

public class InputInfoEntry extends RealmObject {
    private int id;
    private String code;
    private String digit;
    private String date;

    public InputInfoEntry(int id) {
        this.id = id;
    }

    public InputInfoEntry() {

    }

    public InputInfoEntry(String code, String date) {
        this.code = code;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDigit() {
        return digit;
    }

    public void setDigit(String digit) {
        this.digit = digit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
