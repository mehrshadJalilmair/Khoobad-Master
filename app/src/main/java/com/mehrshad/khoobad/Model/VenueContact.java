package com.mehrshad.khoobad.Model;

import com.google.gson.annotations.SerializedName;

public class VenueContact {

    @SerializedName("phone")
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}