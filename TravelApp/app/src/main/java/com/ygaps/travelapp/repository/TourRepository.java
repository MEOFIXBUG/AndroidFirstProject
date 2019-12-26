package com.ygaps.travelapp.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ygaps.travelapp.response.TourInfoResponse;
import com.ygaps.travelapp.response.TourResponse;
import com.ygaps.travelapp.retrofit.Service.Tour.TourAPI;
import com.ygaps.travelapp.retrofit.retrofitRequest;

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


                        if (response.body() != null) {
                            data.setValue(response.body());
                            Log.d(TAG, "tours total result:: " + response.body().getTotal());
                        }
                    }
                    @Override
                    public void onFailure(Call<TourResponse> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }
    public LiveData<TourResponse> getMyTrips(long perpage,long page) {
        final MutableLiveData<TourResponse> data = new MutableLiveData<>();
        apiRequest.getMyTrips(page, Long.toString(perpage))
                .enqueue(new Callback<TourResponse>() {

                    @Override
                    public void onResponse(Call<TourResponse> call, Response<TourResponse> response) {

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
                    public void onFailure(Call<TourResponse> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }
    public LiveData<TourInfoResponse> getTourInfoByID(long id){
        final MutableLiveData<TourInfoResponse> data = new MutableLiveData<>();
        Log.d(TAG, "toi da vao day");
        apiRequest.getTourInfo(id)
                .enqueue(new Callback<TourInfoResponse>() {

                    @Override
                    public void onResponse(Call<TourInfoResponse> call, Response<TourInfoResponse> response) {

                        //Log.d(TAG, "trips total result:: " + response.body().getTotal());
                        if(response.body()!=null){
                            Log.d(TAG, "Name:: " + response.body().getName());
                            Log.d(TAG, "t met nhan:: " + response.body());
                           data.setValue(response.body());
                        }
                        else {
                            Log.d(TAG, "null1: ");
                            data.setValue(null);
                        }


                    }
                    @Override
                    public void onFailure(Call<TourInfoResponse> call, Throwable t) {
                        Log.d(TAG, "null2: ");
                        data.setValue(null);
                    }
                });
        Log.d(TAG,data.toString());
        return data;
    }

}
