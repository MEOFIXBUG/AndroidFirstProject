package com.ygaps.travelapp.view.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.adapter.StopListAdapter;
import com.ygaps.travelapp.extras.OpenActivity;
import com.ygaps.travelapp.extras.SharePreferenceListStopPoint;
import com.ygaps.travelapp.model.Coord;
import com.ygaps.travelapp.model.CoordSet;
import com.ygaps.travelapp.model.StopPoint;
import com.ygaps.travelapp.model.coordRequest;
import com.ygaps.travelapp.response.StopPointList;
import com.ygaps.travelapp.response.TourInfoResponse;
import com.ygaps.travelapp.viewmodel.TourViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.ygaps.travelapp.view.Activity.DetailTourActivity.tourID;
import static com.ygaps.travelapp.view.Activity.TourActivity.searchText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StopPointFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StopPointFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StopPointFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<StopPoint> stopArrayList = new ArrayList<>();
    //private TourAdapter adapter;
    private StopListAdapter adapter;
    private RecyclerView my_recycler_view;
    private LinearLayoutManager layoutManager;
    private FloatingActionButton btnAddSP;
    TourViewModel tourViewModel;
    private ProgressBar progress_bar;
    private  int fromActivity=0;
    private static final String TAG = StopPointFragment.class.getSimpleName();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public StopPointFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StopPointFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StopPointFragment newInstance(String param1, String param2) {
        StopPointFragment fragment = new StopPointFragment();
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
        fromActivity=getArguments().getInt("fromActivity");
        View view  = inflater.inflate(R.layout.fragment_stop_point, container, false);
        initialization(view);
        getstopPoint();

        btnAddSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePreferenceListStopPoint.clear(getActivity());
                SharePreferenceListStopPoint.saveTourIdFromCreateTour(tourID, getActivity());
                OpenActivity.openMapActivity(getActivity());
            }
        });


        return view;
    }
    private void initialization(View view) {
        my_recycler_view = (RecyclerView) view.findViewById(R.id.rcv);
        btnAddSP=view.findViewById(R.id.add_stop);
        progress_bar = (ProgressBar) view.findViewById(R.id.progress_bar);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        my_recycler_view.setLayoutManager(layoutManager);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        my_recycler_view.setHasFixedSize(true);
        // adapter
        //adapter = new TourAdapter(getActivity(), tourArrayList);
        adapter = new StopListAdapter(getActivity(),stopArrayList);
        adapter.setOnItemClickListener((stop) ->
        {
            //.appPreference.showToast("123 Tour!!");
            if(fromActivity==1)
            OpenActivity.openStopPointDetail(getActivity(),stop.getId());
            if(fromActivity==2)
                OpenActivity.openStopPointDetail(getActivity(),stop.getServiceId());
        });

        my_recycler_view.setAdapter(adapter);


        // View Model
        tourViewModel = ViewModelProviders.of(this).get(TourViewModel.class);
        //tourViewModel.init(49,1,1);
        if(fromActivity==1)
        {
            searchText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                        adapter.filter(s.toString());

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }
    @SuppressLint("RestrictedApi")
    private void getstopPoint() {
        if(fromActivity==2){
            LiveData<TourInfoResponse> data= tourViewModel.getTourInfo(tourID);
            if(data!= null)
            {
                data.observe(this,tourInfoResponse->{

                    progress_bar.setVisibility(View.GONE);
                    if (tourInfoResponse != null) {
                        List<StopPoint> stop = tourInfoResponse.getStopPoints();
                        if(!stop.isEmpty())
                        {
                            stopArrayList.addAll(stop);

                        }
                        adapter.notifyDataSetChanged();
                    }
                    else {

                    }
                });
            }
        }
        if(fromActivity==1)
        {
            btnAddSP.setVisibility(View.GONE);
            coordRequest a= new coordRequest();
            a.setHasOneCoordinate(false);

            ////
            Coord p1= new Coord();
            p1.setLat(23.980056);
            p1.setLong(85.577677);

            Coord p2= new Coord();
            p2.setLat(23.588665);
            p2.setLong(163.065945);
            CoordSet line1= new CoordSet(p1,p2);

            ////
            Coord p3= new Coord();
            p3.setLat(-12.098356);
            p3.setLong(163.707522);

            Coord p4= new Coord();
            p4.setLat(-13.928084);
            p4.setLong(75.526301);
            CoordSet line2= new CoordSet(p3,p4);

            List<CoordSet> t =new ArrayList<>();
            t.add(line1); t.add(line2);
            a.setCoordList(t);

            //Log.d(TAG,a.toString());
            LiveData<StopPointList> data= tourViewModel.getSuggestDestination(a);
            if(data!= null)
            {
                data.observe(this,StopPointList->{

                    progress_bar.setVisibility(View.GONE);
                    if (StopPointList != null) {
                        List<StopPoint> stop = StopPointList.getStopPoints();
                        if(!stop.isEmpty())
                        {
                            stopArrayList.addAll(stop);

                        }
                        adapter.notifyDataSetChanged();
                    }
                    else {

                    }
                });
            }
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
