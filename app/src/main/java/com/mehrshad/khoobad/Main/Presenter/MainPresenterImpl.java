package com.mehrshad.khoobad.Main.Presenter;

import com.mehrshad.khoobad.Model.Places;
import com.mehrshad.khoobad.Model.Query;

public class MainPresenterImpl implements MainPresenter.presenter, MainPresenter.GetPlacesIntractor.OnFinishedListener {

    private MainPresenter.MainView mainView;
    private MainPresenter.GetPlacesIntractor getPlacesIntractor;

    public MainPresenterImpl(MainPresenter.MainView mainView, MainPresenter.GetPlacesIntractor getPlacesIntractor) {

        this.mainView = mainView;
        this.getPlacesIntractor = getPlacesIntractor;
    }

    @Override
    public void onDestroy() {

        mainView = null;
        getPlacesIntractor.onDestroy();
    }

    @Override
    public void onRefresh(Query query) {

        getPlacesIntractor.getPlaces(this , query);
    }

    @Override
    public void fetchPlaces(Query query) {

        mainView.showProgress();
        getPlacesIntractor.getPlaces(this , query);
    }

    @Override
    public void loadMore(Query query) {

        getPlacesIntractor.getPlaces(this , query);
    }

    @Override
    public void onFinished(Places places) {

        if(mainView != null){
            mainView.setDataToRecyclerView(places);
            mainView.hideProgress();
            mainView.endRefreshing();
        }
    }

    @Override
    public void onFailure(Throwable t) {

        if(mainView != null){
            mainView.onResponseFailure(t);
            mainView.hideProgress();
            mainView.endRefreshing();
        }
    }
}
