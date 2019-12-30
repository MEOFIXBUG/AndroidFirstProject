package com.ygaps.travelapp.view.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.adapter.ItemAdapter;
import com.ygaps.travelapp.adapter.TourAdapter;
import com.ygaps.travelapp.extras.OpenActivity;
import com.ygaps.travelapp.extras.PaginationScrollListener;
import com.ygaps.travelapp.model.Tour;
import com.ygaps.travelapp.response.TourResponse;
import com.ygaps.travelapp.view.Activity.DetailTourActivity;
import com.ygaps.travelapp.view.Activity.SplashActivity;
import com.ygaps.travelapp.viewmodel.TourViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.ygaps.travelapp.view.Activity.TourActivity.searchBtn;
import static com.ygaps.travelapp.view.Activity.TourActivity.searchText;
import static com.ygaps.travelapp.view.Activity.TourActivity.searchView;

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
    private ItemAdapter adapter;
    private RecyclerView my_recycler_view;
    private ProgressBar progress_circular_tour1;
    private ProgressBar progress_circular_tour2;
    private LinearLayoutManager layoutManager;
    TourViewModel tourViewModel;
    TextView noTrips;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int page = 1;
    private long total = 0;
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
        loadData(page);
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

        progress_circular_tour1 = (ProgressBar) view.findViewById(R.id.progress_circular_tour);
        progress_circular_tour2 = (ProgressBar) view.findViewById(R.id.progress_circular_tour2);
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
        adapter = new ItemAdapter(getActivity(), tourArrayList);
        adapter.setOnItemClicklListener((tour) ->
        {
            OpenActivity.openDetailActivity(getActivity(), tour.getID(),tour.getName(), true);
           /* SplashActivity.appPreference.showToast("onclick" + tour.getID());
            Intent intent = new Intent(getActivity(), DetailTourActivity.class);
            intent.putExtra("tourID",tour.getID());
            intent.putExtra("tourName",tour.getName());
            intent.putExtra("Editable",true);
            startActivity(intent);*/
        });
        my_recycler_view.setAdapter(adapter);
        my_recycler_view.addOnScrollListener(new PaginationScrollListener(layoutManager) {
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
        //tourViewModel.init(49,1,2);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //SplashActivity.appPreference.showToast("onclick btn " );
                searchData(searchText.getText().toString(),1);
            }
        });
    }
    private void loadData(int pageIndex) {
        progress_circular_tour2.setVisibility(View.INVISIBLE);
        LiveData<TourResponse> MyTrips= tourViewModel.getMyTrips(5,pageIndex);

        if(MyTrips!= null)
        {
            MyTrips.observe(this,tourResponse->{
                isLoading = false;
                if (tourResponse != null) {
                    total=tourResponse.getTotal();
                    progress_circular_tour2.setVisibility(View.GONE);
                    progress_circular_tour1.setVisibility(View.GONE);
                    noTrips.setVisibility(GONE);
                    adapter.addItems(tourResponse.getUnDeletedTour());
                    Log.d(TAG,""+Math.ceil((double)tourResponse.getTotal()/5));
                    if (page >= Math.ceil((double)tourResponse.getTotal()/5)) {
                        isLastPage = true;
                    } else {
                        page = page + 1;
                    }
                }
                else {
                    noTrips.setVisibility(VISIBLE);
                }
            });
        }

    }
    private void searchData(String keyWord,int pageIndex){
        progress_circular_tour2.setVisibility(View.INVISIBLE);
        LiveData<TourResponse> MyTrips= tourViewModel.searchMyTrips(keyWord,total,pageIndex);
        if(MyTrips!= null)
        {
            MyTrips.observe(this,tourResponse->{
                isLoading = false;

                if (tourResponse != null) {
                    progress_circular_tour2.setVisibility(View.GONE);
                    progress_circular_tour1.setVisibility(View.GONE);
                    noTrips.setVisibility(GONE);
//                    List<Tour> tours = tourResponse.getTours();
//                    //Log.d(TAG, "data:: " + tours.get(0).getName());
//                    for (Tour temp:tours) {
//                        if(temp.getStatus()!=-1){
//                            tourArrayList.add(temp);
//                        }
//                    }
                    adapter.updateData(tourResponse.getUnDeletedTour());

                    adapter.notifyDataSetChanged();
                }
                else {
                    adapter.clear();
                    adapter.notifyDataSetChanged();
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
