package com.mehrshad.khoobad.Main.View.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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
import com.mehrshad.khoobad.Model.Query;
import com.mehrshad.khoobad.Model.Venue;
import com.mehrshad.khoobad.Model.VenueDetails;
import com.mehrshad.khoobad.Model.Venues;
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
    private MLocationManager locationManager;
    private MRecyclerView recyclerView;

    Query venuesQuery;
    static public final int REQUEST_LOCATION = 1;
    static public final int REQUEST_LOCATION_SRVICE = 2;

    AlertDialog enableLocationDlg = null;

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
    protected void onDestroy() {
        super.onDestroy();

        if (presenter != null)presenter.onDestroy();
        if (locationManager != null)locationManager.stopUpdatingLocation();
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
        venuesQuery.v = GeneralFunctions.persian_to_english(DateFormatter.currentDate());
        presenter = new MainPresenterImpl(this, new GetVenuesIntractorImpl());
    }

    /**
     * Initializing Toolbar and RecyclerView
     */
    private void initializeToolbarAndRecyclerView() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view_venues_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ActVenues.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setEmptyView(findViewById(R.id.no_venue_tv));

        // Retain an instance so that you can call `resetState()` for fresh searches
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list

                if (venuesQuery.cachedVenues)return;
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

        adapter = new VenuesAdapter(getApplicationContext() , new ArrayList<>() , onRecyclerItemClickListener);
        recyclerView.setAdapter(adapter);
    }

    private OnRecyclerItemClickListener onRecyclerItemClickListener = new OnRecyclerItemClickListener() {
        @Override
        public void onItemClick(String VENUE_ID) {

            runOnUiThread(() -> recyclerView.setEnabled(false));
            presenter.fetchVenueById(VENUE_ID , GeneralFunctions.persian_to_english(DateFormatter.currentDate()));
        }
    };

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
        int resCount = venues.getResponse().getTotalResults();

        if (resCount > 0)
        {

             //merging all venues of all groups

            ArrayList<Venues.Response.Group> groups = venues.getResponse().getGroups();
            if (groups != null)
            {
                for (int i = 0 ; i < groups.size() ; i++)
                {
                    Venues.Response.Group group = groups.get(i);
                    if (group != null)
                    {
                        venueArrayList.addAll(group.getItems());
                    }
                }
            }
        }
        adapter.addMoreVenues(venueArrayList);
        PreferenceHelper.getInstance().setCachedVenues(venues);
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

        runOnUiThread(() -> recyclerView.setEnabled(true)); //single selection;
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

        runOnUiThread(() -> recyclerView.setEnabled(true)); //single selection;
        Toast.makeText(this, throwable, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        if (progressBar != null)progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        if (progressBar != null)progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void endRefreshing() {
        if (swipeRefreshLayout.isRefreshing())swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void requestLocationAccess() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION , Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_LOCATION);
    }

    /*
    * to enable location service
     */
    @Override
    public void checkIsLocationProviderEnable() {

        Context ctx = this;

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setCancelable(false);
        builder.setMessage(ctx.getResources().getString(R.string.TXT_GPS_NOT_ENABLE));
        builder.setPositiveButton(ctx.getResources().getString(R.string.TXT_ENABLE_GPS), (paramDialogInterface, paramInt) -> {

            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(myIntent , REQUEST_LOCATION_SRVICE);
        });
        builder.setNegativeButton(ctx.getString(R.string.TXT_CANCEL), (paramDialogInterface, paramInt) -> {
        });
        enableLocationDlg = builder.show();
    }

    @Override
    public void lastKnownLocation(Location currentLocation) {

        venuesQuery.cachedVenues = false;
        venuesQuery.cachedVenuesLoadCount = 0;
        venuesQuery.limit = 30;
        venuesQuery.ll = GeneralFunctions.persian_to_english(currentLocation.getLatitude()+","+currentLocation.getLongitude());
        presenter.fetchVenues(venuesQuery);
    }

    @Override
    public void userLocationUnchanged(Location cachedLocation) {

        if (venuesQuery.cachedVenuesLoadCount == 1)return;
        venuesQuery.cachedVenuesLoadCount++;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_LOCATION_SRVICE)
        {
            locationManager.startUpdatingLocation();
        }
    }
}
