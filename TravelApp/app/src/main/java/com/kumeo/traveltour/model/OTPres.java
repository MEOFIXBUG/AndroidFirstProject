package com.kumeo.traveltour.model;

import com.google.gson.annotations.SerializedName;

public class OTPres {
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    @SerializedName("userId")
    private Integer userId;
    @SerializedName("newPassword")
    private String newPassword;
    @SerializedName("verifyCode")
    private String verifyCode;
}
