package com.ygaps.travelapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ygaps.travelapp.model.toInvited;
import com.ygaps.travelapp.repository.TourRepository;
import com.ygaps.travelapp.response.StatusResponse;
import com.ygaps.travelapp.response.TourInfoResponse;
import com.ygaps.travelapp.response.TourResponse;

public class TourViewModel extends AndroidViewModel {
    private static final String TAG = TourViewModel.class.getSimpleName();
    private TourRepository tourRepository;
    private LiveData<TourResponse> tourResponseLiveData;
    private LiveData<TourInfoResponse> tourInfoResponseLiveData;
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
    public LiveData<TourInfoResponse> getTourInfo(long id){
        try {
            Log.d(TAG," re: " + id);
            tourInfoResponseLiveData= tourRepository.getTourInfoByID(id);
             return tourInfoResponseLiveData;
        }
        catch (Exception ex){
            Log.d(TAG," re: " + ex);
            return null;
        }
    }
    public LiveData<StatusResponse> Invite_Join(toInvited req){
        try {
            Log.d(TAG," re: " + req.getInvitedUserId());
            return  tourRepository.Invite_Join(req);
        }
        catch (Exception ex){
            Log.d(TAG," re: " + ex);
            return null;
        }
    }
}
