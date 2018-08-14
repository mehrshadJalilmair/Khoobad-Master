package com.mehrshad.khoobad.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class VenueDetails implements Serializable {

    @SerializedName("meta")
    private Meta meta;

    @SerializedName("response")
    private Response response;

    public Response getResponse() {
        return response;
    }

    public Meta getMeta() {
        return meta;
    }
    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public ArrayList<String> getPhotosUrls()
    {
        return getResponse().getVenue().getPhotos().getAllPhotosUrls();
    }

    public ArrayList<VenueCategory> getCategories()
    {
        return getResponse().getVenue().getCategories();
    }

    public class Meta {

        @SerializedName("code")
        private String code;

        public String getCode() {
            return code;
        }
        public void setCode(String code) {
            this.code = code;
        }
    }

    public class Response {

        @SerializedName("venue")
        private VenueDS venue;

        public VenueDS getVenue() {
            return venue;
        }

        public class VenueDS extends BaseVenue
        {
            @SerializedName("contact")
            private VenueContact contact;

            @SerializedName("verified")
            private Boolean verified;

            @SerializedName("description")
            private String description;

            @SerializedName("hours")
            private VenueHours hours;

            public VenueContact getContact() {
                return contact;
            }

            public Boolean getVerified() {

                if (verified != null)
                    return verified;
                return false;
            }

            public void setVerified(Boolean verified) {
                this.verified = verified;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public VenueHours getHours() {
                return hours;
            }

        }
    }
}
