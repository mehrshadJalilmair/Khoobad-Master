package com.mehrshad.khoobad.Main.Presenter;

import com.mehrshad.khoobad.Model.VenueDetails;
import com.mehrshad.khoobad.Model.Venues;
import com.mehrshad.khoobad.Model.Query;

public interface MainPresenter {

    /**
     * Call when user interact with the view and other when view OnDestroy()
     * */
    interface presenter{

        void onDestroy();

        void onRefresh(Query query);

        void fetchVenues(Query query);

        void loadMore(Query query);

        void fetchVenueById(String VENUE_ID, String VERSIONING);
    }

    /**
     * showProgress() and hideProgress() would be used for displaying and hiding the progressBar
     * while the setDataToRecyclerView and onResponseFailure is fetched from the GetPacesInteractorImpl class
     **/
    interface MainView {

        void showProgress();

        void hideProgress();

        void endRefreshing();

        void setDataToRecyclerView(Venues venues);

        void onResponseFailure(String throwable);

        void showVenueDetails(VenueDetails venueDetails);

        void onGetVenueFailure(String throwable);
    }

    /**
     * Intractors are classes built for fetching data from your database, web services, or any other data source.
     **/
    interface GetVenuesIntractor {

        interface OnFinishedListener {

            void onFinished(Venues venues);
            void onFailure(String t);
        }
        interface OnGetVenueFinishedListener
        {
            void onGetVenueFinished(VenueDetails venueDetails);
            void onGetVenueFailure(String t);
        }
        void getVenues(OnFinishedListener onFinishedListener, Query query);
        void getVenueById(OnGetVenueFinishedListener onGetVenueFinishedListener, String VENUE_ID, String VERSIONING);
        void onDestroy();
    }
}
