package com.mehrshad.khoobad.Network;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.mehrshad.khoobad.Khoobad;

public class NetworkTracker {

    private OnNetworkStatusListener listener;

    public void checkConnection(Context context, OnNetworkStatusListener listener) {

        this.listener = listener;

        if (!isNetworkAvailable()) {

            if (listener != null) {
                listener.onNoProvider();
            }
        }
    }

    private boolean isNetworkAvailable() {

        ConnectivityManager cm = (ConnectivityManager) Khoobad.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = null;
        if (cm != null) {
            netInfo = cm.getActiveNetworkInfo();
        }
        return netInfo != null && netInfo.isConnected();
    }

    public interface OnNetworkStatusListener {

        void onNetworkAvailable();

        void onNetworkNotAvailable();

        void onNoProvider();
    }
}
