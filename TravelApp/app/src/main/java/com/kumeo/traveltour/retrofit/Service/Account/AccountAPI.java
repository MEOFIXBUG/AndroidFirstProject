package com.kumeo.traveltour.retrofit.Service.Account;

import com.kumeo.traveltour.model.Account;
import com.kumeo.traveltour.model.facebookToken;
import com.kumeo.traveltour.response.LoginResponse;
import com.kumeo.traveltour.response.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AccountAPI {
 @POST("user/login")
 Call<LoginResponse> login(@Body Account request);
 /*@Headers({
         "Accept: application/json",
         "Content-Type: application/json"
 })*/
 @POST("user/register")
 Call<Account> register(@Body Account request);

 @POST("user/login/by-facebook")
 Call<LoginResponse> loginByFacebook(@Body facebookToken fbAccount);
}
