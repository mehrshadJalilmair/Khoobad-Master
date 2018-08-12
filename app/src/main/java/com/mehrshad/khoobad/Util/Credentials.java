package com.mehrshad.khoobad.Util;

import com.mehrshad.khoobad.Khoobad;
import com.mehrshad.khoobad.R;

public class Credentials {

    private static final Credentials ourInstance = new Credentials();

    public static Credentials getInstance() {
        return ourInstance;
    }

    private Credentials() {

        client_id = Khoobad.context.getResources().getString(R.string.foursquare_client_id);
        client_secret = Khoobad.context.getResources().getString(R.string.foursquare_client_secret);
    }

    private String client_id;
    private String client_secret;

    public String getClient_id() {
        return client_id;
    }
    public String getClient_secret() {
        return client_secret;
    }
}
