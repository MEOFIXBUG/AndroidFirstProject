package com.kumeo.traveltour.view.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kumeo.traveltour.R;
import com.kumeo.traveltour.model.OTPreq;
import com.kumeo.traveltour.model.OTPres;
import com.kumeo.traveltour.response.ActiveResultResponse;
import com.kumeo.traveltour.response.RecoveryResponse;
import com.kumeo.traveltour.retrofit.Service.AccountInterface;
import com.kumeo.traveltour.retrofit.Service.User.UserAPI;
import com.kumeo.traveltour.retrofit.retrofitRequest;
import com.kumeo.traveltour.view.Activity.PinEntryEditText;
import com.kumeo.traveltour.view.Activity.SplashActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecoveryPassOTPFragment extends Fragment {
    //private static final String ARG_PARAM1 = "param1";
   // private static final String ARG_PARAM2 = "param2";

   // private static final String TAG = VerificationFragment.class.getSimpleName();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button recoBtn;
    private  EditText ID;
    private  EditText NewPass;
    private String OTP;
    private AccountInterface registerFromActivityListener;
    public RecoveryPassOTPFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
/*    public static VerificationFragment newInstance(String param1, String param2) {
        VerificationFragment fragment = new VerificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           // mParam1 = getArguments().getString(ARG_PARAM1);
           // mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
    // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_recovery_pass, container, false);
        final PinEntryEditText txtPinEntry = (PinEntryEditText) view.findViewById(R.id.inputOTP);
    ID = view.findViewById(R.id.idEdit);
    NewPass = view.findViewById(R.id.newPass);
    recoBtn =  view.findViewById(R.id.sendOTP);
    recoBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ID.getText().toString().compareTo("") == 0 || OTP.compareTo("") == 0) {
                SplashActivity.appPreference.showToast("Input ID and OTP");
            }
            else if (NewPass.getText().toString().length()<6)
            {
                SplashActivity.appPreference.showToast("Password must longer than 6 characters");
            }
            else {
                UserAPI user = retrofitRequest.getRetrofitInstance().create(UserAPI.class);
                OTPres otpres = new OTPres();
                otpres.setUserId(Integer.parseInt(ID.getText().toString()));
                otpres.setVerifyCode(OTP);//vai
                otpres.setNewPassword(NewPass.getText().toString());
                Call<RecoveryResponse> verifyOTP = user.verifyOTP(otpres);
                verifyOTP.enqueue(new Callback<RecoveryResponse>() {
                    @Override
                    public void onResponse(Call<RecoveryResponse> call, Response<RecoveryResponse> response) {
                        if (response.isSuccessful() && response.code() == 200) {
                            if (response.body().getMessage().compareTo("Successful") == 0) {
                                SplashActivity.appPreference.showToast("Successful. Please login");
                                //registerFromActivityListener.signIn();
                                LoginFragment nextFrag = new LoginFragment();
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_container, nextFrag, "LoginFrag")
                                        .addToBackStack(null)
                                        .commit();
                            }
                        } else {
                            txtPinEntry.setText(null);
                            SplashActivity.appPreference.showToast("Verify code is invalid or expired");
                        }
                    }

                    @Override
                    public void onFailure(Call<RecoveryResponse> call, Throwable t) {
                    }
                });
            }
        }
    });
    txtPinEntry.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().length()==6) {
                OTP = s.toString();
            }

        }
    });
    return view;
}



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
        //Activity activity = (Activity) context;
        //createTourFromTravelTour = (TourInterface) activity;
        //Activity activity = (Activity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }
}
