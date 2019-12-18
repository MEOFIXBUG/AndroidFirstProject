package com.kumeo.traveltour.retrofit.Service.Tour;


import com.kumeo.traveltour.model.Tour;
import com.kumeo.traveltour.response.TourResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TourAPI {
    @GET("tour/list")
    Call<TourResponse> getListTour(@Query("rowPerPage") long perpage ,
                                   @Query("pageNum") long page
    );
    @GET("tour/history-user")
    Call<TourResponse> getMyTrips(@Query("pageIndex") long page,
                                  @Query("pageSize") String perpage
    );

    @POST("/tour/create")
    Call<Tour> createTour(@Body Tour tourCreate);

}
