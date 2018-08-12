package com.mehrshad.khoobad.Main.Presenter;

import com.mehrshad.khoobad.Model.VenueDetails;
import com.mehrshad.khoobad.Model.Venues;
import com.mehrshad.khoobad.Model.Query;

public class MainPresenterImpl implements MainPresenter.presenter, MainPresenter.GetVenuesIntractor.OnFinishedListener , MainPresenter.GetVenuesIntractor.OnGetVenueFinishedListener {

    private MainPresenter.MainView mainView;
    private MainPresenter.GetVenuesIntractor getVenuesIntractor;

    public MainPresenterImpl(MainPresenter.MainView mainView, MainPresenter.GetVenuesIntractor getVenuesIntractor) {

        this.mainView = mainView;
        this.getVenuesIntractor = getVenuesIntractor;
    }

    @Override
    public void onDestroy() {

        if (mainView != null)mainView = null;
        getVenuesIntractor.onDestroy();
    }

    @Override
    public void onRefresh(Query query) {

        getVenuesIntractor.getVenues(this , query);
    }

    @Override
    public void fetchVenues(Query query) {

        mainView.showProgress();
        getVenuesIntractor.getVenues(this , query);
    }

    @Override
    public void loadMore(Query query) {

        getVenuesIntractor.getVenues(this , query);
    }

    @Override
    public void fetchVenueById(String VENUE_ID, String VERSIONING) {

        mainView.showProgress();
        getVenuesIntractor.getVenueById(this , VENUE_ID, VERSIONING);
    }

    @Override
    public void onFinished(Venues venues) {

        if(mainView != null){
            mainView.setDataToRecyclerView(venues);
            mainView.hideProgress();
            mainView.endRefreshing();
        }
    }

    @Override
    public void onFailure(String t) {

        if(mainView != null){
            mainView.onResponseFailure(t);
            mainView.hideProgress();
            mainView.endRefreshing();
        }
    }

    @Override
    public void onGetVenueFinished(VenueDetails venueDetails) {

        if(mainView != null){
            mainView.hideProgress();
            mainView.showVenueDetails(venueDetails);
        }
    }

    @Override
    public void onGetVenueFailure(String t) {

        if(mainView != null){
            mainView.hideProgress();
            mainView.onGetVenueFailure(t);
        }
    }
}
