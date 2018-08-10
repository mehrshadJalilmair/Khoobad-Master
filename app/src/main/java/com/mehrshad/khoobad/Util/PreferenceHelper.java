package com.mehrshad.khoobad.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

import com.google.gson.Gson;
import com.mehrshad.khoobad.Khoobad;
import com.mehrshad.khoobad.Model.Places;

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
    private String CACHED_PLACES_PREF_TAG = "lastPlaces";

    /*
     * Places pref methods
     */
    public Places getCachedPlaces()
    {
        String json = sharedPreferences.getString(CACHED_PLACES_PREF_TAG, null);
        return json == null ? null : new Gson().fromJson(json, Places.class);
    }

    public void setCachedPlaces(Places places)
    {
        String json = places == null ? null : new Gson().toJson(places);
        sharedPreferences.edit().putString(CACHED_PLACES_PREF_TAG, json).apply();
    }

   /*
   * Location pref methods
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
