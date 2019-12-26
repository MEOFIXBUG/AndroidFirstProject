package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class Account {
    @SerializedName("address")
    private String adress;
    @SerializedName("password")
    private String password;
    @SerializedName("fullName")
    private String fullName;
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;
    @SerializedName("emailPhone")
    private String emailPhone;
    @SerializedName("gender")
    private int gender=0;//0: Female, 1: Male
    @SerializedName("dob")
    private String dob; //yyyy-mm-dd

    public int getGender() {
        return gender;
    }

    public String getDob() {
        return dob;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getAdress() {
        return adress;
    }

    public String getPhone() {
        return phone;
    }

    public void setGender( int Gender ) {
        this.gender=Gender;
    }

    public void setEmail( String Email ) {
        this.email=Email;
    }

    public void setPassword(String Password ) {
        this.password=Password;
    }

    public void setPhone(String Phone ) {
        this.phone=Phone;
    }

    public void setAdress(String Address ) {
        this.adress=Address;
    }

    public void setFullName( String FullName ) {
        this.fullName=FullName;
    }

    public String getEmailPhone() {
        return emailPhone;
    }

    public void setEmailPhone( String EmailPhone) {

        this.emailPhone=EmailPhone;
    }
}
