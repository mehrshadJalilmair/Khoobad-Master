package com.mehrshad.khoobad.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

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