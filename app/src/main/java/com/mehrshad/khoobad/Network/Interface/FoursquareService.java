package com.mehrshad.khoobad.Network.Interface;

import com.mehrshad.khoobad.Model.VenueDetails;
import com.mehrshad.khoobad.Model.Venues;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FoursquareService {

    @GET("venues/explore")
    Call<Venues> snapToVenue(@Query("client_id") String client_id,
                             @Query("client_secret") String client_secret,
                             @Query("v") String v,
                             @Query("limit") Integer limit,
                             @Query("ll") String ll,
                             @Query("intent") String intent,
                             @Query("radius") String radius);

    @GET("venues/{VENUE_ID}")
    Call<VenueDetails> venueDetails(@Path("VENUE_ID") String VENUE_ID, @Query("client_id") String client_id,
                                    @Query("client_secret") String client_secret, @Query("v") String v);
}