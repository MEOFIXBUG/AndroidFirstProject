package com.kumeo.traveltour.view.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kumeo.traveltour.R;
import com.kumeo.traveltour.adapter.ItemAdapter;
import com.kumeo.traveltour.adapter.TourAdapter;
import com.kumeo.traveltour.extras.PaginationScrollListener;
import com.kumeo.traveltour.model.Tour;
import com.kumeo.traveltour.response.TourResponse;
import com.kumeo.traveltour.retrofit.Service.TourInterface;
import com.kumeo.traveltour.view.Activity.CreateTourActivity;
import com.kumeo.traveltour.view.Activity.DetailTourActivity;
import com.kumeo.traveltour.view.Activity.MainActivity;
import com.kumeo.traveltour.view.Activity.SplashActivity;
import com.kumeo.traveltour.view.Activity.TourActivity;
import com.kumeo.traveltour.viewmodel.TourViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TravelFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TravelFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class TravelFragment extends Fragment {
    private ArrayList<Tour> tourArrayList = new ArrayList<>();
    //private TourAdapter adapter;
    private ItemAdapter adapter;
    private RecyclerView my_recycler_view;
    private ProgressBar progress_circular_tour1;
    private ProgressBar progress_circular_tour2;
    private LinearLayoutManager layoutManager;
    TourViewModel tourViewModel;
    private static final String TAG = TravelFragment.class.getSimpleName();
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int page = 1;
    private OnFragmentInteractionListener mListener;

    private FloatingActionButton btnAdd;

    private TourInterface createTourFromTravelTour;

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


        //Quyennnn
        btnAdd=view.findViewById(R.id.fab);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.appPreference.showToast("Create Tour!!");
                createTourFromTravelTour.openCreateTourActivity();
            }
        });
        ////Quyennnn


        initialization(view);
        loadData(page);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event


    private void initialization(View view) {
        progress_circular_tour1 = (ProgressBar) view.findViewById(R.id.progress_circular_tour1);
        progress_circular_tour2 = (ProgressBar) view.findViewById(R.id.progress_circular_tour2);
        my_recycler_view = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        my_recycler_view.setLayoutManager(layoutManager);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        my_recycler_view.setHasFixedSize(true);

        // adapter
        //adapter = new TourAdapter(getActivity(), tourArrayList);
        adapter = new ItemAdapter(getActivity(), tourArrayList);
        adapter.setOnItemClicklListener((tour) ->
        {
            SplashActivity.appPreference.showToast("onclick" + tour.getID());
            Intent intent = new Intent(getActivity(), DetailTourActivity.class);
            intent.putExtra("tourID",tour.getID());
            intent.putExtra("tourName",tour.getName());
            startActivity(intent);
        });

        my_recycler_view.setAdapter(adapter);
        my_recycler_view.addOnScrollListener(new PaginationScrollListener(layoutManager){

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                if (!isLastPage) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadData(page);
                        }
                    }, 300);
                }
            }

            @Override
            public int getTotalPageCount() {
                return 0;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        // View Model
        tourViewModel = ViewModelProviders.of(this).get(TourViewModel.class);
        //tourViewModel.init(49,1,1);

    }
    private void loadData(long pageIndex) {
        progress_circular_tour2.setVisibility(View.INVISIBLE);
        LiveData<TourResponse> TourList= tourViewModel.getTours(7,pageIndex);
        TourList.observe(this,tourResponse->{

            isLoading = false;
            if (tourResponse != null) {
                progress_circular_tour2.setVisibility(View.GONE);
                progress_circular_tour1.setVisibility(View.GONE);
                adapter.addItems(tourResponse.getTours());
                if (page >= tourResponse.getTotal()/7) {
                    isLastPage = true;
                } else {
                    page = page + 1;
                }
            }
//            if (tourResponse != null) {
//                progress_circular_tour.setVisibility(View.GONE);
//                List<Tour> tours = tourResponse.getTours();
//                Log.d(TAG, "data:: " + tours.get(0).getName());
//                tourArrayList.addAll(tours);
//                adapter.notifyDataSetChanged();
//            }
        });
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
        Activity activity = (Activity) context;
        createTourFromTravelTour = (TourInterface) activity;
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
