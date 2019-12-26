package com.ygaps.travelapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ygaps.travelapp.repository.TourRepository;
import com.ygaps.travelapp.response.TourInfoResponse;
import com.ygaps.travelapp.response.TourResponse;

public class TourViewModel extends AndroidViewModel {
    private TourRepository tourRepository;
    private LiveData<TourResponse> tourResponseLiveData;
    public TourViewModel(@NonNull Application application) {
        super(application);
        tourRepository = new TourRepository();
    }
//    public void init(long perpage, long page, int flag ) {
//        if (this.tourResponseLiveData != null) {
//            // ViewModel is created per Fragment so
//            // we know the userId won't change
//            return;
//        }
//        if( flag ==1 ) {
//            tourResponseLiveData = tourRepository.getTours(perpage,page);
//        }
//        else {
//            if(flag==2){
//                try {
//                    tourResponseLiveData = tourRepository.getMyTrips(perpage,page);
//                }
//                catch (Exception ex){
//                    tourResponseLiveData= null;
//                }
//            }
//        }
//    }
    public LiveData<TourResponse> getTours(long perpage,long page) {
        try {
            tourResponseLiveData = tourRepository.getTours(perpage,page);
        }
        catch (Exception ex){
            tourResponseLiveData= null;
        }
        return tourResponseLiveData;
    }
    public LiveData<TourResponse> getMyTrips(long perpage,long page) {
        try {
            tourResponseLiveData = tourRepository.getMyTrips(perpage,page);
        }
        catch (Exception ex){
            tourResponseLiveData= null;
        }
        return tourResponseLiveData;
    }
    public LiveData<TourInfoResponse> getTourInfo(int id){
        try {
             return tourRepository.getTourInfoByID(id);
        }
        catch (Exception ex){
            return null;
        }
    }
}
