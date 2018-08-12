package com.mehrshad.khoobad.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Venues {

    @SerializedName("meta")
    private Meta meta;

    @SerializedName("response")
    private Response response;

    public Response getResponse() {
        return response;
    }
    public void setResponse(Response response) {
        this.response = response;
    }

    public Meta getMeta() {
        return meta;
    }
    public void setMeta(Meta meta) {
        this.meta = meta;
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

        @SerializedName("totalResults")
        private Integer totalResults;

        @SerializedName("groups")
        private ArrayList<Group>groups;

        public Integer getTotalResults() {
            return totalResults;
        }
        public void setTotalResults(Integer totalResults) {
            this.totalResults = totalResults;
        }

        public ArrayList<Group> getGroups() {
            return groups;
        }
        public void setGroups(ArrayList<Group> groups) {
            this.groups = groups;
        }

        public class Group {

            @SerializedName("items")
            private ArrayList<Venue> items;

            public ArrayList<Venue> getItems() {
                return items;
            }

            public void setItems(ArrayList<Venue> items) {
                this.items = items;
            }
        }
    }
}
