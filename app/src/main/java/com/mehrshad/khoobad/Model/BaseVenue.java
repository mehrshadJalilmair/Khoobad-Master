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
    private Double rating;

    @SerializedName("ratingColor")
    private String ratingColor;

    @SerializedName("photos")
    private VenuePhotos photos;

    public ArrayList<VenueCategory> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<VenueCategory> categories) {
        this.categories = categories;
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

    public void setLocation(VenueLocation location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRating() {
        return rating;
    }

    public String getRatingColor() {
        return ratingColor;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setRatingColor(String ratingColor) {
        this.ratingColor = ratingColor;
    }

    public VenuePhotos getPhotos() {
        return photos;
    }

    public void setPhotos(VenuePhotos photos) {
        this.photos = photos;
    }
}
