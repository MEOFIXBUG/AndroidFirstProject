package com.kumeo.traveltour.view.Fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kumeo.traveltour.R;
import com.kumeo.traveltour.adapter.TourAdapter;
import com.kumeo.traveltour.model.Tour;
import com.kumeo.traveltour.response.TourResponse;
import com.kumeo.traveltour.retrofit.Service.TourInterface;
import com.kumeo.traveltour.view.Activity.SplashActivity;
import com.kumeo.traveltour.view.Activity.TourActivity;
import com.kumeo.traveltour.viewmodel.TourViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyTripFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyTripFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyTripFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<Tour> tourArrayList = new ArrayList<>();
    private TourAdapter adapter;
    private RecyclerView my_recycler_view;
    private ProgressBar progress_circular_tour;
    private LinearLayoutManager layoutManager;
    TourViewModel tourViewModel;
    TextView noTrips;
    //private TourInterface createTourFromTravelTour;
    private static final String TAG = MyTripFragment.class.getSimpleName();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private FloatingActionButton add_fab;

    public MyTripFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyTripFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyTripFragment newInstance(String param1, String param2) {
        MyTripFragment fragment = new MyTripFragment();
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
        View view = inflater.inflate(R.layout.fragment_my_trip, container, false);
        initialization(view);
        getMyTrips();


/*        ////Quyennnn
        add_fab=view.findViewById(R.id.add_trip);
        add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.appPreference.showToast("Create Tour!!");
                createTourFromTravelTour.openCreateTourActivity();
            }
        });
        ////Quyennnn*/
        return view;
    }
    private void initialization(View view) {
        progress_circular_tour = (ProgressBar) view.findViewById(R.id.progress_circular_tour);
        my_recycler_view = (RecyclerView) view.findViewById(R.id.my_recycler_view);
         //add_fab= (FloatingActionButton) view.findViewById(R.id.add_trip);
        noTrips =(TextView) view.findViewById(R.id.my_trips_no_items);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        my_recycler_view.setLayoutManager(layoutManager);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        my_recycler_view.setHasFixedSize(true);

        // adapter
        adapter = new TourAdapter(getActivity(), tourArrayList);
        my_recycler_view.setAdapter(adapter);

        // View Model
        tourViewModel = ViewModelProviders.of(this).get(TourViewModel.class);
        //tourViewModel.init(49,1,2);

    }
    private void getMyTrips() {
        LiveData<TourResponse> MyTrips= tourViewModel.getMyTrips(49,1);
        if(MyTrips!= null)
        {
            MyTrips.observe(this,tourResponse->{
                progress_circular_tour.setVisibility(GONE);
                if (tourResponse != null) {
                    noTrips.setVisibility(GONE);
                    List<Tour> tours = tourResponse.getTours();
                    Log.d(TAG, "data:: " + tours.get(0).getName());
                    tourArrayList.addAll(tours);
                    adapter.notifyDataSetChanged();
                }
                else {
                    noTrips.setVisibility(VISIBLE);
                }
            });
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
        //Activity activity = (Activity) context;
        //createTourFromTravelTour = (TourInterface) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
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
