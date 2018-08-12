package com.mehrshad.khoobad.Model;

import com.google.gson.annotations.SerializedName;

public class VenueContact {

    @SerializedName("phone")
    private String phone;

    @SerializedName("formattedPhone")
    private String formattedPhone;

    public String getFormattedPhone() {
        return formattedPhone;
    }

    public String getPhone() {
        return phone;
    }

    public void setFormattedPhone(String formattedPhone) {
        this.formattedPhone = formattedPhone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}