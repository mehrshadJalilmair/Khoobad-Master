package com.mehrshad.khoobad.Model;

import android.content.Context;

import com.mehrshad.khoobad.R;

public class Query
{
    public Query(Context context)
    {
        client_id = context.getResources().getString(R.string.foursquare_client_id);
        client_secret = context.getResources().getString(R.string.foursquare_client_secret);
    }

    public Integer limit = 30;
    public Integer queryLimitIncBy = 10;

    public String v;
    private String client_id;
    private String client_secret;
    public String radius = "500";
    public String ll;
    public String intent = "match";
    public boolean cachedPlaces = false;

    public String getClient_id() {
        return client_id;
    }
    public String getClient_secret() {
        return client_secret;
    }
}
