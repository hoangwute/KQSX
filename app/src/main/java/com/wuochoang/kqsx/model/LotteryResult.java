package com.wuochoang.kqsx.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by admin on 06-Mar-18.
 */

public class LotteryResult extends RealmObject {
    @SerializedName("date")
    @Expose
    @PrimaryKey
    private String date;
    @SerializedName("g0")
    @Expose
    private String g0;
    @SerializedName("g1")
    @Expose
    private String g1;
    @SerializedName("g2")
    @Expose
    private String g2;
    @SerializedName("g3")
    @Expose
    private String g3;
    @SerializedName("g4")
    @Expose
    private String g4;
    @SerializedName("g5")
    @Expose
    private String g5;
    @SerializedName("g6")
    @Expose
    private String g6;
    @SerializedName("g7")
    @Expose
    private String g7;
    @SerializedName("g8")
    @Expose
    private String g8;
    @SerializedName("province_id")
    @Expose
    private String provinceId;
    @SerializedName("region")
    @Expose
    private String region;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getG0() {
        return g0;
    }

    public void setG0(String g0) {
        this.g0 = g0;
    }

    public String getG1() {
        return g1;
    }

    public void setG1(String g1) {
        this.g1 = g1;
    }

    public String getG2() {
        return g2;
    }

    public void setG2(String g2) {
        this.g2 = g2;
    }

    public String getG3() {
        return g3;
    }

    public void setG3(String g3) {
        this.g3 = g3;
    }

    public String getG4() {
        return g4;
    }

    public void setG4(String g4) {
        this.g4 = g4;
    }

    public String getG5() {
        return g5;
    }

    public void setG5(String g5) {
        this.g5 = g5;
    }

    public String getG6() {
        return g6;
    }

    public void setG6(String g6) {
        this.g6 = g6;
    }

    public String getG7() {
        return g7;
    }

    public void setG7(String g7) {
        this.g7 = g7;
    }

    public String getG8() {
        return g8;
    }

    public void setG8(String g8) {
        this.g8 = g8;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
