package com.wuochoang.kqsx.network.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by quyenlx on 8/9/2017.
 */

public class UserEntity {
    @SerializedName("id")
    private int id;
    @SerializedName("userName")
    private String username;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
