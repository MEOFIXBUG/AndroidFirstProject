package com.ygaps.travelapp.view.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.extras.ReadExcel;
import com.ygaps.travelapp.extras.SharePreferenceListStopPoint;
import com.ygaps.travelapp.model.Tour;
import com.ygaps.travelapp.response.ServiceResponse;
import com.ygaps.travelapp.response.TourInfoResponse;
import com.ygaps.travelapp.retrofit.Service.Tour.TourAPI;
import com.ygaps.travelapp.retrofit.retrofitRequest;
import com.ygaps.travelapp.view.Activity.CreateTourActivity;
import com.ygaps.travelapp.view.Activity.SplashActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ygaps.travelapp.extras.converter.createDate;
import static com.ygaps.travelapp.view.Activity.DetailStopPoint.serviceId;
import static com.ygaps.travelapp.view.Activity.DetailTourActivity.tourID;
import static com.ygaps.travelapp.view.Activity.DetailTourActivity.tourViewModel;

public class GeneralSPFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private GeneralSPFragment.OnFragmentInteractionListener mListener;

    private EditText etSPName;
    private  EditText etSPAddress;
    private  EditText etMinCost;
    private  EditText etMaxCost;
    private TextView spinnerService;
    private TextView contact;
    private RatingBar simpleRatingBar;

    private int serviceType;
    private long province;
    private double longitudeFromMap;//nhan tu man hinh tour map
    private double latitudeFromMap;
    private TourAPI tourapi;

    private ServiceResponse serviceResponse;

    public GeneralSPFragment() {
        // Required empty public constructor
    }

    public static GeneralSPFragment newInstance(String param1, String param2) {
        GeneralSPFragment fragment = new GeneralSPFragment();
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_general_stop_point, container, false);

        initialize(view);
        getStopPointBaseInfo();

        return view;
    }


    public void initialize(View view)
    {
        etSPName=view.findViewById(R.id.etSPName);
        etSPAddress=view.findViewById(R.id.etSPAddress);
        etMinCost=view.findViewById(R.id.etMinCost);
        etMaxCost=view.findViewById(R.id.etMaxCost);
        spinnerService=view.findViewById(R.id.service_spinner);
        contact=view.findViewById(R.id.contact);
        simpleRatingBar=view.findViewById(R.id.simpleRatingBar);
    }
    private void getStopPointBaseInfo(){
        tourapi= retrofitRequest.getRetrofitInstance().create(TourAPI.class);
        Call<ServiceResponse> callTour= tourapi.getDetailService(serviceId);
        SplashActivity.appPreference.showToast(serviceId+" Successful");
        callTour.enqueue(new Callback<ServiceResponse>() {
            @Override
            public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
                if (response.code()==200){
                    SplashActivity.appPreference.showToast(" Successful");
                    serviceResponse=response.body();

                    etSPName.setText(serviceResponse.getName());
                    etSPAddress.setText(serviceResponse.getAddress());
                    etMinCost.setText(serviceResponse.getMinCost()+"");
                    etMaxCost.setText(serviceResponse.getMaxCost()+"");
                    List<String> name= ReadExcel.getListServiceName(getContext());
                    spinnerService.setText(name.get(serviceResponse.getServiceTypeId()-1)+"");
                    contact.setText(serviceResponse.getContact());
                    simpleRatingBar.setRating(serviceResponse.getSelfStarRating());

                } else {
                    //SplashActivity.appPreference.showToast("Create tour failed in some fields");
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse> call, Throwable t) {
                //SplashActivity.appPreference.showToast("Create Failed. Check your internet connection.");
            }
        });
    }
    
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
}
