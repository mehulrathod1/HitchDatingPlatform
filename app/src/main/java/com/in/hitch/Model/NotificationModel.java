package com.in.hitch.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NotificationModel {


    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("data")
    @Expose
    List<NotificationData> notificationList = new ArrayList<>();

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

    public List<NotificationData> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<NotificationData> notificationList) {
        this.notificationList = notificationList;
    }

    public static class NotificationData {


        @SerializedName("username")
        @Expose
        String username;

        @SerializedName("profile_img")
        @Expose
        String profile_img;


        @SerializedName("notification")
        @Expose
        String notification;


        @SerializedName("time")
        @Expose
        String time;

        public NotificationData(String username, String profile_img, String notification, String time) {
            this.username = username;
            this.profile_img = profile_img;
            this.notification = notification;
            this.time = time;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getProfile_img() {
            return profile_img;
        }

        public void setProfile_img(String profile_img) {
            this.profile_img = profile_img;
        }

        public String getNotification() {
            return notification;
        }

        public void setNotification(String notification) {
            this.notification = notification;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}