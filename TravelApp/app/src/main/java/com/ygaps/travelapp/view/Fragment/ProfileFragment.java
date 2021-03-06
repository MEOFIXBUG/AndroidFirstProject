package com.ygaps.travelapp.view.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.model.UserInfo;
import com.ygaps.travelapp.model.PasswordUpdate;
import com.ygaps.travelapp.model.RegistrationFirebase;
import com.ygaps.travelapp.model.removeFireBase;
import com.ygaps.travelapp.response.ActiveResponse;
import com.ygaps.travelapp.response.RecoveryResponse;
import com.ygaps.travelapp.retrofit.Service.User.UserAPI;
import com.ygaps.travelapp.retrofit.retrofitRequest;
import com.ygaps.travelapp.view.Activity.MainActivity;
import com.ygaps.travelapp.view.Activity.SplashActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private ImageButton editPro;
    private Button editOk;
    private Button editCancel;
    private Button sendregFB;
    private Button sendunregFB;
    private Button btnChangepass;
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
        editPro = view.findViewById(R.id.btnEditProfile);
        editOk = view.findViewById(R.id.btnEditDone);
        editCancel = view.findViewById(R.id.btnCancelEdit);
        //
        sendregFB = view.findViewById(R.id.regFB);
        sendunregFB = view.findViewById(R.id.unregFB);
        if (SplashActivity.appPreference.getFirebaseSTT()==false)
        {
            sendunregFB.setVisibility(View.GONE);
            sendregFB.setVisibility(View.VISIBLE);
        }
        else
        {
            sendregFB.setVisibility(View.GONE);
            sendunregFB.setVisibility(View.VISIBLE);
        }
        sendregFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                                String androidId = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                                UserAPI user = retrofitRequest.getRetrofitInstance().create(UserAPI.class);
                                RegistrationFirebase reg = new RegistrationFirebase();
                                reg.setFcmToken(token);
                                reg.setDeviceId(androidId);
                                reg.setPlatform(1);
                                reg.setAppVersion("1.0");
                                Call<RecoveryResponse> regFB = user.regFirebase(SplashActivity.appPreference.getToken(),reg);
                                regFB.enqueue(new Callback<RecoveryResponse>() {
                                    @Override
                                    public void onResponse(Call<RecoveryResponse> call, Response<RecoveryResponse> response) {
                                        if (response.isSuccessful()) {
                                            if (response.code() == 200) {
                                                SplashActivity.appPreference.showToast("Đăng ký nhận tin thành công");
                                                SplashActivity.appPreference.setFirebaseSTT(true);
                                                sendregFB.setVisibility(View.GONE);
                                                sendunregFB.setVisibility(View.VISIBLE);
                                            } else if (response.code() == 404) {
                                            } else {
                                            }
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<RecoveryResponse> call, Throwable t) {
                                    }
                                });
                            }
                        });

            }
        });
        //
        sendunregFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SplashActivity.appPreference.setFirebaseSTT(false);
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
                                String androidId = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                                UserAPI user = retrofitRequest.getRetrofitInstance().create(UserAPI.class);
                                removeFireBase reg = new removeFireBase();
                                reg.setFcmToken(token);
                                reg.setDeviceId(androidId);
                                Call<RecoveryResponse> unregFB = user.unregFirebase(SplashActivity.appPreference.getToken(),reg);
                                unregFB.enqueue(new Callback<RecoveryResponse>() {
                                    @Override
                                    public void onResponse(Call<RecoveryResponse> call, Response<RecoveryResponse> response) {
                                        if (response.isSuccessful()) {
                                            if (response.code() == 200) {
                                                SplashActivity.appPreference.showToast("Hủy đăng ký nhận tin thành công");
                                                sendunregFB.setVisibility(View.GONE);
                                                sendregFB.setVisibility(View.VISIBLE);
                                            } else if (response.code() == 404) {
                                            } else {
                                            }
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<RecoveryResponse> call, Throwable t) {
                                    }
                                });

                            }
                        });

            }
        });
        //
        TextView fullName = view.findViewById(R.id.txtFullName);
        TextView email = view.findViewById(R.id.emailPro);
        TextView phone = view.findViewById(R.id.phonePro);
        TextView address = view.findViewById(R.id.addPro);
        TextView gender = view.findViewById(R.id.genPro);
        TextView dob = view.findViewById(R.id.dobPro);
        TextView emailVeri = view.findViewById(R.id.emailVeri);
        TextView phoneVeri = view.findViewById(R.id.phoneVeri);
        Button btnEmailVeri = view.findViewById(R.id.emailVeriBtn);
        EditText nameEdit = view.findViewById(R.id.txtFullNameEdit);
        EditText emailEdit = view.findViewById(R.id.emailProEdit);
        EditText phoneEdit = view.findViewById(R.id.phoneProEdit);
        EditText addEdit = view.findViewById(R.id.addProEdit);
        EditText genEdit = view.findViewById(R.id.genProEdit);
        EditText dobEdit = view.findViewById(R.id.dobProEdit);
        //

        EditText curpas = view.findViewById(R.id.currentpass);
        EditText newpas1 = view.findViewById(R.id.newpass);
        EditText newpas2 = view.findViewById(R.id.newpass2);
        //
        editOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean condition = true;
                    String newName = nameEdit.getText().toString();
                    String newEmail = emailEdit.getText().toString();
                    String newPhone = phoneEdit.getText().toString();
                    String newDob = dobEdit.getText().toString();
                    Integer newGen = 1;
                if (newName.compareTo("")==0)
                {
                    SplashActivity.appPreference.showToast("Input Name");
                    condition=false;
                }
                if (newEmail.compareTo("")==0 || !(android.util.Patterns.EMAIL_ADDRESS.matcher(newEmail).matches())) {
                    SplashActivity.appPreference.showToast("Email Invalid");
                    condition=false;
                }
                 if (newPhone.compareTo("")==0 ||!android.util.Patterns.PHONE.matcher(newPhone).matches()) {
                     SplashActivity.appPreference.showToast("Phone Invalid");
                     condition = false;
                 }
                if (genEdit.getText().toString().compareTo("")==0 && (genEdit.getText().toString().compareTo("Nam")!=0 || genEdit.getText().toString().compareTo("Nữ")!=0))
                {
                    SplashActivity.appPreference.showToast("Invalid Gender");
                    SplashActivity.appPreference.showToast("Giới tính Nam hoặc Nữ");
                    condition=false;
                }
                else
                {
                    if (genEdit.getText().toString().compareTo("Nam")==0)
                    {
                        newGen = 1;
                    }
                    else{
                        newGen = 0;
                    }
                }
                if (newDob.compareTo("")==0)
                {
                    SplashActivity.appPreference.showToast("Input Date of Birth");
                    condition=false;
                }
                if (condition) {

                    SplashActivity.appPreference.showToast("Edit Done");
                    UserInfo newInfo = new UserInfo(newName,newEmail,newPhone,newDob,newGen);
                    String header = SplashActivity.appPreference.getToken();
                    UserAPI user = retrofitRequest.getRetrofitInstance().create(UserAPI.class);
                    Call<RecoveryResponse> info = user.updateInfo(header,newInfo);
                    info.enqueue(new Callback<RecoveryResponse>() {
                        @Override
                        public void onResponse(Call<RecoveryResponse> call, Response<RecoveryResponse> response) {
                            if (response.isSuccessful() && response.code()==200) {
                                    SplashActivity.appPreference.showToast("Update info successful");
                                ProfileFragment nextFrag= new ProfileFragment();
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_container, nextFrag, "reload")
                                        .addToBackStack(null)
                                        .commit();
                            }
                        }

                        @Override
                        public void onFailure(Call<RecoveryResponse> call, Throwable t) {
                        }
                    });


                    editCancel.setVisibility(View.GONE);
                    editOk.setVisibility(View.GONE);
                    editPro.setVisibility(View.VISIBLE);

                    nameEdit.setVisibility(View.GONE);
                    emailEdit.setVisibility(View.GONE);
                    phoneEdit.setVisibility(View.GONE);
                    addEdit.setVisibility(View.GONE);
                    dobEdit.setVisibility(View.GONE);
                    genEdit.setVisibility(View.GONE);

                    email.setVisibility(View.VISIBLE);
                    phone.setVisibility(View.VISIBLE);
                    dob.setVisibility(View.VISIBLE);
                    gender.setVisibility(View.VISIBLE);
                    address.setVisibility(View.VISIBLE);
                }
            }
        });
        editCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SplashActivity.appPreference.showToast("Edit Canceled");
                editCancel.setVisibility(View.GONE);
                editOk.setVisibility(View.GONE);
                editPro.setVisibility(View.VISIBLE);

                nameEdit.setVisibility(View.GONE);
                emailEdit.setVisibility(View.GONE);
                phoneEdit.setVisibility(View.GONE);
                addEdit.setVisibility(View.GONE);
                dobEdit.setVisibility(View.GONE);
                genEdit.setVisibility(View.GONE);

                fullName.setVisibility(View.VISIBLE);
                email.setVisibility(View.VISIBLE);
                phone.setVisibility(View.VISIBLE);
                dob.setVisibility(View.VISIBLE);
                gender.setVisibility(View.VISIBLE);
                address.setVisibility(View.VISIBLE);
            }
        });
        editPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editCancel.setVisibility(View.VISIBLE);
                editOk.setVisibility(View.VISIBLE);
                editPro.setVisibility(View.GONE);
                nameEdit.setVisibility(View.VISIBLE);
                emailEdit.setVisibility(View.VISIBLE);
                phoneEdit.setVisibility(View.VISIBLE);
                addEdit.setVisibility(View.VISIBLE);
                dobEdit.setVisibility(View.VISIBLE);
                genEdit.setVisibility(View.VISIBLE);

                fullName.setVisibility(View.GONE);
                email.setVisibility(View.GONE);
                phone.setVisibility(View.GONE);
                dob.setVisibility(View.GONE);
                gender.setVisibility(View.GONE);
                address.setVisibility(View.GONE);
            }
        });
        String header =  SplashActivity.appPreference.getToken();
        UserAPI user= retrofitRequest.getRetrofitInstance().create(UserAPI.class);
        Call<UserInfo> info = user.myInfo(header);
        info.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if (response.isSuccessful()){


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
                        btnEmailVeri.setVisibility(View.VISIBLE);
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
        //Changepass
        btnChangepass = view.findViewById(R.id.changepassBtn);
        btnChangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String curpasStr = curpas.getText().toString();
                String newpas1Str = newpas1.getText().toString();
                String newpas2Str = newpas2.getText().toString();
                if (curpasStr.compareTo("")==0 || newpas1Str.compareTo("")==0 || newpas2Str.compareTo("")==0)
                {
                    SplashActivity.appPreference.showToast("Input password");
                }
                else if (newpas1Str.length()<6 || newpas2Str.length()<6)
                {
                    SplashActivity.appPreference.showToast("New password must longer than 6 character");
                }
                else if (newpas1Str.compareTo(newpas2Str)!=0)
                {
                    SplashActivity.appPreference.showToast("Repeat new password not match");
                }
                else
                {
                    PasswordUpdate pu = new PasswordUpdate();
                    pu.setUserId(Integer.parseInt(SplashActivity.appPreference.getUserID()));
                    pu.setCurrentPassword(curpasStr);
                    pu.setNewPassword(newpas1Str);
                    String header = SplashActivity.appPreference.getToken();
                    Call<RecoveryResponse> updatePassword = user.updatePassword(header,pu);
                    updatePassword.enqueue(new Callback<RecoveryResponse>() {
                        @Override
                        public void onResponse(Call<RecoveryResponse> call, Response<RecoveryResponse> response) {
                            if (response.isSuccessful() && response.code()==200){
                                    SplashActivity.appPreference.showToast("Update password successfull");
                                curpas.setText("");
                                newpas1.setText("");
                                newpas2.setText("");
                            }
                            else if (response.code()==400)
                            {
                                SplashActivity.appPreference.showToast("Current password is wrong");
                            }
                            else
                            {
                                SplashActivity.appPreference.showToast("Update password failed (Server Erro)");
                                curpas.setText("");
                                newpas1.setText("");
                                newpas2.setText("");
                            }
                        }
                        @Override
                        public void onFailure(Call<RecoveryResponse> call, Throwable t) {
                            SplashActivity.appPreference.showToast("Send request failed. Try again later");
                        }
                    });
                }

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
