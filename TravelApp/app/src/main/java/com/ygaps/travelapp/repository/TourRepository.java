package com.ygaps.travelapp.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ygaps.travelapp.model.coordRequest;
import com.ygaps.travelapp.model.toInvited;
import com.ygaps.travelapp.response.ReviewTourResponse;
import com.ygaps.travelapp.response.StatusResponse;
import com.ygaps.travelapp.response.StopPointList;
import com.ygaps.travelapp.response.TourInfoResponse;
import com.ygaps.travelapp.response.TourResponse;
import com.ygaps.travelapp.response.UserListRp;
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
        apiRequest.getListTour(perpage, page,"startDate",false)
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
    public LiveData<ReviewTourResponse> getReviewOftour(long tourId, long page, long perpage) {
        final MutableLiveData<ReviewTourResponse> data = new MutableLiveData<>();
        apiRequest.getListReviewTour(tourId,page, perpage)
                .enqueue(new Callback<ReviewTourResponse>() {
                    @Override
                    public void onResponse(Call<ReviewTourResponse> call, Response<ReviewTourResponse> response) {

                        if(response.code()==200) {
                            if (response.body() != null) {
                                data.setValue(response.body());
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<ReviewTourResponse> call, Throwable t) {
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
    public LiveData<TourResponse> searchMyTrips(String keyword, long perpage, long page) {
        final MutableLiveData<TourResponse> data = new MutableLiveData<>();
        apiRequest.searchMyTrips(keyword,page, perpage)
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
        apiRequest.getTourInfo(id)
                .enqueue(new Callback<TourInfoResponse>() {

                    @Override
                    public void onResponse(Call<TourInfoResponse> call, Response<TourInfoResponse> response) {
                        if(response.body()!=null){
                           data.setValue(response.body());
                        }
                        else {
                            data.setValue(null);
                        }
                    }
                    @Override
                    public void onFailure(Call<TourInfoResponse> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        Log.d(TAG,data.toString());
        return data;
    }
    public LiveData<StatusResponse> Invite_Join(toInvited req){
        final MutableLiveData<StatusResponse> data = new MutableLiveData<>();
        apiRequest.Invite_Join(req)
                .enqueue(new Callback<StatusResponse>() {
                    @Override
                    public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {

                        if (response.body() != null) {
                            data.setValue(response.body());
                            Log.d(TAG, "tours total result:: " + response.body().getMessage());
                        }
                    }
                    @Override
                    public void onFailure(Call<StatusResponse> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }
    public LiveData<StopPointList> getSuggestDestination(coordRequest req){
        final MutableLiveData<StopPointList> data = new MutableLiveData<>();
        apiRequest.getSuggestDestination(req)
                .enqueue(new Callback<StopPointList>() {
                    @Override
                    public void onResponse(Call<StopPointList> call, Response<StopPointList> response) {

                        if (response.body() != null) {
                            data.setValue(response.body());
                            Log.d(TAG, "tours total result:: " + response.body().toString());
                        }
                    }
                    @Override
                    public void onFailure(Call<StopPointList> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }

}
