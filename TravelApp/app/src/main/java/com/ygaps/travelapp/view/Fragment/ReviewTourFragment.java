package com.ygaps.travelapp.view.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.adapter.ReviewTourAdapter;
import com.ygaps.travelapp.extras.OpenActivity;
import com.ygaps.travelapp.model.MessageResponse;
import com.ygaps.travelapp.model.ReviewOfUser;
import com.ygaps.travelapp.model.ReviewTour;
import com.ygaps.travelapp.model.Tour;
import com.ygaps.travelapp.response.ReviewTourResponse;
import com.ygaps.travelapp.response.TourInfoResponse;
import com.ygaps.travelapp.retrofit.Service.Tour.TourAPI;
import com.ygaps.travelapp.retrofit.retrofitRequest;
import com.ygaps.travelapp.view.Activity.SplashActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ygaps.travelapp.extras.converter.createDate;
import static com.ygaps.travelapp.view.Activity.DetailTourActivity.tourID;
import static com.ygaps.travelapp.view.Activity.DetailTourActivity.tourViewModel;

public class ReviewTourFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private ProgressBar progress_bar;
    private ReviewTourFragment.OnFragmentInteractionListener mListener;
    private TextView dateDescription;
    private TextView tvTourName;
    private TextView peopleDescription;
    private TextView my_review_no_items;
    private TextView moneyDescription;
    private RatingBar simpleRatingBar;
    private EditText etReview;
    private Button btnSubmit;
    private String tourName;
    private RecyclerView reviewRecycleView;
    private LinearLayoutManager layoutManager;
    private ArrayList<ReviewOfUser> reviewArrayList = new ArrayList<>();
    private ReviewTourAdapter adapter;
    private LinearLayout ratingTourLinearLayout;
    private LinearLayout reviewLinearLayout;

    TourAPI tourapi;

    private long rating;
    private String review;

    public ReviewTourFragment() {
        // Required empty public constructor
    }

    public static ReviewTourFragment newInstance(String param1, String param2) {
        ReviewTourFragment fragment = new ReviewTourFragment();
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review_tour, container, false);

        initialization(view);
        getTourBaseInfo();
        getReviewList();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rating=Math.round(simpleRatingBar.getRating());
                review=etReview.getText().toString();

                ReviewTour reviewTour=new ReviewTour();
                reviewTour.setTourId(tourID);
                reviewTour.setPoint(rating);
                reviewTour.setReview(review);

                sendReviewPublicTour(reviewTour);
            }
        });

        return view;
    }

    private void initialization(View view) {
        dateDescription=view.findViewById(R.id.dateDescription);
        tvTourName=view.findViewById(R.id.tourName);
        peopleDescription=view.findViewById(R.id.peopleDescription);
        moneyDescription=view.findViewById(R.id.moneyDescription);
        my_review_no_items=view.findViewById(R.id.my_review_no_items);

        simpleRatingBar=view.findViewById(R.id.simpleRatingBar);
        etReview=view.findViewById(R.id.etReview);
        btnSubmit=view.findViewById(R.id.btnSubmit);
        reviewRecycleView=view.findViewById(R.id.reviewRecycleView);
        reviewLinearLayout=view.findViewById(R.id.reviewLinearLayout);
        ratingTourLinearLayout=view.findViewById(R.id.ratingTourLinearLayout);

        layoutManager = new LinearLayoutManager(getActivity());
        reviewRecycleView.setLayoutManager(layoutManager);
        progress_bar = (ProgressBar) view.findViewById(R.id.progress_bar);

        adapter = new ReviewTourAdapter(getActivity(),reviewArrayList);

        reviewRecycleView.setAdapter(adapter);
        //set adapter

    }
    private void getReviewList() {
        LiveData<ReviewTourResponse> data= tourViewModel.getReviewOfTour(tourID, 1, 50);
        if(data!= null)
        {
            data.observe(this,reviewInfoResponse->{

                progress_bar.setVisibility(View.GONE);
                if (reviewInfoResponse != null) {
                    List<ReviewOfUser> review = reviewInfoResponse.getreviewList();
                    if(!review.isEmpty())
                    {
                        reviewArrayList.addAll(review);

                    }
                    adapter.notifyDataSetChanged();
                }
                else {
                    my_review_no_items.setVisibility(View.GONE);
                }
            });
        }
        else {
            my_review_no_items.setVisibility(View.GONE);
        }

    }
    private void getTourBaseInfo(){
        LiveData<TourInfoResponse> data= tourViewModel.getTourInfo(tourID);
        if(data!= null)
        {
            data.observe(this,tourInfo->{

                if (tourInfo != null) {
                    if (tourInfo.getIsPrivate()==true){
                        ratingTourLinearLayout.setVisibility(View.GONE);
                        reviewLinearLayout.setVisibility(View.GONE);
                    }
                    tourName=tourInfo.getName();
                    if (!TextUtils.isEmpty(tourInfo.getName())&& tourInfo.getName()!= null) {
                        tvTourName.setText(tourInfo.getName());
                    }
                    else tvTourName.setText("[Tour name]");
                    if (!TextUtils.isEmpty(createDate(tourInfo.getStartDate()))&& createDate(tourInfo.getStartDate())!= null&&!TextUtils.isEmpty(createDate(tourInfo.getEndDate()))&& createDate(tourInfo.getEndDate())!= null) {
                        dateDescription.setText(createDate(tourInfo.getStartDate())+" - "+createDate(tourInfo.getEndDate()));
                    }
                    peopleDescription.setText(tourInfo.getAdults()+"  adults"+ "           "+tourInfo.getChilds() + " childs");
                    moneyDescription.setText(tourInfo.getMinCost()+ " - "+ tourInfo.getMaxCost());
                }
                else {

                }
            });
        }
    }

    private void sendReviewPublicTour(ReviewTour reqTour) {
        tourapi = retrofitRequest.getRetrofitInstance().create(TourAPI.class);
        Call<MessageResponse> callTour = tourapi.sendReviewToPublicTour(reqTour);
        callTour.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.code()==200){
                    SplashActivity.appPreference.showToast(response.body().getMessage());
                    OpenActivity.openDetailActivity(getActivity(), tourID,tourName, true );
                } else {
                    SplashActivity.appPreference.showToast("Send review to tour failed in some fields");
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                SplashActivity.appPreference.showToast("Send review to tour failed..");
            }
        });

    }





















    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
}
