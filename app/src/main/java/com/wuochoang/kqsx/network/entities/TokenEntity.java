package com.wuochoang.kqsx.network.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by quyenlx on 8/9/2017.
 */

public class TokenEntity {
    @SerializedName("status")
    private int status;
    @SerializedName("tokenValue")
    String tokenValue;

    public int getStatus() {
        return status;
    }

    public String getTokenValue() {
        return tokenValue;
    }
}
