package com.kumeo.traveltour.retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class retrofitRequest {
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://35.197.153.192:3000/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            //OkHttpClient httpClient = new OkHttpClient.Builder().build();
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            /*httpClientBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request.Builder requestBuilder = chain.request().newBuilder();
                    requestBuilder.header("Content-Type", "application/json");
                    requestBuilder.header("Accept", "application/json");
                    return chain.proceed(requestBuilder.build());
                }
            });*/
            OkHttpClient httpClient = httpClientBuilder.build();
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
