package com.kumeo.traveltour.view.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.FrameLayout;

import com.kumeo.traveltour.R;
import com.kumeo.traveltour.extras.MyApplication;
import com.kumeo.traveltour.retrofit.Service.Account.AccountAPI;
import com.kumeo.traveltour.retrofit.Service.AccountInterface;
import com.kumeo.traveltour.retrofit.retrofitRequest;
import com.kumeo.traveltour.view.Fragment.LoginFragment;
import com.kumeo.traveltour.view.Fragment.RegisterFragment;

public class MainActivity extends AppCompatActivity implements AccountInterface {

    //public static MyApplication appPreference;
   // public static AccountViewModel accountViewModel;
    FrameLayout container_layout;
    public static AccountAPI accountApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //accountViewModel=ViewModelProviders.of(this).get(AccountViewModel.class);
        //appPreference = new MyApplication(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        accountApi = retrofitRequest.getRetrofitInstance().create(AccountAPI.class);
        container_layout = findViewById(R.id.fragment_container);
        //check login status from sharedPreference
        // when get false
        getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, new LoginFragment())
                    .commit();

        setSupportActionBar(toolbar);
    }

    @Override
    public void register() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new RegisterFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void login() {
        Intent intent = new Intent(MainActivity.this, TourActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        MainActivity.this.finish();
    }

    @Override
    public void signIn() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new LoginFragment())
                .addToBackStack(null)
                .commit();
    }
}
