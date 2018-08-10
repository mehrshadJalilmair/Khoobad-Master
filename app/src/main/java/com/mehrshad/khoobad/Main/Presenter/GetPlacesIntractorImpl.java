package com.mehrshad.khoobad.Main.Presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mehrshad.khoobad.Model.Places;
import com.mehrshad.khoobad.Model.Query;
import com.mehrshad.khoobad.Network.HelperRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetPlacesIntractorImpl implements MainPresenter.GetPlacesIntractor {

    @Override
    public void getPlaces(OnFinishedListener onFinishedListener , Query query) {

        Call<Places> placesCall = new HelperRetrofit().placesScope().snapToPlace(query.getClient_id() ,
                query.getClient_secret() , query.v , query.limit , query.ll , query.radius);

        Callback<Places> placesCallback = new Callback<Places>() {
            @Override
            public void onResponse(@NonNull Call<Places> call, @NonNull final Response<Places> response) {

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
                onFinishedListener.onFailure(null);
            }

            @Override
            public void onFailure(@NonNull Call<Places> call, @NonNull Throwable t) {

                Log.d("response" ,t.getMessage());
                onFinishedListener.onFailure(t);
            }
        };
        placesCall.enqueue(placesCallback);
    }
}
