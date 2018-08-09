package com.mehrshad.khoobad.Main.Presenter;

import com.mehrshad.khoobad.Model.Place;
import com.mehrshad.khoobad.Model.Places;
import com.mehrshad.khoobad.Model.Query;

import java.util.ArrayList;

public interface MainPresenter {

    /**
     * Call when user interact with the view and other when view OnDestroy()
     * */
    interface presenter{

        void onDestroy();

        void onRefresh(Query query);

        void fetchPlaces(Query query);

        void loadMore(Query query);
    }

    /**
     * showProgress() and hideProgress() would be used for displaying and hiding the progressBar
     * while the setDataToRecyclerView and onResponseFailure is fetched from the GetPacesInteractorImpl class
     **/
    interface MainView {

        void showProgress();

        void hideProgress();

        void endRefreshing();

        void setDataToRecyclerView(Places places);

        void onResponseFailure(Throwable throwable);
    }

    /**
     * Intractors are classes built for fetching data from your database, web services, or any other data source.
     **/
    interface GetPlacesIntractor {

        interface OnFinishedListener {

            void onFinished(Places places);
            void onFailure(Throwable t);
        }
        void getPlaces(OnFinishedListener onFinishedListener, Query query);
    }
}
