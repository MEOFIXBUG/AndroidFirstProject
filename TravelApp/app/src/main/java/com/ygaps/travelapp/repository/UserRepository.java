package com.ygaps.travelapp.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ygaps.travelapp.response.TourResponse;
import com.ygaps.travelapp.response.UserListRp;
import com.ygaps.travelapp.retrofit.Service.User.UserAPI;
import com.ygaps.travelapp.retrofit.retrofitRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private static final String TAG = UserRepository.class.getSimpleName();
    private UserAPI apiRequest;
    public UserRepository() {
        apiRequest = retrofitRequest.getRetrofitInstance().create(UserAPI.class);
    }
    public LiveData<UserListRp> searchUser(String keyword, long perpage, long page) {
        final MutableLiveData<UserListRp> data = new MutableLiveData<>();
        apiRequest.searchUser(keyword,page, Long.toString(perpage))
                .enqueue(new Callback<UserListRp>() {

                    @Override
                    public void onResponse(Call<UserListRp> call, Response<UserListRp> response) {

                        if (response.body() != null) {
                            //Log.d(TAG, "trips total result:: " + response.body().getTotal());
                            if(response.body().getTotal()!=0){
                                Log.d(TAG, "trips total result:: " + response.body().getTotal());
                                data.setValue(response.body());
                            }
                            else {
                                data.setValue(null);
                            }

                        }
                    }
                    @Override
                    public void onFailure(Call<UserListRp> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }
}
