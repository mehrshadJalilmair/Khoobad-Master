package com.mehrshad.khoobad.Main.View.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mehrshad.khoobad.Interface.OnRecyclerItemClickListener;
import com.mehrshad.khoobad.Main.Adapter.PlacesAdapter;
import com.mehrshad.khoobad.Main.Presenter.GetPlacesIntractorImpl;
import com.mehrshad.khoobad.Main.Presenter.MainPresenter;
import com.mehrshad.khoobad.Main.Presenter.MainPresenterImpl;
import com.mehrshad.khoobad.Model.Place;
import com.mehrshad.khoobad.Model.Places;
import com.mehrshad.khoobad.Model.Query;
import com.mehrshad.khoobad.R;
import com.mehrshad.khoobad.Util.DateFormatter;
import com.mehrshad.khoobad.Util.EndlessRecyclerViewScrollListener;
import com.mehrshad.khoobad.Util.MLocationManager;
import com.mehrshad.khoobad.Util.PreferenceHelper;

import java.util.ArrayList;

public class ActPlaces extends AppCompatActivity implements MainPresenter.MainView , SwipeRefreshLayout.OnRefreshListener, MLocationManager.LocationManagerInterface {

    private ProgressBar progressBar;
    private MainPresenter.presenter presenter;
    private PlacesAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    Query placesQuery;
    final Integer queryLimit = 10;

    static public final int REQUEST_LOCATION = 1;
    MLocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_places);

        initializeToolbarAndRecyclerView();
        initProgressBar();
        initQuery();
        initLocationManager();
    }

    @Override
    protected void onStart() {
        super.onStart();

        locationManager.startUpdatingLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.onDestroy();
        locationManager.stopUpdatingLocation();
    }

    /*
     * Initializing location manager to access user's location
     */
    private void initLocationManager() {

        locationManager = new MLocationManager(this , this);
    }

    /*
     * Initializing query and presenter then call foursquare api to fetch nearby places
     */
    private void initQuery() {

        placesQuery = new Query(this);
        placesQuery.v = DateFormatter.currentDate();
        presenter = new MainPresenterImpl(this, new GetPlacesIntractorImpl());
    }

    /**
     * Initializing Toolbar and RecyclerView
     */
    private void initializeToolbarAndRecyclerView() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_places_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ActPlaces.this);
        recyclerView.setLayoutManager(layoutManager);

        // Retain an instance so that you can call `resetState()` for fresh searches
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                placesQuery.limit += queryLimit;
                presenter.loadMore(placesQuery);
                Log.d("loadmore" , placesQuery.limit+"");
            }
        };
        recyclerView.addOnScrollListener(scrollListener);


        swipeRefreshLayout = findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        adapter = new PlacesAdapter(null , onRecyclerItemClickListener);
        recyclerView.setAdapter(adapter);
    }
    private OnRecyclerItemClickListener onRecyclerItemClickListener = position ->
            Toast.makeText(ActPlaces.this, "item "+position, Toast.LENGTH_SHORT).show();

    /**
     * Initializing ProgressBar
     */
    private void initProgressBar() {

        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);

        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setGravity(Gravity.CENTER);
        relativeLayout.addView(progressBar);
        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        progressBar.setVisibility(View.INVISIBLE);
        this.addContentView(relativeLayout, params);
    }

    @Override
    public void onRefresh() {

        placesQuery.limit = queryLimit;
        presenter.onRefresh(placesQuery);
    }

    @Override
    public void setDataToRecyclerView(Places places) {

        if (places.getResponse().getTotalResults() > 0)
        {
            ArrayList<Place> placeArrayList = new ArrayList<>();
            /*
             * merging all venues of all groups
             */
            for (Places.Response.Group group:
                    places.getResponse().getGroups()) {

                placeArrayList.addAll(group.getItems());
            }
            PreferenceHelper.getInstance().setCachedPlaces(places);
            adapter.addMorePlaces(placeArrayList);
        }
    }

    /**
     * Overriding interfaces
     */
    @Override
    public void onResponseFailure(Throwable throwable) { //null or not null

        Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void endRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void requestLocationAccess() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION , Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_LOCATION);
    }

    @Override
    public void lastKnownLocation(Location currentLocation) {

        placesQuery.cachedPlaces = false;
        placesQuery.ll = currentLocation.getLatitude()+","+currentLocation.getLongitude();
        presenter.fetchPlaces(placesQuery);
    }

    @Override
    public void userLocationUnchanged(Location cachedLocation) {

        placesQuery.cachedPlaces = true;
        presenter.fetchPlaces(placesQuery);
    }

    /**
     * User Location Permission Request callback
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_LOCATION) {

            boolean locationAccess = false;
            if(grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                locationAccess = true;
            }
            locationManager.setLocationAccess(locationAccess);
        }
    }
}
