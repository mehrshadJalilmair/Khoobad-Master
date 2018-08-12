package com.mehrshad.khoobad.Model;

import com.google.gson.annotations.SerializedName;

public class Venue {

    @SerializedName("venue")
    private BaseVenue venue;

    public BaseVenue getBaseVenue() {
        return venue;
    }
    public void setBaseVenue(BaseVenue venue) {
        this.venue = venue;
    }
}
