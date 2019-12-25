package com.kumeo.traveltour.view.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kumeo.traveltour.R;
import com.kumeo.traveltour.UserInfo;
import com.kumeo.traveltour.response.ActiveResultResponse;
import com.kumeo.traveltour.retrofit.Service.User.UserAPI;
import com.kumeo.traveltour.retrofit.retrofitRequest;
import com.kumeo.traveltour.view.Activity.MainActivity;
import com.kumeo.traveltour.view.Activity.PinEntryEditText;
import com.kumeo.traveltour.view.Activity.SplashActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationFragment extends Fragment {
    //private static final String ARG_PARAM1 = "param1";
   // private static final String ARG_PARAM2 = "param2";

   // private static final String TAG = VerificationFragment.class.getSimpleName();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button clickOk;

    private MyTripFragment.OnFragmentInteractionListener mListener;
    private FloatingActionButton add_fab;

    public VerificationFragment() {
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
        View view = inflater.inflate(R.layout.fragment_verification, container, false);
        final PinEntryEditText txtPinEntry = (PinEntryEditText) view.findViewById(R.id.inputCode);
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
                    txtPinEntry.setText(null);
                    UserAPI user;
                    user = retrofitRequest.getRetrofitInstance().create(UserAPI.class);
                    Call<ActiveResultResponse> isActive = user.getActiveStatus(Integer.parseInt(SplashActivity.appPreference.getUserID()),"email",s.toString());
                    isActive.enqueue(new Callback<ActiveResultResponse>() {
                        @Override
                        public void onResponse(Call<ActiveResultResponse> call, Response<ActiveResultResponse> response) {
                            if (response.isSuccessful() && response.code() == 200){
                                if (response.body().getSuccess()==true)
                                {
                                    SplashActivity.appPreference.showToast("Verified");
                                    SplashActivity.appPreference.setEmailVerified(true);
                                    ProfileFragment nextFrag= new ProfileFragment();
                                    getActivity().getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.fragment_container, nextFrag, "tag2")
                                            .addToBackStack(null)
                                            .commit();
                                }
                            }
                            else
                            {
                                SplashActivity.appPreference.showToast("Verify code is invalid or expired");
                            }
                        }
                        @Override
                        public void onFailure(Call<ActiveResultResponse> call, Throwable t) {
                        }
                    });
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
