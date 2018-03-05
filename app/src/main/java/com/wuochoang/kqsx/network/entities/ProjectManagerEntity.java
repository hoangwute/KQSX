package com.wuochoang.kqsx.network.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ThanhNH on 8/11/2017.
 */

public class ProjectManagerEntity {
    @SerializedName("id")
    private int mId;
    @SerializedName("username")
    private String mUserName;
    @SerializedName("firstName")
    private String mFirstName;
    @SerializedName("lastName")
    private String mLastName;
    @SerializedName("departmentId")
    private String mDepartmentId;
    @SerializedName("lastPasswordChange")
    private String mLastPasswordChange;
    @SerializedName("loginTokenId")
    private String mLoginToken;
    @SerializedName("roleId")
    private String mRoleId;
    @SerializedName("lockedTime")
    private long mLockedTime;
    @SerializedName("lockedReason")
    private long mLockedReason;
    @SerializedName("status")
    private int mStatus;
    @SerializedName("createUserId")
    private String mCreateUserId;
    @SerializedName("createTime")
    private long mCreateTime;
    @SerializedName("updateUserId")
    private long mUpdateUserId;
    @SerializedName("updateTime")
    private long mUpdateTime;
    @SerializedName("token")
    private int mToken;
    @SerializedName("roleManager")
    private String[] mRoleManager;
}
