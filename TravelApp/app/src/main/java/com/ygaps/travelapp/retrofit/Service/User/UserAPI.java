package com.ygaps.travelapp.retrofit.Service.User;

import com.ygaps.travelapp.UserInfo;
import com.ygaps.travelapp.model.OTPreq;
import com.ygaps.travelapp.model.OTPres;
import com.ygaps.travelapp.model.PasswordUpdate;
import com.ygaps.travelapp.response.ActiveResponse;
import com.ygaps.travelapp.response.ActiveResultResponse;
import com.ygaps.travelapp.response.RecoveryOTP;
import com.ygaps.travelapp.response.RecoveryResponse;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserAPI {

 @GET("/user/info")
 Call<UserInfo> myInfo(@Header("Authorization") String author);
 @GET("/user/send-active")
 Call <ActiveResponse> sendActive(@Query("userId") Integer userID,@Query("type") String type );
 @GET("/user/active")
 Call <ActiveResultResponse> getActiveStatus(@Query("userId") Integer userID, @Query("type") String type, @Query("verifyCode") String verifyCode);
@POST("/user/request-otp-recovery")
 Call<RecoveryOTP> requestOTP(@Body OTPreq req);
 @POST("/user/verify-otp-recovery")
 Call<RecoveryResponse> verifyOTP(@Body OTPres OTPres);
 @POST("/user/edit-info")
 Call<RecoveryResponse> updateInfo(@Header("Authorization") String author,@Body UserInfo userInfo);
@POST("/user/update-password")
 Call<RecoveryResponse> updatePassword(@Header("Authorization") String author, @Body PasswordUpdate updatePass);

}