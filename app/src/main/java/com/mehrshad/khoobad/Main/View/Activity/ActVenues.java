package com.mehrshad.khoobad.Main.View.Activity;

import android.Manifest;
import android.content.Intent;
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
import com.mehrshad.khoobad.Main.Adapter.MRecyclerView;
import com.mehrshad.khoobad.Main.Adapter.VenuesAdapter;
import com.mehrshad.khoobad.Main.Presenter.GetVenuesIntractorImpl;
import com.mehrshad.khoobad.Main.Presenter.MainPresenter;
import com.mehrshad.khoobad.Main.Presenter.MainPresenterImpl;
import com.mehrshad.khoobad.Model.Venue;
import com.mehrshad.khoobad.Model.VenueDetails;
import com.mehrshad.khoobad.Model.Venues;
import com.mehrshad.khoobad.Model.Query;
import com.mehrshad.khoobad.R;
import com.mehrshad.khoobad.Util.DateFormatter;
import com.mehrshad.khoobad.Util.EndlessRecyclerViewScrollListener;
import com.mehrshad.khoobad.Util.GeneralFunctions;
import com.mehrshad.khoobad.Util.MLocationManager;
import com.mehrshad.khoobad.Util.PreferenceHelper;

import java.util.ArrayList;
import java.util.Collections;

public class ActVenues extends AppCompatActivity implements MainPresenter.MainView , SwipeRefreshLayout.OnRefreshListener, MLocationManager.LocationManagerInterface {

    private ProgressBar progressBar;
    private MainPresenter.presenter presenter;
    private VenuesAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    MLocationManager locationManager;

    Query venuesQuery;
    static public final int REQUEST_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_venues);

        initializeToolbarAndRecyclerView();
        initProgressBar();
        initQuery();
        initLocationManager();
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.onDestroy();
        //locationManager.stopUpdatingLocation();
    }

    /*
     * Initializing location manager to access user's location
     */
    private void initLocationManager() {

        locationManager = new MLocationManager(this , this);
        locationManager.startUpdatingLocation();
    }

    /*
     * Initializing query and presenter then call foursquare api to fetch nearby venues
     */
    private void initQuery() {

        venuesQuery = new Query(this);
        venuesQuery.v = DateFormatter.currentDate();
        presenter = new MainPresenterImpl(this, new GetVenuesIntractorImpl());
    }

    /**
     * Initializing Toolbar and RecyclerView
     */
    private void initializeToolbarAndRecyclerView() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MRecyclerView recyclerView = findViewById(R.id.recycler_view_venues_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ActVenues.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setEmptyView(findViewById(R.id.no_venue_tv));

        // Retain an instance so that you can call `resetState()` for fresh searches
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                venuesQuery.limit += venuesQuery.queryLimitIncBy;
                presenter.loadMore(venuesQuery);
                Log.d("loadmore" , venuesQuery.limit+"");
            }
        };
        recyclerView.addOnScrollListener(scrollListener);

        swipeRefreshLayout = findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        adapter = new VenuesAdapter(null , onRecyclerItemClickListener);
        recyclerView.setAdapter(adapter);
    }

    private OnRecyclerItemClickListener onRecyclerItemClickListener = VENUE_ID ->
            presenter.fetchVenueById(VENUE_ID , DateFormatter.currentDate());

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

        venuesQuery.limit = venuesQuery.queryLimitIncBy;
        presenter.onRefresh(venuesQuery);
    }

    @Override
    public void setDataToRecyclerView(Venues venues) {

        ArrayList<Venue> venueArrayList = new ArrayList<>();
        if (venues.getResponse().getTotalResults() > 0)
        {
            /*
             * merging all venues of all groups
             */
            for (Venues.Response.Group group:
                    venues.getResponse().getGroups()) {

                venueArrayList.addAll(group.getItems());
            }
        }
        Collections.sort(venueArrayList
                , (o1, o2) -> o1.getBaseVenue().getLocation().getDistance().compareTo(o2.getBaseVenue().getLocation().getDistance()));
        PreferenceHelper.getInstance().setCachedVenues(venues);
        adapter.addMoreVenues(venueArrayList);
    }

    /**
     * Overriding interfaces
     */
    @Override
    public void onResponseFailure(String throwable) { //null or not null

        Toast.makeText(this, throwable, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showVenueDetails(VenueDetails venueDetails) {

        String venueDetailsStr = GeneralFunctions.stringOf(venueDetails);
        if (venueDetailsStr == null)
        {
            Toast.makeText(this, getResources().getString(R.string.TXT_CANT_SHOW_VENUE), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(ActVenues.this , ActVenue.class);
        intent.putExtra("venue" , venueDetailsStr);
        startActivity(intent);
    }

    @Override
    public void onGetVenueFailure(String throwable) {

        Toast.makeText(this, throwable, Toast.LENGTH_SHORT).show();
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

        venuesQuery.cachedVenues = false;
        venuesQuery.limit = 30;
        venuesQuery.ll = currentLocation.getLatitude()+","+currentLocation.getLongitude();
        presenter.fetchVenues(venuesQuery);
    }

    @Override
    public void userLocationUnchanged(Location cachedLocation) {

        venuesQuery.cachedVenues = true;
        presenter.fetchVenues(venuesQuery);
    }

    /**
     * User VenueLocation Permission Request callback
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_LOCATION) {

            boolean locationAccess = false;
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                locationAccess = true;
            }
            locationManager.setLocationAccess(locationAccess);
            if (locationAccess)locationManager.startUpdatingLocation();
        }
    }
}
