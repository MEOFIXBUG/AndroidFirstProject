package com.ygaps.travelapp.view.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ygaps.travelapp.R;
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
    private String UserID=SplashActivity.appPreference.getUserID();
    private EditText etTourName;
    private EditText etStartDate;
    private EditText etEndDate;
    private EditText etAdults;
    private EditText etChilds;
    private EditText etMinCost;
    private EditText etMaxCost;
    private EditText etAvatar;
    public boolean isPrivate=false;
    private EditText textFullname;
    private AutoCompleteTextView textCountry;
    private Button inviteBtn;
    private Button updateAvatar;
    private Button Done;
    private TourAPI tourapi;
    private CheckBox checkBox;
    public Button btnRemoveTour;
    private List<String> countries = new ArrayList<>();
    UserViewModel userViewModel;
    private List<String> IdList =new ArrayList<>();
    private int selectedID=-1;
    private static final String TAG = InviteFragment.class.getSimpleName();
    ArrayAdapter adapterCountries;
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
        View view =inflater.inflate(R.layout.fragment_invite, container, false);
        initialization(view);
        chooseDate(etStartDate);
        chooseDate(etEndDate);

        Log.d(TAG,textCountry.getText().toString());
        getUsers(textCountry.getText().toString());



        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkRequiredField()) {
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
        btnRemoveTour=view.findViewById(R.id.btnRemoveTour);
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
            updateAvatar.setVisibility(View.GONE);
            btnRemoveTour.setVisibility(View.GONE);

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

    }
    private void getUsers(String key) {

        LiveData<UserListRp> data= userViewModel.searchUsers(key,50,1);
        if(data!= null)
        {
            data.observe(this,userResponse->{

                if (userResponse != null) {
                    List<UserInfo> temp = userResponse.getUsers();
                    countries.clear();
                    IdList.clear();
                    int i=0;
                    for (UserInfo s : temp) {
                        if(s.getFullName()!=null){
                            countries.add(s.getFullName());
                            IdList.add(s.getUserId());
                            i++;
                            if(i==5) break;
                        }
                    }

                    for(String a : countries){
                        if(a!= null){
                            Log.d(TAG,a);
                        }
                    }
                    //adapterCountries.add(countries);
                    adapterCountries.clear();
                    adapterCountries.addAll(countries);
                    adapterCountries.notifyDataSetChanged();

                }
                else {

                }
            });
        }

    }
    private void getTourBaseInfo(){
        LiveData<TourInfoResponse> data= tourViewModel.getTourInfo(tourID);
        if(data!= null)
        {
            data.observe(this,tourInfo->{

                if (tourInfo != null) {

                    if (!TextUtils.isEmpty(tourInfo.getName())&& tourInfo.getName()!= null) {
                        etTourName.setText(tourInfo.getName());
                    }
                    if (!TextUtils.isEmpty(createDate(tourInfo.getStartDate()))&& createDate(tourInfo.getStartDate())!= null) {
                        etStartDate.setText(createDate(tourInfo.getStartDate()));
                    }
                    if (!TextUtils.isEmpty(createDate(tourInfo.getEndDate()))&& createDate(tourInfo.getEndDate())!= null) {
                        etEndDate.setText(createDate(tourInfo.getEndDate()));
                    }
                        etAdults.setText(Integer.toString(tourInfo.getAdults()));

                        etChilds.setText(Integer.toString(tourInfo.getChilds()));

                        etMinCost.setText(tourInfo.getMinCost());

                        etMaxCost.setText((tourInfo.getMaxCost()));
                        checkBox.setChecked(!tourInfo.getIsPrivate());

                    //etAvatar

                }
                else {

                }
            });
        }
    }
    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        //editText.setBackgroundColor(Color.WHITE);
    }
    private Tour makeTourRequest() throws ParseException {
        Tour res = new Tour();
        res.setID(tourID);
        res.setName(etTourName.getText().toString());
        res.setStartDate(converter.milisecondDate(etStartDate.getText().toString()));
        res.setEndDate(converter.milisecondDate(etEndDate.getText().toString()));

        if (!TextUtils.isEmpty(etAdults.getText()))res.setAdults(parseInt(etAdults.getText().toString()));
        if( !TextUtils.isEmpty(etChilds.getText()))res.setChilds(parseInt(etChilds.getText().toString()));
        if(!TextUtils.isEmpty(etAvatar.getText()))res.setAvatar(etAvatar.getText().toString());
        if(!TextUtils.isEmpty(etMinCost.getText()))res.setMinCost((etMinCost.getText().toString()));
        if(!TextUtils.isEmpty(etMaxCost.getText()))res.setMaxCost((etMaxCost.getText().toString()));
        if(checkBox.isChecked())isPrivate=false;
        else isPrivate=true;
        res.setIsPrivate(isPrivate);

        return res;
    }
    private void updateTour(Tour reqTour)
    {
        tourapi= retrofitRequest.getRetrofitInstance().create(TourAPI.class);
        Call<Tour> callTour= tourapi.updateTour(reqTour);
        callTour.enqueue(new Callback<Tour>() {
            @Override
            public void onResponse(Call<Tour> call, Response<Tour> response) {
                if (response.isSuccessful()){
                    SplashActivity.appPreference.showToast("Update Successful");
                    //OpenActivity.openDetailActivity(getActivity(),response.body().getID() ,response.body().getName(), true);

                } else {
                    SplashActivity.appPreference.showToast("Create tour failed in some fields");
                }
            }

            @Override
            public void onFailure(Call<Tour> call, Throwable t) {
                SplashActivity.appPreference.showToast("Create Failed. Check your internet connection.");
            }
        });
    }
    private boolean sendInvation(int Uid){
        try {
            return  true;
        }catch ( Exception ex){
            return false;
        }
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

    private void chooseDate(EditText etStartDate)
    {
        final Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(myCalendar, etStartDate);

            }
        };
        etStartDate.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               new DatePickerDialog(getActivity(), date, myCalendar
                                                       .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                                       myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                                           }
                                       }
        );
    }
    private void updateLabel(Calendar myCalendar, EditText etStartDate) {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etStartDate.setText(sdf.format(myCalendar.getTime()));
    }
}
