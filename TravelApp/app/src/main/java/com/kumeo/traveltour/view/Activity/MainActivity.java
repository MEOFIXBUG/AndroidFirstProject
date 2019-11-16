package com.kumeo.traveltour.view.Activity;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.view.View;
import android.widget.FrameLayout;

import com.kumeo.traveltour.R;
import com.kumeo.traveltour.extras.MyApplication;
import com.kumeo.traveltour.model.Account;
import com.kumeo.traveltour.retrofit.Service.Account.AccountService;
import com.kumeo.traveltour.retrofit.Service.AccountInterface;
import com.kumeo.traveltour.retrofit.retrofitRequest;
import com.kumeo.traveltour.view.Fragment.LoginFragment;
import com.kumeo.traveltour.view.Fragment.RegisterFragment;
import com.kumeo.traveltour.viewmodel.AccountViewModel;

public class MainActivity extends AppCompatActivity implements AccountInterface {

    public static MyApplication appPreference;
   // public static AccountViewModel accountViewModel;
    FrameLayout container_layout;
    public static AccountService accountApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //accountViewModel=ViewModelProviders.of(this).get(AccountViewModel.class);
        appPreference = new MyApplication(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        accountApi = retrofitRequest.getRetrofitInstance().create(AccountService.class);
        container_layout = findViewById(R.id.fragment_container);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new LoginFragment())
                .commit();
        setSupportActionBar(toolbar);
    }

    @Override
    public void register() {

    }

    @Override
    public void login() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new RegisterFragment())
                .commit();
    }

    @Override
    public void logout() {

    }
}
