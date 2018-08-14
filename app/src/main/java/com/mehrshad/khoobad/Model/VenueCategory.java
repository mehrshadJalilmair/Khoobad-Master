package com.mehrshad.khoobad.Model;

import com.google.gson.annotations.SerializedName;

public class VenueCategory
{
    @SerializedName("id")
    private String id;

    @SerializedName("pluralName")
    private String pluralName;

    @SerializedName("name")
    private String name;

    @SerializedName("icon")
    private Icon icon;

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public class Icon
    {
        @SerializedName("suffix")
        private String suffix;

        @SerializedName("prefix")
        private String prefix;

        public String getCatUrl()
        {
            return prefix + "100" + suffix;
        }

        public String getSuffix() {
            return suffix;
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }

    }
}