package com.wuochoang.kqsx.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admin on 06-Mar-18.
 */

public class ApiResult {
    @SerializedName("sms_code")
    @Expose
    private String smsCode;
    @SerializedName("kqxs")
    @Expose
    private List<LotteryResult> lotteryResults = null;

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public List<LotteryResult> getLotteryResults() {
        return lotteryResults;
    }

    public void setLotteryResults(List<LotteryResult> lotteryResults) {
        this.lotteryResults = lotteryResults;
    }


}
