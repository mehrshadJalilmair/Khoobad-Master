package com.mehrshad.khoobad.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

import com.google.gson.Gson;
import com.mehrshad.khoobad.Khoobad;
import com.mehrshad.khoobad.Model.Venues;

public class PreferenceHelper {
    private static final PreferenceHelper ourInstance = new PreferenceHelper();

    public static PreferenceHelper getInstance() {
        return ourInstance;
    }
    private PreferenceHelper() {

        sharedPreferences = Khoobad.context.getSharedPreferences("AppData" , Context.MODE_PRIVATE);
    }

    private SharedPreferences sharedPreferences;

    private String CACHED_LOCATION_PREF_TAG = "lastLocation";
    private String CACHED_VENUES_PREF_TAG = "lastVenues";

    /*
     * Venues pref methods
     */
    public Venues getCachedVenues()
    {
        String json = sharedPreferences.getString(CACHED_VENUES_PREF_TAG, null);
        return json == null ? null : new Gson().fromJson(json, Venues.class);
    }

    public void setCachedVenues(Venues venues)
    {
        String json = venues == null ? null : new Gson().toJson(venues);
        sharedPreferences.edit().putString(CACHED_VENUES_PREF_TAG, json).apply();
    }

   /*
   * VenueLocation pref methods
   */
    public Location getCachedLocation()
    {
        String json = sharedPreferences.getString(CACHED_LOCATION_PREF_TAG, null);
        return json == null ? null : new Gson().fromJson(json, Location.class);
    }

    public void setCachedLocation(Location location)
    {
        String json = location == null ? null : new Gson().toJson(location);
        sharedPreferences.edit().putString(CACHED_LOCATION_PREF_TAG, json).apply();
    }
}
