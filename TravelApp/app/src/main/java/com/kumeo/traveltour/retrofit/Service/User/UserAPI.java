package com.kumeo.traveltour.retrofit.Service.User;

import android.content.Context;
import android.content.SharedPreferences;

import com.kumeo.traveltour.R;
import com.kumeo.traveltour.UserInfo;
import com.kumeo.traveltour.extras.MyApplication;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface UserAPI {

 @GET("/user/info")
 Call<UserInfo> myInfo(@Header("Authorization") String author);
 /*@Headers({
         "Accept: application/json",
         "Content-Type: application/json"
 })*/

}