package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class RegistrationFirebase {
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

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    @SerializedName("fcmToken")
    private String fcmToken;
    @SerializedName("deviceId")
    private String deviceId;
    @SerializedName("platform")
    private Integer platform;
    @SerializedName("appVersion")
    private String appVersion;
}
