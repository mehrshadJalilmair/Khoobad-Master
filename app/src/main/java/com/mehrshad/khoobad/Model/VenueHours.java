package com.mehrshad.khoobad.Model;

import com.google.gson.annotations.SerializedName;

public class VenueHours {

    @SerializedName("status")
    private String status;

    @SerializedName("isOpen")
    private Boolean isOpen;

    public Boolean getOpen() {
        return isOpen;
    }

    public String getStatus() {
        return status;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
