package com.kumeo.traveltour.view.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.kumeo.traveltour.R;
import com.kumeo.traveltour.model.Account;
import com.kumeo.traveltour.response.LoginResponse;
import com.kumeo.traveltour.retrofit.Service.AccountInterface;
import com.kumeo.traveltour.view.Activity.MainActivity;
import com.kumeo.traveltour.view.Activity.SplashActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kumeo.traveltour.view.Activity.MainActivity.accountApi;

public class LoginFragment extends Fragment {

    public LoginFragment() {
        // Required empty public constructor
    }
    private AccountInterface loginFromActivityListener;
    private Button registerBtn;

    private EditText emailInput, passwordInput;
    private Button loginBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_login, container, false);

        // for login
        emailInput = view.findViewById(R.id.inputUser);
        passwordInput = view.findViewById(R.id.inputPassword);
        loginBtn = view.findViewById(R.id.login_signIn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        registerBtn = view.findViewById(R.id.signup);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginFromActivityListener.register();
            }
        });
        return view;
    }
    private void loginUser() {
        String Email = emailInput.getText().toString();
        String Password = passwordInput.getText().toString();

        if (TextUtils.isEmpty(Email)){
            SplashActivity.appPreference.showToast("Your email is required.");
        } else if (TextUtils.isEmpty(Password)){
            SplashActivity.appPreference.showToast("Password required");
        } else {
            Account user =new Account();
            user.setEmailPhone(Email);
            user.setPassword(Password);
            Call<LoginResponse> call = accountApi.login(user);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()){
                        SplashActivity.appPreference.showToast("Login Successful");
                        SplashActivity.appPreference.setToken(response.body().getUserId());
                        SplashActivity.appPreference.setToken(response.body().getToken());
                        SplashActivity.appPreference.setLoginStatus(true); // set login status in sharedPreference
                        loginFromActivityListener.login();

                    } else {
                        SplashActivity.appPreference.showToast("Login Failed");
                        emailInput.setText("");
                        passwordInput.setText("");
                    }
                }
                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                }
            });
         }
    } //ending loginUser()
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        loginFromActivityListener = (AccountInterface) activity;
    }
}
