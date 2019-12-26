package com.ygaps.travelapp.view.Activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.FrameLayout;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.retrofit.Service.Account.AccountAPI;
import com.ygaps.travelapp.retrofit.Service.AccountInterface;
import com.ygaps.travelapp.retrofit.retrofitRequest;
import com.ygaps.travelapp.view.Fragment.LoginFragment;
import com.ygaps.travelapp.view.Fragment.RegisterFragment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
        try {
            PackageManager z= getPackageManager();
            PackageInfo info = z.getPackageInfo(
                    "com.ygaps.travelapp",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                //Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

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
