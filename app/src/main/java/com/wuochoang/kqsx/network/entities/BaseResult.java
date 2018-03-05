package com.wuochoang.kqsx.network.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by quyenlx on 8/9/2017.
 */

public class BaseResult<T> {
    @SerializedName("message")
    protected String message;
    @SerializedName("status")
    protected String status;
    @SerializedName("data")
    protected T data;

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }
}
