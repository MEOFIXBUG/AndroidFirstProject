package com.kumeo.traveltour.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kumeo.traveltour.UserInfo;
import com.kumeo.traveltour.model.StopPoint;
import com.kumeo.traveltour.model.Tour;
import com.kumeo.traveltour.model.comment;

import java.util.List;

public class TourInfoResponse extends Tour {
    @SerializedName("stopPoints")
    @Expose
    private List<StopPoint> stopPoints=null;
    @SerializedName("comments")
    @Expose
    private List<comment> comments=null;
    @SerializedName("comments")
    @Expose
    private List<UserInfo> members=null;
}
