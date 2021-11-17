package com.in.hitch.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetUserImageModel {

    @SerializedName("status")
    boolean status;
    @SerializedName("message")
    String message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<GetImageModel> getList() {
        return list;
    }

    public void setList(List<GetImageModel> list) {
        this.list = list;
    }

    @SerializedName("data")
    List<GetImageModel> list = new ArrayList<>();

    public static class GetImageModel {

        @SerializedName("id")
        String id;

        public GetImageModel(String  id, String image_name) {
            this.id = id;
            this.image_name = image_name;
        }

        public String isId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImage_name() {
            return image_name;
        }

        public void setImage_name(String image_name) {
            this.image_name = image_name;
        }

        @SerializedName("image_name")
        String image_name;


    }

}

