package com.kumeo.traveltour.retrofit.Service.Account;

import com.kumeo.traveltour.model.Account;
import com.kumeo.traveltour.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AccountService {
    @POST("user/login")
    Call<LoginResponse> login(@Body Account request);
    //@POST("user/register")

}
