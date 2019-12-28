package com.ygaps.travelapp.view.Fragment;

<<<<<<< HEAD
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
=======
import android.content.Context;
>>>>>>> 04e401d6d648dc9feabc307d0acf6b2c6f821560
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ygaps.travelapp.R;
<<<<<<< HEAD
import com.ygaps.travelapp.UserInfo;
import com.ygaps.travelapp.adapter.ItemAdapter;
import com.ygaps.travelapp.adapter.TourAdapter;
import com.ygaps.travelapp.extras.OpenActivity;
import com.ygaps.travelapp.extras.PaginationScrollListener;
import com.ygaps.travelapp.extras.SharePreferenceListStopPoint;
import com.ygaps.travelapp.extras.converter;
import com.ygaps.travelapp.model.Tour;
import com.ygaps.travelapp.model.toInvited;
import com.ygaps.travelapp.repository.UserRepository;
import com.ygaps.travelapp.response.TourInfoResponse;
import com.ygaps.travelapp.response.TourResponse;
import com.ygaps.travelapp.response.UserListRp;
import com.ygaps.travelapp.retrofit.Service.Tour.TourAPI;
import com.ygaps.travelapp.retrofit.retrofitRequest;
import com.ygaps.travelapp.view.Activity.CreateTourActivity;
import com.ygaps.travelapp.view.Activity.DetailTourActivity;
import com.ygaps.travelapp.view.Activity.SplashActivity;
import com.ygaps.travelapp.viewmodel.TourViewModel;
import com.ygaps.travelapp.viewmodel.UserViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.ygaps.travelapp.extras.converter.createDate;
import static com.ygaps.travelapp.view.Activity.DetailTourActivity.tourID;
import static com.ygaps.travelapp.view.Activity.DetailTourActivity.tourViewModel;
import static com.ygaps.travelapp.view.Activity.DetailTourActivity.Editable;
import static java.lang.Integer.parseInt;
=======
>>>>>>> 04e401d6d648dc9feabc307d0acf6b2c6f821560


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InviteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InviteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InviteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public InviteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InviteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InviteFragment newInstance(String param1, String param2) {
        InviteFragment fragment = new InviteFragment();
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
<<<<<<< HEAD
        View view =inflater.inflate(R.layout.fragment_invite, container, false);
        initialization(view);
        chooseDate(etStartDate);
        chooseDate(etEndDate);

        Log.d(TAG,textCountry.getText().toString());
        getUsers(textCountry.getText().toString());

        btnRemoveTour=view.findViewById(R.id.btnRemoveTour);

        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkRequiredField()) {
                    try {
                        Tour tourupdate = makeTourRequest();
                        updateTour(tourupdate);

                        //tro ve fragment stop point
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, new StopPointFragment());
                        transaction.commit();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        btnRemoveTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        Tour tourupdate= null;
                        try {
                            tourupdate = makeTourRequest();
                            tourupdate.setStatus(-1);
                            updateTour(tourupdate);

                            //tro ve home
                            OpenActivity.openHomeActivity(getActivity());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();


            }
        });

        return view;
    }
    public boolean checkRequiredField() {
        if (TextUtils.isEmpty(etTourName.getText())) {
            etTourName.setError("Tour  Name is required");
            return false;
        }
        if (TextUtils.isEmpty(etStartDate.getText())) {
            etStartDate.setError("Start Date is required");
            return false;
        }
        if (TextUtils.isEmpty(etEndDate.getText())) {
            etEndDate.setError("End Date is required");
            return false;
        }
        /*if (TextUtils.isEmpty(etSourceLat.getText())) {
            etSourceLat.setError("Source Latitude is required");
            return false;
        }
        if (TextUtils.isEmpty(etSourceLong.getText())) {
            etSourceLong.setError("Source Longitude is required");
            return false;
        }
        if (TextUtils.isEmpty(etDesLat.getText())) {
            etDesLat.setError("Destination Latitude is required");
            return false;
        }
        if (TextUtils.isEmpty(etDesLong.getText())) {
            etDesLong.setError("Destination Longitude is required");
            return false;
        }*/

        String startDate = etStartDate.getText().toString();
        String endDate=etEndDate.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date strStartDate = null;
        Date strEndDate=null;

        try{
            strStartDate = sdf.parse(startDate);
            strEndDate=sdf.parse(endDate);
        }catch (ParseException e) {
            e.printStackTrace();
        }

        if (strEndDate.getTime() < strStartDate.getTime()) {
            etStartDate.setError("End date must be after start date");
            etEndDate.setError("End date must be after start date");
            return false;
        }

        return true;
    }
    private void initialization(View view) {
        etTourName=view.findViewById(R.id.etTourName);
        etStartDate=view.findViewById(R.id.etStartDate);
        etEndDate=view.findViewById(R.id.etEndDate);
        etAdults=view.findViewById(R.id.etAdults);
        etChilds=view.findViewById(R.id.etChilds);
        etMinCost=view.findViewById(R.id.etMinCost);
        etMaxCost=view.findViewById(R.id.etMaxCost);
        etAvatar=view.findViewById(R.id.etAvatar);
        textCountry =(AutoCompleteTextView)view.findViewById(R.id.uInvite);
        updateAvatar=(Button) view.findViewById(R.id.updateAvatar);
        inviteBtn=(Button) view.findViewById(R.id.btnInvite);
        checkBox = (CheckBox) view.findViewById(R.id.checkbox_id);
        Done =(Button) view.findViewById(R.id.btnDone);
        getTourBaseInfo();
        if(!Editable){
            disableEditText(etTourName);
            disableEditText(etAdults);
            disableEditText(etAvatar);
            disableEditText(etChilds);
            disableEditText(etEndDate);
            disableEditText(etStartDate);
            disableEditText(etMaxCost);
            disableEditText(etMinCost);
            disableEditText(textCountry);
            inviteBtn.setText("JOIN");
            updateAvatar.setVisibility(GONE);
            btnRemoveTour.setVisibility(GONE);
            //Done.setEnabled(false);
            Done.setVisibility(GONE);
            checkBox.setEnabled(false);
        }
        adapterCountries = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,countries);
        textCountry.setAdapter(adapterCountries);

        // Sét đặt số ký tự nhỏ nhất, để cửa sổ gợi ý hiển thị
        textCountry.setThreshold(1);
        textCountry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG,textCountry.getText().toString());
                getUsers(textCountry.getText().toString());
            }
        });
        // adapter
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        //tourViewModel.init(49,1,2);

        inviteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.appPreference.showToast("onclick invite" );
                selectedID=Integer.parseInt(IdList.get(countries.indexOf(textCountry.getText().toString())));
                    Log.d(TAG,""+selectedID);
                    toInvited a= new toInvited();
                    a.setTourId(Long.toString(tourID));
                    if(!Editable){
                        a.setInvitedUserId(UserID);
                        a.setInvited(false);
                    }
                    else{
                        if(selectedID>0){
                        a.setInvitedUserId(Integer.toString(selectedID));
                        a.setInvited(false);
                        }
                    }
                    //tourViewModel.Invite_Join(a);
            }
        });

=======
        return inflater.inflate(R.layout.fragment_invite, container, false);
>>>>>>> 04e401d6d648dc9feabc307d0acf6b2c6f821560
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        void onFragmentInteraction(Uri uri);
    }
}
