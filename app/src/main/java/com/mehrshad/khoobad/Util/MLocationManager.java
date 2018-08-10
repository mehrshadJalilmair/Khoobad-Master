package com.mehrshad.khoobad.Util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.mehrshad.khoobad.Khoobad;

public class MLocationManager {

    public interface LocationManagerInterface
    {
        void requestLocationAccess();
        void lastKnownLocation(Location currentLocation);
    }

    private LocationManagerInterface locationManagerInterface;

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private boolean locationAccess;

    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 60000;
    private static final long SMALLEST_UPDATE_DISPLACEMENT_IN_METERS = 100;

    public MLocationManager(Activity activity, LocationManagerInterface locationManagerInterface) {
        this.locationManagerInterface = locationManagerInterface;
        initLocationUpdateRequest();
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            locationManagerInterface.requestLocationAccess();

        }
    }

    public void setLocationAccess(boolean locationAccess)
    {
        this.locationAccess = locationAccess;
    }

    private void initLocationUpdateRequest()
    {
        this.locationRequest = new LocationRequest();
        this.locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        this.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        this.locationRequest.setSmallestDisplacement(SMALLEST_UPDATE_DISPLACEMENT_IN_METERS);

        this.locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                Location currentLocation = locationResult.getLastLocation();
                locationManagerInterface.lastKnownLocation(currentLocation);
            }
        };
        this.mFusedLocationClient = LocationServices.getFusedLocationProviderClient(Khoobad.context);
    }

    @SuppressLint("MissingPermission") // this SuppressLint is not important - permission is accessed before call requestLocationUpdates
    public void startUpdatingLocation() {

        if (locationAccess)this.mFusedLocationClient.requestLocationUpdates(this.locationRequest,
                this.locationCallback, Looper.myLooper());
    }

    public void stopUpdatingLocation() {

        if (!locationAccess)
            if (this.mFusedLocationClient != null && this.locationCallback != null)
                this.mFusedLocationClient.removeLocationUpdates(this.locationCallback);
    }
}
