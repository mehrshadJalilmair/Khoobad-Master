package com.mehrshad.khoobad.Model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;

public class VenueLocation
{
    @SerializedName("address")
    private String address;

    @SerializedName("lat")
    private Double lat;

    @SerializedName("lng")
    private Double lng;

    @SerializedName("distance")
    private Double distance;

    @SerializedName("formattedAddress")
    private ArrayList<String> formattedAddress;

    public ArrayList<String> getFormattedAddress() {
        return formattedAddress;
    }

    public String getDistance() {
        return String.valueOf(distance.intValue()) + " متر";
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public String getAddress() {

        String address = this.address;
        ArrayList<String> tempElements = getFormattedAddress();

        if (tempElements != null)
        {
            if (!tempElements.isEmpty())

                Collections.reverse(tempElements);
                address = TextUtils.join("," , tempElements);

        }
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public void setFormattedAddress(ArrayList<String> formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}