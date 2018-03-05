package com.wuochoang.kqsx.network.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ThanhNH on 8/11/2017.
 */

public class LeaderEntity {
    @SerializedName("id")
    private int mId;
    @SerializedName("userName")
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
    private String mLoginTokenId;
    @SerializedName("roleId")
    private int mRoleId;
    @SerializedName("lockedTime")
    private long mLockedTime;
    @SerializedName("lockedReason")
    private long mLockedReason;
    @SerializedName("status")
    private int mStatus;
    @SerializedName("createUserId")
    private int mCreateUserId;
    @SerializedName("createTime")
    private long mCreateTime;
    @SerializedName("updateUserId")
    private int mUpdateUserId;
    @SerializedName("updateTime")
    private long mUpdateUserTime;
    @SerializedName("token")
    private String mToken;
    @SerializedName("roleManager")
    private String[] mRoleManager;
}
