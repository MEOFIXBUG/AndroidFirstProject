package com.ygaps.travelapp.view.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.adapter.StopListAdapter;
import com.ygaps.travelapp.model.StopPoint;
import com.ygaps.travelapp.response.TourInfoResponse;
import com.ygaps.travelapp.view.Activity.SplashActivity;
import com.ygaps.travelapp.viewmodel.TourViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.ygaps.travelapp.view.Activity.DetailTourActivity.tourID;
import static com.ygaps.travelapp.view.Activity.DetailTourActivity.tourViewModel;


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
    private ProgressBar progress_bar;
    private StopListAdapter adapter;
    private RecyclerView my_recycler_view;
    private LinearLayoutManager layoutManager;
    //TourViewModel tourViewModel;
    private static final String TAG = TravelFragment.class.getSimpleName();
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
        View view  = inflater.inflate(R.layout.fragment_stop_point, container, false);
        initialization(view);
        getstopPoint();
        return view;
    }
    private void initialization(View view) {
        progress_bar = (ProgressBar) view.findViewById(R.id.progress_bar);
        my_recycler_view = (RecyclerView) view.findViewById(R.id.rcv);

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
            SplashActivity.appPreference.showToast("123 Tour!!");
        });

        my_recycler_view.setAdapter(adapter);


        // View Model
        //tourViewModel = ViewModelProviders.of(this).get(TourViewModel.class);
        //tourViewModel.init(49,1,1);

    }
    private void getstopPoint() {
        LiveData<TourInfoResponse> data= tourViewModel.getTourInfo(tourID);
        if(data!= null)
        {
            data.observe(this,tourInfoResponse->{
                progress_bar.setVisibility(View.GONE);
                if (tourInfoResponse != null) {

                    List<StopPoint> stop = tourInfoResponse.getStopPoints();
                    if(stop.isEmpty())
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
