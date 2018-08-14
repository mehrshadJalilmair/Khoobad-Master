package com.mehrshad.khoobad.Model;

import com.google.gson.annotations.SerializedName;
import com.mehrshad.khoobad.Util.GeneralFunctions;

import java.util.ArrayList;

public class VenuePhotos
{
    @SerializedName("count")
    private Integer count;

    @SerializedName("groups")
    private ArrayList<Group> groups;

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }

    public ArrayList<String> getAllPhotosUrls()
    {
        ArrayList<String> urls = new ArrayList<>();

        for (Group g:
             getGroups()) {
            for (Group.Item i:g.getItems()) {

                urls.add(i.getUrl());
            }
        }
        return urls;
    }

    public class Group
    {
        @SerializedName("items")
        private ArrayList<Item> items;

        public ArrayList<Item> getItems() {
            return items;
        }

        public void setItems(ArrayList<Item> items) {
            this.items = items;
        }

        public class Item
        {
            @SerializedName("prefix")
            private String prefix;

            @SerializedName("suffix")
            private String suffix;

            @SerializedName("visibility")
            private String visibility;

            public String getUrl()
            {
                return prefix + GeneralFunctions.getSize().get(0) + "x" + (int)(0.4*GeneralFunctions.getSize().get(1)) + suffix;
            }

            public String getSuffix() {
                return suffix;
            }

            public String getVisibility() {
                return visibility;
            }

            public void setSuffix(String suffix) {
                this.suffix = suffix;
            }

            public void setVisibility(String visibility) {
                this.visibility = visibility;
            }
        }
    }
}