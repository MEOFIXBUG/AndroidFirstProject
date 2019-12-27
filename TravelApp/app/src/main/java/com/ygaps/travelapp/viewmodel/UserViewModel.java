package com.ygaps.travelapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ygaps.travelapp.repository.TourRepository;
import com.ygaps.travelapp.repository.UserRepository;
import com.ygaps.travelapp.response.TourResponse;
import com.ygaps.travelapp.response.UserListRp;

public class UserViewModel extends AndroidViewModel {
    private static final String TAG = UserViewModel.class.getSimpleName();
    private UserRepository userRepository;
    private LiveData<UserListRp> userResponseLiveData;
    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository();
    }
    public LiveData<UserListRp> searchUsers(String key,long perpage,long page) {
        try {
            userResponseLiveData = userRepository.searchUser(key,perpage,page);
        }
        catch (Exception ex){
            userResponseLiveData= null;
        }
        return userResponseLiveData;
    }
}
