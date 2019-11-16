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

import androidx.fragment.app.Fragment;

import com.kumeo.traveltour.R;
import com.kumeo.traveltour.model.Account;
import com.kumeo.traveltour.response.RegisterResponse;
import com.kumeo.traveltour.retrofit.Service.AccountInterface;
import com.kumeo.traveltour.view.Activity.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterFragment extends Fragment {

    private EditText nameInput, emailInput, phoneInput, passwordInput;
    Button regBtn;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_register, container, false);
        nameInput = view.findViewById(R.id.nameInput);
        emailInput = view.findViewById(R.id.emailInput);
        phoneInput = view.findViewById(R.id.phoneInput);
        passwordInput = view.findViewById(R.id.passwordInput);
        regBtn = view.findViewById(R.id.btnRegister);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
                Log.e("reg button", "clicked");
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
            MainActivity.appPreference.showToast("Your name is required.");
        } else if (TextUtils.isEmpty(email)){
            MainActivity.appPreference.showToast("Your email is required.");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            MainActivity.appPreference.showToast("Invalid email");
        } else if (TextUtils.isEmpty(password)){
            MainActivity.appPreference.showToast("Password required");
        } else if (password.length() < 6){
            MainActivity.appPreference.showToast("Create a password at least 6 characters long.");
        }
        else {
            Account user =new Account();
            user.setEmail(email);
            user.setPassword(password);
            user.setPhone(phone);
            user.setFullName(name);
            Call<RegisterResponse> userCall = MainActivity.accountApi.register(user);
            userCall.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    if (response.isSuccessful()){
                        MainActivity.appPreference.showToast("Registered Successfully");


                    } else {
                        MainActivity.appPreference.showToast("Oops! something went wrong.");

                    }
                }

                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                }
            });
        }

    }

}
