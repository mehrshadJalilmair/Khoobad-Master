package com.mehrshad.khoobad.Model;

import com.google.gson.annotations.SerializedName;

public class VenueHours {

    @SerializedName("isOpen")
    private Boolean isOpen;

    public Boolean getOpen() {
        return isOpen;
    }
}
