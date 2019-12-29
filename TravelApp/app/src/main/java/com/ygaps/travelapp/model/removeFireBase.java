package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class removeFireBase {


    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @SerializedName("fcmToken")
    private String fcmToken;
    @SerializedName("deviceId")
    private String deviceId;
}
