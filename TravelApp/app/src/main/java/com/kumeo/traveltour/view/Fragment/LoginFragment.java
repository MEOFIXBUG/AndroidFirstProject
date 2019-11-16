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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kumeo.traveltour.view.Activity.MainActivity.accountApi;

public class LoginFragment extends Fragment {

    public LoginFragment() {
        // Required empty public constructor
    }
    private AccountInterface loginFromActivityListener;
    private TextView registerTV;

    private EditText emailInput, passwordInput;
    private Button loginBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_login, container, false);

        // for login
        emailInput = view.findViewById(R.id.emailInput);
        passwordInput = view.findViewById(R.id.passwordInput);
        loginBtn = view.findViewById(R.id.btnLogin);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        registerTV = view.findViewById(R.id.linkRegister);
        registerTV.setOnClickListener(new View.OnClickListener() {
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
            MainActivity.appPreference.showToast("Your email is required.");
        } else if (TextUtils.isEmpty(Password)){
            MainActivity.appPreference.showToast("Password required");
        } else {
//            Call<User> userCall = MainActivity.serviceApi.doLogin(Email, Password);
//            userCall.enqueue(new Callback<User>() {
//                @Override
//                public void onResponse(Call<User> call, Response<User> response) {
//                    if (response.body().getResponse().equals("data")){
//                        MainActivity.appPreference.setLoginStatus(true); // set login status in sharedPreference
//                        loginFromActivityListener.login(
//                                response.body().getName(),
//                                response.body().getEmail(),
//                                response.body().getCreated_at());
//                    } else if (response.body().getResponse().equals("login_failed")){
//                        MainActivity.appPreference.showToast("Error. Login Failed");
//                        emailInput.setText("");
//                        passwordInput.setText("");
//                    }
//                }
//                @Override
//                public void onFailure(Call<User> call, Throwable t) {
//                }
//            });
            Account user =new Account();
            user.setEmailPhone(Email);
            user.setPassword(Password);
            Call<LoginResponse> call = accountApi.login(user);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()){
                        loginFromActivityListener.login();
                        MainActivity.appPreference.setToken(response.body().getUserId());
                        MainActivity.appPreference.setToken(response.body().getToken());
                        MainActivity.appPreference.setLoginStatus(true); // set login status in sharedPreference

                    } else {
                        MainActivity.appPreference.showToast("Error. Login Failed");
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
