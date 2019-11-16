package com.kumeo.traveltour.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginResponse {
    @SerializedName("userId")
    //@Expose
    private String userId;
    @SerializedName("token")
    //@Expose
    private String token;
    @SerializedName("emailVerified")
    //@Expose
    private boolean emailVerified;
    @SerializedName("phoneVerified")
    //@Expose
    private boolean phoneVerified;
    public String getUserId() {
        return userId;
    }

    public void setUserId(String UserId) {
        this.userId = UserId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String TOKEN) {
        this.token = TOKEN;
    }

    public boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean EmailVerified) {
        this.emailVerified = EmailVerified;
    }

    public boolean getPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(boolean PhoneVerified) {
        this.phoneVerified = PhoneVerified;
    }


}
