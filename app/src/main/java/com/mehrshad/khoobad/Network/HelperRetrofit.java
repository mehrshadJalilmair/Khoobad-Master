package com.mehrshad.khoobad.Network;


import android.support.annotation.NonNull;

import com.mehrshad.khoobad.Network.Interface.FoursquareService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mehrshad.khoobad.Khoobad.context;


public class HelperRetrofit {


    public HelperRetrofit()
    {

    }

    public  Retrofit configureRetrofit(final String baseUrl) {
        final Retrofit retrofit;

        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {

                Request request = chain.request().newBuilder().build();
                return chain.proceed(request);
            }
        }).build();

        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder().baseUrl(baseUrl).client(httpClient).addConverterFactory(GsonConverterFactory.create(gson)).build();

        return retrofit;
    }

    public FoursquareService venuesScope() {

        return configureRetrofit("https://api.foursquare.com/v2/").create(FoursquareService.class);
    }
}
