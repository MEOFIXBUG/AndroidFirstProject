package com.kumeo.traveltour.view.Fragment;

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

import com.kumeo.traveltour.R;
import com.kumeo.traveltour.adapter.TourAdapter;
import com.kumeo.traveltour.model.Tour;
import com.kumeo.traveltour.response.TourResponse;
import com.kumeo.traveltour.view.Activity.TourActivity;
import com.kumeo.traveltour.viewmodel.TourViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TravelFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TravelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TravelFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Tour> tourArrayList = new ArrayList<>();
    private TourAdapter adapter;
    private RecyclerView my_recycler_view;
    private ProgressBar progress_circular_tour;
    private LinearLayoutManager layoutManager;
    TourViewModel tourViewModel;
    private static final String TAG = TourActivity.class.getSimpleName();
    private OnFragmentInteractionListener mListener;

    public TravelFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TravelFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TravelFragment newInstance(String param1, String param2) {
        TravelFragment fragment = new TravelFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_travel, container, false);
        initialization(view);
        getTour();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event

    private void initialization(View view) {
        progress_circular_tour = (ProgressBar) view.findViewById(R.id.progress_circular_tour);
        my_recycler_view = (RecyclerView) view.findViewById(R.id.my_recycler_view);

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
        tourViewModel.init(49,1);

    }
    private void getTour() {
        LiveData<TourResponse> TourList= tourViewModel.getTourResponseLiveData();
        TourList.observe(this,tourResponse->{
            if (tourResponse != null) {
                progress_circular_tour.setVisibility(View.GONE);
                List<Tour> tours = tourResponse.getTours();
                Log.d(TAG, "data:: " + tours.get(0).getName());
                tourArrayList.addAll(tours);
                adapter.notifyDataSetChanged();
            }
        });
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
        //void onFragmentInteraction(Uri uri);
    }
}
