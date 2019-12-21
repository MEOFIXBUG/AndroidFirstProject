package com.kumeo.traveltour.retrofit.Service.User;

import android.content.Context;
import android.content.SharedPreferences;

import com.kumeo.traveltour.R;
import com.kumeo.traveltour.UserInfo;
import com.kumeo.traveltour.extras.MyApplication;
import com.kumeo.traveltour.response.ActiveResponse;
import com.kumeo.traveltour.response.ActiveResultResponse;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface UserAPI {

 @GET("/user/info")
 Call<UserInfo> myInfo(@Header("Authorization") String author);
 @GET("/user/send-active")
 Call <ActiveResponse> sendActive(@Query("userId") Integer userID,@Query("type") String type );
 @GET("/user/active")
 Call <ActiveResultResponse> getActiveStatus(@Query("userId") Integer userID, @Query("type") String type, @Query("verifyCode") String verifyCode);

}