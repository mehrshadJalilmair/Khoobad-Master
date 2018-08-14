package com.mehrshad.khoobad.Model;

import android.content.Context;

import com.mehrshad.khoobad.R;

public class Query
{
    public Query(Context context)
    {}

    public Integer limit = 30;
    public Integer queryLimitIncBy = 10;

    public String v;
    public String radius = "500";
    public String ll;
    public String intent = "match";

    public boolean cachedVenues = false;
    public int cachedVenuesLoadCount = 0;
}
