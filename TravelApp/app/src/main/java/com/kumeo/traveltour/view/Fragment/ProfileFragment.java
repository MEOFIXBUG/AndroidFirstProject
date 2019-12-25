package com.kumeo.traveltour.view.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import com.facebook.CallbackManager;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.kumeo.traveltour.R;
import com.kumeo.traveltour.UserInfo;
import com.kumeo.traveltour.extras.MyApplication;
import com.kumeo.traveltour.response.ActiveResponse;
import com.kumeo.traveltour.retrofit.Service.User.UserAPI;
import com.kumeo.traveltour.retrofit.retrofitRequest;
import com.kumeo.traveltour.view.Activity.CreateTourActivity;
import com.kumeo.traveltour.view.Activity.MainActivity;
import com.kumeo.traveltour.view.Activity.SplashActivity;
import com.kumeo.traveltour.view.Activity.TourActivity;
import com.kumeo.traveltour.view.Activity.TourMapsActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.core.content.ContextCompat.getSystemService;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        String header =  SplashActivity.appPreference.getToken();
        UserAPI user;
        user = retrofitRequest.getRetrofitInstance().create(UserAPI.class);
        Call<UserInfo> info = user.myInfo(header);
        info.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if (response.isSuccessful()){
                    TextView fullName = view.findViewById(R.id.txtFullName);
                    TextView email = view.findViewById(R.id.emailPro);
                    TextView phone = view.findViewById(R.id.phonePro);
                    TextView address = view.findViewById(R.id.addPro);
                    TextView gender = view.findViewById(R.id.genPro);
                    TextView dob = view.findViewById(R.id.dobPro);
                    TextView emailVeri = view.findViewById(R.id.emailVeri);
                    TextView phoneVeri = view.findViewById(R.id.phoneVeri);

                    Button btnEmailVeri = view.findViewById(R.id.emailVeriBtn);

                    fullName.setText(""+response.body().getFullName());
                    email.setText("Email : "+response.body().getEmail());
                    phone.setText("SDT : "+response.body().getPhone());
                    address.setText("Địa chỉ : "+response.body().getAddress());
                    if (response.body().getGender()!=1)
                    {
                        gender.setText("Giới tính : Nam");
                    }
                    else {
                        gender.setText("Giới tính : Nữ");}
                    dob.setText("Ngày Sinh : "+response.body().getDob());
                    SplashActivity.appPreference.showToast(SplashActivity.appPreference.getEmailVerified());
                    if (SplashActivity.appPreference.getEmailVerified().compareTo("true")==0)
                    {
                        emailVeri.setText("Xác thực email : Đã xác thực");
                        btnEmailVeri.setVisibility(View.GONE);
                    }
                    else
                    {
                        emailVeri.setText("Xác thực email : Chưa xác thực");
                        btnEmailVeri.setVisibility(View.GONE);
                    }
                    if (response.body().isPhoneVerified()== true)
                    {
                        phoneVeri.setText("Xác thực SDT : Đã xác thực");
                        btnEmailVeri.setVisibility(View.GONE);
                    }
                    else
                    {
                        phoneVeri.setText("Xác thực SDT : Chưa xác thực");
                        btnEmailVeri.setVisibility(View.GONE);
                    }

                    if (response.body().getFullName()== null) fullName.setText("[Chưa cập nhật]");
                    if (response.body().getAddress()== null) address.setText("Địa chỉ : [Chưa cập nhật]");
                    if (response.body().getDob()== null) dob.setText("Ngày Sinh : [Chưa cập nhật]");
                    if (response.body().getEmail()== null) email.setText("Email : [Chưa cập nhật]");
                    if (response.body().getPhone()== null) phone.setText("SDT : [Chưa cập nhật]");
                }
            }
            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
            }
        });
        //initialization(view);
        //getMyTrips();
        Button signOutBtn = view.findViewById(R.id.signOutBtn);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SplashActivity.appPreference.setToken("");
                SplashActivity.appPreference.setLoginStatus(false);
                SplashActivity.appPreference.showToast(("Logout successful"));//oki
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        Button verifyEmailbtn = view.findViewById(R.id.emailVeriBtn);
        verifyEmailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<ActiveResponse> sendActive = user.sendActive(Integer.parseInt(SplashActivity.appPreference.getUserID()),"email");
                sendActive.enqueue(new Callback<ActiveResponse>() {
                    @Override
                    public void onResponse(Call<ActiveResponse> call, Response<ActiveResponse> response) {
                        if (response.isSuccessful()){
                            SplashActivity.appPreference.showToast("Send active request succesfull");
                            VerificationFragment nextFrag= new VerificationFragment();
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                                    .addToBackStack(null)
                                    .commit();
                        }
                    }
                    @Override
                    public void onFailure(Call<ActiveResponse> call, Throwable t) {
                        SplashActivity.appPreference.showToast("Send active request failed. Try again later");
                    }
                });

            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        //void onFragmentInteraction(Uri uri);
    }
}
