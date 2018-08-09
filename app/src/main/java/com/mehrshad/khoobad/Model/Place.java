package com.mehrshad.khoobad.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Place {

    @SerializedName("venue")
    private Venue venue;

    public Venue getVenue() {
        return venue;
    }
    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public class Venue
    {
        @SerializedName("id")
        private String id;

        @SerializedName("name")
        private String name;

        @SerializedName("categories")
        private ArrayList<Category> categories;

        @SerializedName("location")
        private Location location;

        @SerializedName("contact")
        private Contact contact;

        @SerializedName("verified")
        private Boolean verified;

        @SerializedName("rating")
        private Double rating;

        @SerializedName("ratingColor")
        private String ratingColor;

        @SerializedName("url")
        private String url;

        @SerializedName("description")
        private String description;

        @SerializedName("hours")
        private Hours hours;

        @SerializedName("photos")
        private Photos photos;

        public ArrayList<Category> getCategories() {
            return categories;
        }

        public void setCategories(ArrayList<Category> categories) {
            this.categories = categories;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Contact getContact() {
            return contact;
        }

        public void setContact(Contact contact) {
            this.contact = contact;
        }

        public Double getRating() {
            return rating;
        }

        public String getRatingColor() {
            return ratingColor;
        }

        public String getUrl() {
            return url;
        }

        public Boolean getVerified() {
            return verified;
        }

        public void setRating(Double rating) {
            this.rating = rating;
        }

        public void setRatingColor(String ratingColor) {
            this.ratingColor = ratingColor;
        }

        public void setUrl(String url) {
            this.url = url;
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

        public Hours getHours() {
            return hours;
        }

        public void setHours(Hours hours) {
            this.hours = hours;
        }

        public Photos getPhotos() {
            return photos;
        }

        public void setPhotos(Photos photos) {
            this.photos = photos;
        }

        public class Location
        {
            @SerializedName("address")
            private String address;

            @SerializedName("lat")
            private Double lat;

            @SerializedName("lng")
            private Double lng;

            @SerializedName("distance")
            private Double distance;

            @SerializedName("formattedAddress")
            private ArrayList<String>formattedAddress;

            public ArrayList<String> getFormattedAddress() {
                return formattedAddress;
            }

            public String getDistance() {
                return String.valueOf(distance.intValue()) + " متر";
            }

            public Double getLat() {
                return lat;
            }

            public Double getLng() {
                return lng;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public void setDistance(Double distance) {
                this.distance = distance;
            }

            public void setFormattedAddress(ArrayList<String> formattedAddress) {
                this.formattedAddress = formattedAddress;
            }

            public void setLat(Double lat) {
                this.lat = lat;
            }

            public void setLng(Double lng) {
                this.lng = lng;
            }
        }

        public class Category
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

            public String getPluralName() {
                return pluralName;
            }

            public void setIcon(Icon icon) {
                this.icon = icon;
            }

            public void setPluralName(String pluralName) {
                this.pluralName = pluralName;
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

                public String getSuffix() {
                    return suffix;
                }

                public String getPrefix() {
                    return prefix;
                }

                public void setSuffix(String suffix) {
                    this.suffix = suffix;
                }

                public void setPrefix(String prefix) {
                    this.prefix = prefix;
                }
            }
        }

        public class Contact {

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

        public class Hours {

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

        public class Photos
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

                    public String getPrefix() {
                        return prefix;
                    }

                    public String getSuffix() {
                        return suffix;
                    }

                    public String getVisibility() {
                        return visibility;
                    }

                    public void setPrefix(String prefix) {
                        this.prefix = prefix;
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
    }
}
