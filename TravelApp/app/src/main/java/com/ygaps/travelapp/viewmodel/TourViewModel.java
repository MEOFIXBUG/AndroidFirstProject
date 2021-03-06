package com.ygaps.travelapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ygaps.travelapp.model.coordRequest;
import com.ygaps.travelapp.model.toInvited;
import com.ygaps.travelapp.model.toResponse;
import com.ygaps.travelapp.repository.TourRepository;
import com.ygaps.travelapp.response.ReviewTourResponse;
import com.ygaps.travelapp.response.StatusResponse;
import com.ygaps.travelapp.response.StopPointList;
import com.ygaps.travelapp.response.TourInfoResponse;
import com.ygaps.travelapp.response.TourResponse;

public class TourViewModel extends AndroidViewModel {
    private static final String TAG = TourViewModel.class.getSimpleName();
    private TourRepository tourRepository;
    private LiveData<TourResponse> tourResponseLiveData;
    private LiveData<TourInfoResponse> tourInfoResponseLiveData;
    private LiveData<ReviewTourResponse> ReviewTourResponseLiveData;
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
    public LiveData<ReviewTourResponse> getReviewOfTour(long tourId,long page, long perpage) {
        try {
            ReviewTourResponseLiveData = tourRepository.getReviewOftour(tourId, page,perpage);
        }
        catch (Exception ex){
            ReviewTourResponseLiveData= null;
        }
        return ReviewTourResponseLiveData;
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
    public LiveData<TourResponse> searchMyTrips(String keyWord, long perpage,long page) {
        try {
            tourResponseLiveData = tourRepository.searchMyTrips(keyWord,perpage,page);
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
            Log.d(TAG," reInvite: " + req.getInvitedUserId());
            return  tourRepository.Invite_Join(req);
        }
        catch (Exception ex){
            Log.d(TAG," reInvite: " + ex);
            return null;
        }
    }
    public LiveData<StopPointList> getSuggestDestination(coordRequest req){
        try {
            Log.d(TAG," re: " + req.toString());
            return  tourRepository.getSuggestDestination(req);
        }
        catch (Exception ex){
            Log.d(TAG," re: " + ex);
            return null;
        }
    }
    public LiveData<TourResponse> getInvitation(long perpage,long page) {
        try {
            tourResponseLiveData = tourRepository.getInvitation(perpage,page);
        }
        catch (Exception ex){
            tourResponseLiveData= null;
        }
        return tourResponseLiveData;
    }
    public boolean AgreeInvation(long tourId ){
        try {
           toResponse req = new toResponse();
           req.setTourId(Long.toString( tourId));
            req.setIsAccept(Boolean.TRUE);
            return  tourRepository.responseInvation(req);
        }
        catch (Exception ex){
            Log.d(TAG," re Invite: " + ex);
            return false;
        }
    }
    public boolean DenyInvation(long tourId){
        try {
            toResponse req = new toResponse();
            req.setTourId(Long.toString( tourId));
            req.setIsAccept(Boolean.FALSE);
            return  tourRepository.responseInvation(req);
        }
        catch (Exception ex){
            Log.d(TAG," re Invite: " + ex);
            return false;
        }
    }
}
