package com.kumeo.traveltour.view.Fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.kumeo.traveltour.R;
import com.kumeo.traveltour.model.Account;
import com.kumeo.traveltour.response.RegisterResponse;
import com.kumeo.traveltour.retrofit.Service.Account.AccountAPI;
import com.kumeo.traveltour.retrofit.Service.AccountInterface;
import com.kumeo.traveltour.retrofit.retrofitRequest;
import com.kumeo.traveltour.view.Activity.MainActivity;
import com.kumeo.traveltour.view.Activity.SplashActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterFragment extends Fragment {

    private EditText nameInput, emailInput, phoneInput, passwordInput;
    Button regBtn;
    private TextView linkLogin;
    private AccountInterface registerFromActivityListener;
    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_register, container, false);
        nameInput = view.findViewById(R.id.usrName);
        emailInput = view.findViewById(R.id.Email);
        phoneInput = view.findViewById(R.id.PhoneNumber);
        passwordInput = view.findViewById(R.id.usrPass);
        regBtn = view.findViewById(R.id.register);
        linkLogin=view.findViewById(R.id.linkLogin);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
                Log.e("reg button", "clicked");
            }
        });
        linkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerFromActivityListener.signIn();
            }
        });
        return view;
    }
    public void registerUser() {
        String name = nameInput.getText().toString();
        String email = emailInput.getText().toString();
        String phone = phoneInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (TextUtils.isEmpty(name)){
            SplashActivity.appPreference.showToast("Your name is required.");
        } else if (TextUtils.isEmpty(email)){
            SplashActivity.appPreference.showToast("Your email is required.");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            SplashActivity.appPreference.showToast("Invalid email");
        } else if (TextUtils.isEmpty(password)){
            SplashActivity.appPreference.showToast("Password required");
        } else if (password.length() < 6){
            SplashActivity.appPreference.showToast("Create a password at least 6 characters long.");
        }
        else {
            Account user =new Account();
            user.setEmail(email);
            user.setPassword(password);
            user.setPhone(phone);
            user.setFullName(name);

            //AccountAPI accountApi = retrofitRequest.getRetrofitInstance().create(AccountAPI.class);
            Call<Account> userCall =  MainActivity.accountApi.register(user);
            userCall.enqueue(new Callback<Account>() {
                @Override
                public void onResponse(Call<Account> call, Response<Account> response) {
                    if (response.isSuccessful()){
                        SplashActivity.appPreference.showToast("Registered Successfully");
                        registerFromActivityListener.signIn();

                    } else {
                        SplashActivity.appPreference.showToast("Oops! something went wrong.");
                        //registerFromActivityListener.signIn();

                    }
                }

                @Override
                public void onFailure(Call<Account> call, Throwable t) {
                }
            });
        }

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        registerFromActivityListener = (AccountInterface) activity;
    }
}
