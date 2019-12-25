package com.kumeo.traveltour.view.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.kumeo.traveltour.R;
import com.kumeo.traveltour.adapter.ItemAdapter;
import com.kumeo.traveltour.adapter.StopListAdapter;
import com.kumeo.traveltour.extras.PaginationScrollListener;
import com.kumeo.traveltour.model.StopPoint;
import com.kumeo.traveltour.view.Activity.DetailTourActivity;
import com.kumeo.traveltour.view.Activity.SplashActivity;
import com.kumeo.traveltour.viewmodel.TourViewModel;

import java.util.ArrayList;


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
    TourViewModel tourViewModel;
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
        return view;
    }
    private void initialization(View view) {
        my_recycler_view = (RecyclerView) view.findViewById(R.id.rcv);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        my_recycler_view.setLayoutManager(layoutManager);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        my_recycler_view.setHasFixedSize(true);
        StopPoint a = new StopPoint();
        a.setName("Quan Com");
        stopArrayList.add(a);
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
