package com.wuochoang.kqsx.network.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ThanhNH on 8/11/2017.
 */

public class MetaDataEntity {
    @SerializedName("projects")
    private List<ProjectEntity> mProjectList;
    @SerializedName("leaders")
    private List<LeaderEntity> mLeaderList;
}
