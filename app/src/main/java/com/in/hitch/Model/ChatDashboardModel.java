package com.in.hitch.Model;

import java.util.ArrayList;
import java.util.List;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatDashboardModel {

    @SerializedName("status")
    @Expose
    String status;
    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("data")
    @Expose
    List<ChatDashboardModel.ChatDashboardList> chatDataList = new ArrayList<>();

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

    public List<ChatDashboardList> getChatDataList() {
        return chatDataList;
    }

    public void setChatDataList(List<ChatDashboardList> chatDataList) {
        this.chatDataList = chatDataList;
    }

    public static class ChatDashboardList {

        @SerializedName("msg_id")
        @Expose
        String msg_id;

        @SerializedName("user_id")
        @Expose
        String user_id;

        @SerializedName("image")
        @Expose
        String image;

        @SerializedName("username")
        @Expose
        String username;

        @SerializedName("message")
        @Expose
        String message;

        @SerializedName("time")
        @Expose
        String time;

        @SerializedName("type")
        @Expose
        String type;

        @SerializedName("msg_type")
        @Expose
        String msg_type;



        public String getMsg_id() {
            return msg_id;
        }

        public void setMsg_id(String msg_id) {
            this.msg_id = msg_id;
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

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }


        public String getMsg_type() {
            return msg_type;
        }

        public void setMsg_type(String msg_type) {
            this.msg_type = msg_type;
        }

        public ChatDashboardList(String msg_id, String user_id, String image, String username, String message, String time, String type, String msg_type) {
            this.msg_id = msg_id;
            this.user_id = user_id;
            this.image = image;
            this.username = username;
            this.message = message;
            this.time = time;
            this.type = type;
            this.msg_type = msg_type;
        }
    }
}
