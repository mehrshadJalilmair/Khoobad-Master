package com.mehrshad.khoobad.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.mehrshad.khoobad.Khoobad;

public class InternetConnection {

    private static final InternetConnection ourInstance = new InternetConnection();

    public static InternetConnection getInstance() {

        return ourInstance;
    }
    private InternetConnection() {}

    private NetworkInfo network()
    {
        Context context = Khoobad.getContext();
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        return activeNetwork;
    }
    public boolean isConnected()
    {
        return network() != null &&
                network().isConnectedOrConnecting();
    }

    public int isConnectedOver()
    {
        return network().getType();
    }

}
