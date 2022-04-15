package com.in.hitch.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MyMatchesModel {

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("data")
    @Expose
    List<MatchesData> matchesData = new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MatchesData> getMatchesData() {
        return matchesData;
    }

    public void setMatchesData(List<MatchesData> matchesData) {
        this.matchesData = matchesData;
    }

    public static class MatchesData {

        @SerializedName("user_id")
        @Expose
        String user_id;



        @SerializedName("first_name")
        @Expose
        String title;

        @SerializedName("last_name")
        @Expose
        String last_name;


        @SerializedName("age")
        @Expose
        String age;


        @SerializedName("km_diff")
        @Expose
        String km_diff;


        @SerializedName("image")
        @Expose
        String image;


        public MatchesData(String user_id, String title, String last_name, String age, String km_diff, String image) {
            this.user_id = user_id;
            this.title = title;
            this.last_name = last_name;
            this.age = age;
            this.km_diff = km_diff;
            this.image = image;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getKm_diff() {
            return km_diff;
        }

        public void setKm_diff(String km_diff) {
            this.km_diff = km_diff;
        }


    }
}