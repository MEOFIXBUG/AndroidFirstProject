package com.ygaps.travelapp.retrofit.Service.Account;

import com.ygaps.travelapp.model.Account;
import com.ygaps.travelapp.model.facebookToken;
import com.ygaps.travelapp.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
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
