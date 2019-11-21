package com.kumeo.traveltour.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kumeo.traveltour.response.TourResponse;
import com.kumeo.traveltour.retrofit.Service.Tour.TourAPI;
import com.kumeo.traveltour.retrofit.retrofitRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TourRepository {
    private static final String TAG = TourRepository.class.getSimpleName();
    private TourAPI apiRequest;

    public TourRepository() {
        apiRequest = retrofitRequest.getRetrofitInstance().create(TourAPI.class);
    }

    public LiveData<TourResponse> getTours(long perpage,long page) {
        final MutableLiveData<TourResponse> data = new MutableLiveData<>();
        apiRequest.getListTour(perpage, page)
                .enqueue(new Callback<TourResponse>() {

                    @Override
                    public void onResponse(Call<TourResponse> call, Response<TourResponse> response) {
                        Log.d(TAG, "onResponse response:: " + response);


                        if (response.body() != null) {
                            data.setValue(response.body());

                            Log.d(TAG, "articles total result:: " + response.body().getTotal());

                            Log.d(TAG, "articles title pos 0:: " + response.body().getTours().get(0).getName());
                        }
                    }
                    @Override
                    public void onFailure(Call<TourResponse> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }
}
