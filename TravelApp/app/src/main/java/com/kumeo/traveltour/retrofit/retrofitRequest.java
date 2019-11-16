package com.kumeo.traveltour.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class retrofitRequest {
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://35.197.153.192:3000/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            OkHttpClient httpClient = new OkHttpClient.Builder().build();
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
