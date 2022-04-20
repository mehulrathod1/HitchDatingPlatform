package com.in.hitch.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class WhoLikesYouModel {

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("data")
    @Expose
    List<WhoLikesYou> chatDataList = new ArrayList<>();

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

    public List<WhoLikesYou> getChatDataList() {
        return chatDataList;
    }

    public void setChatDataList(List<WhoLikesYou> chatDataList) {
        this.chatDataList = chatDataList;
    }

    public static class WhoLikesYou {

        @SerializedName("user_id")
        @Expose
        String user_id;

        @SerializedName("first_name")
        @Expose
        String first_name;

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

        public WhoLikesYou(String user_id, String first_name, String last_name, String age, String km_diff, String image) {
            this.user_id = user_id;
            this.first_name = first_name;
            this.last_name = last_name;
            this.age = age;
            this.km_diff = km_diff;
            this.image = image;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
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

        public String getKm_diff() {
            return km_diff;
        }

        public void setKm_diff(String km_diff) {
            this.km_diff = km_diff;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}
