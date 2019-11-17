package com.kumeo.traveltour.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kumeo.traveltour.repository.TourRepository;
import com.kumeo.traveltour.response.TourResponse;

public class TourViewModel extends AndroidViewModel {
    private TourRepository tourRepository;
    private LiveData<TourResponse> tourResponseLiveData;
    public TourViewModel(@NonNull Application application) {
        super(application);
        tourRepository = new TourRepository();
    }
    public LiveData<TourResponse> getTourResponseLiveData(String Auth, long perPage, long page) {
        this.tourResponseLiveData=tourRepository.getTours(Auth,perPage,page);
        return tourResponseLiveData;
    }
}
