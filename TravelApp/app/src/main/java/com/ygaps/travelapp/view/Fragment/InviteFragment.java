package com.ygaps.travelapp.view.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.UserInfo;
import com.ygaps.travelapp.adapter.ItemAdapter;
import com.ygaps.travelapp.adapter.TourAdapter;
import com.ygaps.travelapp.extras.PaginationScrollListener;
import com.ygaps.travelapp.model.Tour;
import com.ygaps.travelapp.model.toInvited;
import com.ygaps.travelapp.repository.UserRepository;
import com.ygaps.travelapp.response.TourInfoResponse;
import com.ygaps.travelapp.response.TourResponse;
import com.ygaps.travelapp.response.UserListRp;
import com.ygaps.travelapp.view.Activity.DetailTourActivity;
import com.ygaps.travelapp.view.Activity.SplashActivity;
import com.ygaps.travelapp.viewmodel.TourViewModel;
import com.ygaps.travelapp.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.ygaps.travelapp.extras.converter.createDate;
import static com.ygaps.travelapp.view.Activity.DetailTourActivity.tourID;
import static com.ygaps.travelapp.view.Activity.DetailTourActivity.tourViewModel;
import static com.ygaps.travelapp.view.Activity.DetailTourActivity.Editable;


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
    /* public EditText etSourceLat;
     public EditText etSourceLong;
     public EditText etDesLong;
     public EditText etDesLat;*/
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
    private CheckBox checkBox;
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
        Log.d(TAG,textCountry.getText().toString());
        getUsers(textCountry.getText().toString());
        return view;
    }
    private void initialization(View view) {
        etTourName=view.findViewById(R.id.etTourName);
        etStartDate=view.findViewById(R.id.etStartDate);
        etEndDate=view.findViewById(R.id.etEndDate);
/*        etSourceLong=findViewById(R.id.etSourceLong);
        etSourceLat=findViewById(R.id.etSourceLat);
        etDesLat=findViewById(R.id.etDesLat);
        etDesLong=findViewById(R.id.etDesLong);*/
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
                    tourViewModel.Invite_Join(a);
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
}
