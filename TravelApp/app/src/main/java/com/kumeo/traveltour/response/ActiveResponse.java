package com.kumeo.traveltour.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActiveResponse {

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public long getExpiredOn() {
        return expiredOn;
    }

    public void setExpiredOn(long expiredOn) {
        this.expiredOn = expiredOn;
    }

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("sendTo")
    @Expose
    private String sendTo;
    @SerializedName("expiredOn")
    @Expose
    private long expiredOn;


}
