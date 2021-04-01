package com.smallgroup.animationapp.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.smallgroup.animationapp.R;
import com.smallgroup.animationapp.domain.model.User;
import com.smallgroup.animationapp.ui.app.MainAppActivity;

public class SplashActivity extends AppCompatActivity {

    private static final String USER = "CURRENT USER";
    SplashViewModel splashViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        initSplashViewModel();
        checkIfUserIsAuthenticated();
    }

    private void initSplashViewModel() {
        splashViewModel =  ViewModelProviders.of(this).get(SplashViewModel.class);
    }

    private void checkIfUserIsAuthenticated() {
        splashViewModel.checkIfUserIsAuthenticated();
        splashViewModel.isUserAuthenticatedLiveData.observe(this, user -> {
            if (!user.isAuth) {
                goToAuthInActivity();
                finish();
            } else {
                getUserFromDatabase(user.uid);
            }
        });
    }

    private void getUserFromDatabase(String uid) {
        splashViewModel.setUid(uid);
        splashViewModel.userLiveData.observe(this, user -> {
            goToMainAppActivity(user);
            finish();
        });
    }

    private void goToAuthInActivity() {
        Intent intnet = new Intent(this, AuthActivity.class);
        startActivity(intnet);
        finish();
    }

    private void goToMainAppActivity(User user) {
        Intent intent = new Intent(this, MainAppActivity.class);
        intent.putExtra(USER, user);
        startActivity(intent);
        finish();
    }

}