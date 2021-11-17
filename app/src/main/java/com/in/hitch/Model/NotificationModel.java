package com.in.hitch.Model;

public class NotificationModel {

    int notificationItemImage;
    String notificationText,date;

    public int getNotificationItemImage() {
        return notificationItemImage;
    }

    public void setNotificationItemImage(int notificationItemImage) {
        this.notificationItemImage = notificationItemImage;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public NotificationModel(int notificationItemImage, String notificationText, String date) {
        this.notificationItemImage = notificationItemImage;
        this.notificationText = notificationText;
        this.date = date;
    }
}
