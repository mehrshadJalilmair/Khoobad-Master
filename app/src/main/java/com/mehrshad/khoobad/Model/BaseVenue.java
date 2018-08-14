package com.mehrshad.khoobad.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BaseVenue {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("categories")
    private ArrayList<VenueCategory> categories;

    @SerializedName("location")
    private VenueLocation location;

    @SerializedName("rating")
    private double rating;

    @SerializedName("ratingColor")
    private String ratingColor;

    @SerializedName("photos")
    private VenuePhotos photos;

    public ArrayList<VenueCategory> getCategories() {
        return categories;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VenueLocation getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public VenuePhotos getPhotos() {
        return photos;
    }

}
