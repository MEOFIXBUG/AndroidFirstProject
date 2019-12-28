package com.ygaps.travelapp.retrofit.Service.Tour;


import com.ygaps.travelapp.model.Tour;
import com.ygaps.travelapp.model.isAccept;
import com.ygaps.travelapp.response.RecoveryResponse;
import com.ygaps.travelapp.response.TourInfoResponse;
import com.ygaps.travelapp.response.TourResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TourAPI {
    @GET("tour/list")
    Call<TourResponse> getListTour(@Query("rowPerPage") long perpage,
                                   @Query("pageNum") long page
    );
    @GET("tour/history-user")
    Call<TourResponse> getMyTrips(@Query("pageIndex") long page,
                                  @Query("pageSize") String perpage
    );

    @POST("/tour/create")
    Call<Tour> createTour(@Body Tour tourCreate);

    @GET("tour/info")
    Call<TourInfoResponse> getTourInfo(@Query("tourId") long tourId);

    @POST("/tour/response/invitation")
    Call <RecoveryResponse> responseTourInvite(@Header("Authorization") String author, @Body isAccept param);
}
