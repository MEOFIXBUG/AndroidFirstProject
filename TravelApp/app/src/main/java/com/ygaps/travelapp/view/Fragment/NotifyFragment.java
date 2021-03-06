package com.ygaps.travelapp.view.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.adapter.ItemAdapter;
import com.ygaps.travelapp.adapter.NotifyAdapter;
import com.ygaps.travelapp.extras.OpenActivity;
import com.ygaps.travelapp.extras.PaginationScrollListener;
import com.ygaps.travelapp.model.Tour;
import com.ygaps.travelapp.response.TourResponse;
import com.ygaps.travelapp.viewmodel.TourViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.ygaps.travelapp.view.Activity.TourActivity.searchBtn;
import static com.ygaps.travelapp.view.Activity.TourActivity.searchText;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NotifyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotifyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotifyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<Tour> notifyArrayList = new ArrayList<>();
    private NotifyAdapter adapter;
    private RecyclerView my_recycler_view;
    private ProgressBar progress_circular_tour1;
    private ProgressBar progress_circular_tour2;
    private LinearLayoutManager layoutManager;
    public static TourViewModel tourViewModel;
    TextView noTrips;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int page = 1;
    private long total = 0;
    //private TourInterface createTourFromTravelTour;
    private static final String TAG = NotifyFragment.class.getSimpleName();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NotifyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotifyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotifyFragment newInstance(String param1, String param2) {
        NotifyFragment fragment = new NotifyFragment();
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
        View view=inflater.inflate(R.layout.fragment_notify, container, false);
        initialization(view);
        loadData(page);
        return view;
    }
    private void initialization(View view) {

        tourViewModel = ViewModelProviders.of(this).get(TourViewModel.class);
        progress_circular_tour1 = (ProgressBar) view.findViewById(R.id.progress_circular_tour1);
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
        adapter = new NotifyAdapter(getActivity(), notifyArrayList);
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

        //tourViewModel.init(49,1,2);
    }
    private void loadData(int pageIndex) {
        progress_circular_tour2.setVisibility(View.INVISIBLE);
        LiveData<TourResponse> MyInvite= tourViewModel.getInvitation(1000,pageIndex);
        if(MyInvite!= null)
        {
            MyInvite.observe(this,tourResponse->{
                isLoading = false;

                if (tourResponse != null) {
                    total=tourResponse.getTotal();
                    progress_circular_tour2.setVisibility(View.GONE);
                    progress_circular_tour1.setVisibility(View.GONE);
                    noTrips.setVisibility(GONE);
                    List<Tour> a=tourResponse.getUnDeletedTour();
                    for (int i =0;i<=a.size()-1;i++)
                    {
                        adapter.add(a.get(i));
                    }
//                    adapter.addItems(tourResponse.getUnDeletedTour());
                    if (page >= Math.ceil((double)tourResponse.getTotal()/1000)) {
                        isLastPage = true;
                    } else {
                        page = page + 1;
                    }
                }
                else {
                    progress_circular_tour1.setVisibility(View.GONE);
                    noTrips.setVisibility(VISIBLE);
                }
            });
        }

    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
