package com.ygaps.travelapp.view.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.model.OTPreq;
import com.ygaps.travelapp.response.RecoveryOTP;
import com.ygaps.travelapp.retrofit.Service.AccountInterface;
import com.ygaps.travelapp.retrofit.Service.User.UserAPI;
import com.ygaps.travelapp.retrofit.retrofitRequest;
import com.ygaps.travelapp.view.Activity.SplashActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForgotPasswordFragment extends Fragment {
    private AccountInterface registerFromActivityListener;
    private MyTripFragment.OnFragmentInteractionListener mListener;

    private EditText inputText;
    public ForgotPasswordFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot, container, false);
        inputText = view.findViewById(R.id.emaiphoneEdit);
        Button forgot = view.findViewById(R.id.forgotBtn);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = inputText.getText().toString();
                String type = "";
                if (TextUtils.isEmpty(temp)) {
                    SplashActivity.appPreference.showToast("You must input email or phone");
                }
                else {
                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(temp).matches()) {
                        type = "email";
                    } else if (android.util.Patterns.PHONE.matcher(temp).matches()) {
                        type = "phone";
                    } else {
                        type = "";
                    }
                    if (type.compareTo("") != 0) {
                        UserAPI user = retrofitRequest.getRetrofitInstance().create(UserAPI.class);
                        OTPreq optreq = new OTPreq();
                        optreq.setType(type);
                        optreq.setValue(temp);
                        Call<RecoveryOTP> requestOTP = user.requestOTP(optreq);
                        requestOTP.enqueue(new Callback<RecoveryOTP>() {
                            @Override
                            public void onResponse(Call<RecoveryOTP> call, Response<RecoveryOTP> response) {
                                if (response.isSuccessful()) {
                                    if (response.code() == 200) {
                                        SplashActivity.appPreference.showToast("Request OTP OK");
                                        RecoveryPassOTPFragment nextFrag = new RecoveryPassOTPFragment();
                                        getActivity().getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.fragment_container, nextFrag, "recoveryinput")
                                                .addToBackStack(null)
                                                .commit();
                                    } else if (response.code() == 404) {
                                        SplashActivity.appPreference.showToast("Email/Phone not found");
                                    } else {
                                        SplashActivity.appPreference.showToast("Failed to request OTP");
                                    }
                                }
                            }
                            @Override
                            public void onFailure(Call<RecoveryOTP> call, Throwable t) {
                            }
                        });
                    }
                    else
                    {
                        SplashActivity.appPreference.showToast("Invalid email/phone");
                    }
                }
            }
        });

        Button toLogin = view.findViewById(R.id.toLoginBtn);
        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerFromActivityListener.signIn();
            }
        });
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        registerFromActivityListener = (AccountInterface) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }
}
