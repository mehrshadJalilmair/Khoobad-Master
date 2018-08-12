package com.mehrshad.khoobad.Main.Presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mehrshad.khoobad.Khoobad;
import com.mehrshad.khoobad.Model.VenueDetails;
import com.mehrshad.khoobad.Model.Venues;
import com.mehrshad.khoobad.Model.Query;
import com.mehrshad.khoobad.Network.HelperRetrofit;
import com.mehrshad.khoobad.R;
import com.mehrshad.khoobad.Util.Credentials;
import com.mehrshad.khoobad.Util.InternetConnection;
import com.mehrshad.khoobad.Util.PreferenceHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetVenuesIntractorImpl implements MainPresenter.GetVenuesIntractor {

    private Callback<Venues> venuesCallback;
    private Callback<VenueDetails> venueDetailsCallback;

    @Override
    public void getVenues(OnFinishedListener onFinishedListener , Query query) {

        if (query.cachedVenues || (!InternetConnection.getInstance().isConnected() && query.cachedVenues))
        {
            Venues venues =  PreferenceHelper.getInstance().getCachedVenues();
            if (venues == null)
            {
                onFinishedListener.onFailure(Khoobad.context.getResources().getString(R.string.TXT_NO_CACHED_VENUE)); //there is no cached venue
            }
            else
            {
                onFinishedListener.onFinished(venues);
            }
        }
        else
        {
            if (InternetConnection.getInstance().isConnected())
            {
                Call<Venues> venuesCall = new HelperRetrofit().venuesScope().snapToVenue(Credentials.getInstance().getClient_id() ,
                        Credentials.getInstance().getClient_secret() , query.v , query.limit , query.ll , query.intent , query.radius);

                Log.d("urlurl" , venuesCall.request().url().toString());

                venuesCallback = new Callback<Venues>() {
                    @Override
                    public void onResponse(@NonNull Call<Venues> call, @NonNull final Response<Venues> response) {

                        if (response.isSuccessful()) {

                            if (response.body() != null) {
                                if (response.body().getMeta() != null) {
                                    if (response.body().getMeta().getCode().equalsIgnoreCase("200")) {
                                        onFinishedListener.onFinished(response.body());
                                        return;
                                    }
                                }
                            }
                        }
                        onFinishedListener.onFailure(Khoobad.context.getResources().getString(R.string.TXT_PARSING_ERROR)); //status is not  >= 200 or < 300
                    }

                    @Override
                    public void onFailure(@NonNull Call<Venues> call, @NonNull Throwable t) {

                        onFinishedListener.onFailure(Khoobad.context.getResources().getString(R.string.TXT_FETCH__VENUES_ERROR)); //400
                    }
                };
                venuesCall.enqueue(venuesCallback);
            }
            else
            {
                onFinishedListener.onFailure(Khoobad.context.getResources().getString(R.string.TXT_NO_INTERNET_CONNECTION)); //there is no internet connection
            }
        }
    }

    @Override
    public void getVenueById(OnGetVenueFinishedListener onGetVenueFinishedListener, String VENUE_ID, String VERSIONING) {

        if (InternetConnection.getInstance().isConnected())
        {
            Call<VenueDetails> venueDetailsCall = new HelperRetrofit().venuesScope().venueDetails(VENUE_ID,Credentials.getInstance().getClient_id() ,
                    Credentials.getInstance().getClient_secret() , VERSIONING);

            Log.d("VENUE_ID" , venueDetailsCall.request().url().toString());

            venueDetailsCallback = new Callback<VenueDetails>() {
                @Override
                public void onResponse(@NonNull Call<VenueDetails> call, @NonNull final Response<VenueDetails> response) {

                    if (response.isSuccessful()) {

                        if (response.body() != null) {
                            if (response.body().getMeta() != null) {
                                if (response.body().getMeta().getCode().equalsIgnoreCase("200")) {
                                    onGetVenueFinishedListener.onGetVenueFinished(response.body());
                                    return;
                                }
                            }
                        }
                    }

                    String msg  = Khoobad.context.getResources().
                            getString(R.string.TXT_PARSING_ERROR);;
                    switch (response.code())
                    {
                        case 429:
                            Khoobad.context.getResources().
                                    getString(R.string.TXT_EXCEED);
                            break;
                    }
                    onGetVenueFinishedListener.
                            onGetVenueFailure(
                                    msg); //status is not  >= 200 or < 300
                }

                @Override
                public void onFailure(@NonNull Call<VenueDetails> call, @NonNull Throwable t) {

                    onGetVenueFinishedListener.onGetVenueFailure(Khoobad.context.getResources().getString(R.string.TXT_FETCH__VENUES_ERROR)); //400
                }
            };
            venueDetailsCall.enqueue(venueDetailsCallback);
        }
        else
        {
            onGetVenueFinishedListener.onGetVenueFailure(Khoobad.context.getResources().getString(R.string.TXT_NO_INTERNET_CONNECTION)); //there is no internet connection
        }
    }

    @Override
    public void onDestroy() {

        if (venuesCallback != null)
            venuesCallback = null;
        if (venueDetailsCallback != null)
            venueDetailsCallback = null;
    }
}
