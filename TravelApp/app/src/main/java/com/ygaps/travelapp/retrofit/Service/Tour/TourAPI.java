package com.ygaps.travelapp.retrofit.Service.Tour;


import com.ygaps.travelapp.model.MessageResponse;
import com.ygaps.travelapp.model.ReviewTour;
import com.ygaps.travelapp.model.StopPoint;
import com.ygaps.travelapp.model.StopPointsOfTour;
import com.ygaps.travelapp.model.Tour;
import com.ygaps.travelapp.model.coordRequest;
import com.ygaps.travelapp.model.toInvited;
import com.ygaps.travelapp.response.ReviewTourResponse;
import com.ygaps.travelapp.response.StatusResponse;
import com.ygaps.travelapp.response.StopPointList;
import com.ygaps.travelapp.response.TourInfoResponse;
import com.ygaps.travelapp.response.TourResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TourAPI {
    @GET("tour/list")
    Call<TourResponse> getListTour(@Query("rowPerPage") long perpage ,
                                   @Query("pageNum") long page,
                                   @Query("orderBy") String orderBy,
                                   @Query("isDesc") boolean isDesc
    );
    @GET("tour/history-user")
    Call<TourResponse> getMyTrips(@Query("pageIndex") long page,
                                  @Query("pageSize") String perpage
    );
    @GET("/tour/search-history-user")
    Call<TourResponse> searchMyTrips(@Query("searchKey") String searchKey,
                                @Query("pageIndex") long page,
                                  @Query("pageSize") long perpage
    );

    @GET("/tour/get/review-list")
    Call<ReviewTourResponse> getListReviewTour(@Query("tourId") long tourId,
                                               @Query("pageIndex") long page,
                                               @Query("pageSize") long perpage);

    @POST("/tour/create")
    Call<Tour> createTour(@Body Tour tourCreate);

    @POST("tour/update-tour")
    Call<Tour> updateTour(@Body Tour tour);

    @POST("/tour/set-stop-points")
    Call<MessageResponse> createListStopPoint(@Body StopPointsOfTour listStopPoints);

    @GET("tour/info")
    Call<TourInfoResponse> getTourInfo(@Query("tourId") long tourId);
    @POST("/tour/add/member")
    Call<StatusResponse> Invite_Join(@Body toInvited toInvited);

    @POST("/tour/add/review")
    Call<MessageResponse> sendReviewToPublicTour(@Body ReviewTour review);
    @POST("/tour/suggested-destination-list")
    Call<StopPointList> getSuggestDestination(@Body coordRequest a);
}
