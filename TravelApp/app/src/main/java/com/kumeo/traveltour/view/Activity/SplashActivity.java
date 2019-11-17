package com.kumeo.traveltour.view.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.kumeo.traveltour.R;
import com.kumeo.traveltour.extras.MyApplication;
import com.kumeo.traveltour.view.Fragment.LoginFragment;

public class SplashActivity extends AppCompatActivity {

    public static MyApplication appPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        appPreference = new MyApplication(this);
        if (appPreference.getLoginStatus()){
            //when true
            Intent intent = new Intent(SplashActivity.this, TourActivity.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            SplashActivity.this.finish();
        } else {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            SplashActivity.this.finish();
        }
    }
}
