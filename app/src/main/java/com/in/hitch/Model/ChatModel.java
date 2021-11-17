package com.in.hitch.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ChatModel {

    @SerializedName("status")
    @Expose
    String status;
    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("data")
    @Expose
    List<ChatModel.ChatModelData> chatDataList = new ArrayList<>();

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

    public List<ChatModelData> getChatDataList() {
        return chatDataList;
    }

    public void setChatDataList(List<ChatModelData> chatDataList) {
        this.chatDataList = chatDataList;
    }

    public static class ChatModelData {

        @SerializedName("chat_id")
        @Expose
        String chat_id;
        @SerializedName("user_id")
        @Expose
        String user_id;
        @SerializedName("image")
        @Expose
        String image;
        @SerializedName("username")
        @Expose
        String username;

        public String getChat_id() {
            return chat_id;
        }

        public void setChat_id(String chat_id) {
            this.chat_id = chat_id;
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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getUnread_msg() {
            return unread_msg;
        }

        public void setUnread_msg(String unread_msg) {
            this.unread_msg = unread_msg;
        }

        @SerializedName("message")
        @Expose
        String message;
        @SerializedName("datetime")
        @Expose
        String datetime;

        @SerializedName("unread_msg")
        @Expose
        String unread_msg;


        public ChatModelData(String chat_id, String user_id, String image, String username, String message, String datetime, String unread_msg) {
            this.chat_id = chat_id;
            this.user_id = user_id;
            this.image = image;
            this.username = username;
            this.message = message;
            this.datetime = datetime;
            this.unread_msg = unread_msg;
        }
    }
}


