package com.smallgroup.animationapp.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.smallgroup.animationapp.domain.model.User;
import com.smallgroup.animationapp.repository.SplashRepository;

public class SplashViewModel extends ViewModel {

    private SplashRepository splashRepository;
    LiveData<User> isUserAuthenticatedLiveData;
    LiveData<User> userLiveData;

    public SplashViewModel() {
        splashRepository = new SplashRepository();
    }


    void checkIfUserIsAuthenticated() {
        isUserAuthenticatedLiveData = splashRepository.checkIfUserIsAuthenticatedInFirebase();
    }

    void setUid(String uid) {
        userLiveData = splashRepository.addUserToLiveData(uid);
    }

}
