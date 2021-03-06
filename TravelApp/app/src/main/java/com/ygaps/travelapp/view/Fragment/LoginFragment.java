package com.ygaps.travelapp.view.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.ygaps.travelapp.BuildConfig;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.model.Account;
import com.ygaps.travelapp.model.RegistrationFirebase;
import com.ygaps.travelapp.model.facebookToken;
import com.ygaps.travelapp.response.LoginResponse;
import com.ygaps.travelapp.response.RecoveryResponse;
import com.ygaps.travelapp.retrofit.Service.AccountInterface;
import com.ygaps.travelapp.retrofit.Service.User.UserAPI;
import com.ygaps.travelapp.retrofit.retrofitRequest;
import com.ygaps.travelapp.view.Activity.SplashActivity;

import com.google.android.gms.tasks.Task;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ygaps.travelapp.view.Activity.MainActivity.accountApi;

public class LoginFragment extends Fragment {

    public LoginFragment() {
        // Required empty public constructor
    }
    private AccountInterface loginFromActivityListener;
    private Button registerBtn;

    private EditText emailInput, passwordInput;
    private Button loginBtn;

    private LoginButton btnFB;
    final CallbackManager callbackManager = CallbackManager.Factory.create();
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

        //for login with facebook
        btnFB=view.findViewById(R.id.login_button);
        btnFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFacebook(btnFB);
            }
        });
        //forgor passs
        Button forgotbtn = view.findViewById(R.id.forgotpass);
        forgotbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgotPasswordFragment nextFrag= new ForgotPasswordFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        //for register
        registerBtn = view.findViewById(R.id.sigup_reg);
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
                        SplashActivity.appPreference.setUserID(response.body().getUserId());
                        SplashActivity.appPreference.setToken(response.body().getToken());
                        //co token roi ne
                        SplashActivity.appPreference.setEmailVerified(response.body().getEmailVerified());
                        SplashActivity.appPreference.setLoginStatus(true); // set login status in sharedPreference
                        //Firebase




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

    public void loginFacebook(final LoginButton login)
    {
        //facebookSetting();
        // Callback registration
        FacebookSdk.sdkInitialize(getActivity());
        login.setFragment(this);
        login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                //login.setVisibility(View.INVISIBLE);
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                Log.d("FACEBOOK",accessToken.getToken());
                SplashActivity.appPreference.showToast("get access token facebook Successful");

               facebookToken userfb =new facebookToken();
                userfb.setAccessToken(accessToken.getToken());
                //lay accessToken de gui request len server
                Call<LoginResponse> call = accountApi.loginByFacebook(userfb);

                call.enqueue(new Callback<LoginResponse>() {

                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()){



                            SplashActivity.appPreference.showToast("Login FB Successful");
                            SplashActivity.appPreference.setToken(response.body().getUserId());
                            SplashActivity.appPreference.setToken(response.body().getToken());
                            SplashActivity.appPreference.setLoginStatus(true); // set login status in sharedPreference
                            FirebaseInstanceId.getInstance().getInstanceId()
                                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                            if (!task.isSuccessful()) {

                                                SplashActivity.appPreference.showToast("Failed");
                                                return;
                                            }
                                            // Get new Instance ID token
                                            String token = task.getResult().getToken();
                                            SplashActivity.appPreference.setFirebaseToken(token);

                                        }
                                    });
                            loginFromActivityListener.login();

                        } else {
                            SplashActivity.appPreference.showToast("Login FB Failed");
                            emailInput.setText("");
                            passwordInput.setText("");
                        }
                    }
                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                    }
                });
            }

            @Override
            public void onCancel() {
                // App code
                SplashActivity.appPreference.showToast("CANCEL");
            }

            @Override
            public void onError(FacebookException error) {
                SplashActivity.appPreference.showToast(error.toString());
                Log.d("ERROR", error.toString());

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Log.d("FRAGMENT", "onResultCalled");
    }
}
