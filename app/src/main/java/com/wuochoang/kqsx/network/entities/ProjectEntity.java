package com.wuochoang.kqsx.network.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ThanhNH on 8/11/2017.
 */

public class ProjectEntity {
    @SerializedName("id")
    private int mId;
    @SerializedName("projectCode")
    private String mProjectCode;
    @SerializedName("projectName")
    private String mProjectName;
    @SerializedName("pm")
    private List<ProjectManagerEntity> mListPm;
    @SerializedName("members")
    private List<MemberProjectEntity> mListMember;
}
