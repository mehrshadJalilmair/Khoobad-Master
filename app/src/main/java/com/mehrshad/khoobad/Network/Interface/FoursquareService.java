package com.mehrshad.khoobad.Network.Interface;

import com.mehrshad.khoobad.Model.Place;
import com.mehrshad.khoobad.Model.Places;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoursquareService {

    @GET("venues/explore")
    Call<Places> snapToPlace(@Query("client_id") String client_id,
                             @Query("client_secret") String client_secret,
                             @Query("v") String v,
                             @Query("limit") Integer limit,
                             @Query("ll") String ll,
                             @Query("radius") String radius);

    @GET("venues/")
    Call<Place> placeDetails();
}