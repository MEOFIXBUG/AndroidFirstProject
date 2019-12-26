package com.ygaps.travelapp.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ygaps.travelapp.UserInfo;
import com.ygaps.travelapp.model.StopPoint;
import com.ygaps.travelapp.model.Tour;
import com.ygaps.travelapp.model.comment;

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
