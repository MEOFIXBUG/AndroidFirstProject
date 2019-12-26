package com.ygaps.travelapp.response;

import com.google.gson.annotations.SerializedName;

public class RecoveryOTP {
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    @SerializedName("type")
    private String type;
    @SerializedName("expiredOn")
    private long expire;
}
