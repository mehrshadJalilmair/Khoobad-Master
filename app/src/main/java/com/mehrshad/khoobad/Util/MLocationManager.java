package com.mehrshad.khoobad.Util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class MLocationManager {

    public interface LocationManagerInterface
    {
        void requestLocationAccess();
        void checkIsLocationProviderEnable();
        void lastKnownLocation(Location currentLocation);
        void userLocationUnchanged(Location cachedLocation);
    }

    private LocationManagerInterface locationManagerInterface;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private boolean locationAccess;
    private Location currentLocation;
    private Location cachedLocation;
    private Context context;

    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 6000;

    public MLocationManager(Context activity, LocationManagerInterface locationManagerInterface) {

        this.context = activity;
        this.locationManagerInterface = locationManagerInterface;

        //has location access
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            locationManagerInterface.requestLocationAccess();
        }
        else
        {
            setLocationAccess(true);
        }
    }

    private boolean cachedState(Location currentLocation) {
        return cachedLocation != null && cachedLocation.distanceTo(currentLocation) <= 100;
    }

    private void initLocationUpdateRequest()
    {
        this.locationRequest = LocationRequest.create();
        this.locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        this.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        this.locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                currentLocation = locationResult.getLastLocation();
                if (cachedState(currentLocation))
                    locationManagerInterface.userLocationUnchanged(cachedLocation);
                else
                    locationManagerInterface.lastKnownLocation(currentLocation);

                cachedLocation = currentLocation;
                PreferenceHelper.getInstance().setCachedLocation(cachedLocation);
            }
        };
        this.mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    public void setLocationAccess(boolean locationAccess)
    {
        initLocationUpdateRequest();
        this.locationAccess = locationAccess;
    }

    @SuppressLint("MissingPermission") // this SuppressLint is not important - permission is accessed before call requestLocationUpdates
    public void startUpdatingLocation() {

        boolean chl = checkIsLocationProviderEnable();
        if (!chl)return;
        cachedLocation = PreferenceHelper.getInstance().getCachedLocation();
        if (locationAccess)this.mFusedLocationClient.requestLocationUpdates(this.locationRequest,
                this.locationCallback, Looper.myLooper());
    }

    public void stopUpdatingLocation() {

        if (currentLocation != null)
            PreferenceHelper.getInstance().setCachedLocation(currentLocation);

        if (this.mFusedLocationClient != null && this.locationCallback != null)
            this.mFusedLocationClient.removeLocationUpdates(this.locationCallback);
    }

    //is location service enable
    private boolean checkIsLocationProviderEnable()
    {
        int locationMode = 0;
        String locationProviders;

        boolean location_enabled;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }

            location_enabled = locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            location_enabled =  !TextUtils.isEmpty(locationProviders);
        }

        if(!location_enabled) {

            locationManagerInterface.checkIsLocationProviderEnable();
        }

        return location_enabled;
    }
}
