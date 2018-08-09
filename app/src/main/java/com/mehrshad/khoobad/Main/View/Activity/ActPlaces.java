package com.mehrshad.khoobad.Main.View.Activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

import java.util.ArrayList;

public class ActPlaces extends AppCompatActivity implements MainPresenter.MainView , SwipeRefreshLayout.OnRefreshListener {

    private ProgressBar progressBar;
    private MainPresenter.presenter presenter;
    private PlacesAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    Query placesQuery;
    final Integer queryLimit = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_places);

        initializeToolbarAndRecyclerView();
        initProgressBar();
        initQueryAndFetch();
    }

    /*
     * Initializing query and presenter then call foursquare api to fetch nearby places
     */
    private void initQueryAndFetch() {

        placesQuery = new Query(this);
        placesQuery.v = "20180808";
        placesQuery.ll = "35.697177,51.381544";

        presenter = new MainPresenterImpl(this, new GetPlacesIntractorImpl());
        presenter.fetchPlaces(placesQuery);
    }

    /**
     * Initializing Toolbar and RecyclerView
     */
    private void initializeToolbarAndRecyclerView() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_places_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ActPlaces.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {

                    placesQuery.limit += queryLimit;
                    presenter.loadMore(placesQuery);
                }
            }
        });

        swipeRefreshLayout = findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        adapter = new PlacesAdapter(null , onRecyclerItemClickListener);
        recyclerView.setAdapter(adapter);
    }
    private OnRecyclerItemClickListener onRecyclerItemClickListener = new OnRecyclerItemClickListener() {
        @Override
        public void onItemClick(int position) {

            Toast.makeText(ActPlaces.this, "item "+position, Toast.LENGTH_SHORT).show();
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

            adapter.addMorePlaces(placeArrayList);
        }
    }

    @Override
    public void onResponseFailure(Throwable throwable) {

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
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
