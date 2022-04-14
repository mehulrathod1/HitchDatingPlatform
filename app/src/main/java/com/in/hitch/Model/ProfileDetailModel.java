package com.in.hitch.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ProfileDetailModel {

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("data")
    @Expose
    ProfileDetail profileDetail;

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

    public ProfileDetail getProfileDetail() {
        return profileDetail;
    }

    public void setProfileDetail(ProfileDetail profileDetail) {
        this.profileDetail = profileDetail;
    }

    public static class ProfileDetail {


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


        @SerializedName("gender")
        @Expose
        String gender;


        @SerializedName("job_title")
        @Expose
        String job_title;


        @SerializedName("school_name")
        @Expose
        String school_name;


        @SerializedName("current_location")
        @Expose
        String current_location;


        @SerializedName("image")
        @Expose
        List<ImageList> imageLists = new ArrayList<>();

        public List<ImageList> getImageLists() {
            return imageLists;
        }

        public void setImageLists(List<ImageList> imageLists) {
            this.imageLists = imageLists;
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

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getJob_title() {
            return job_title;
        }

        public void setJob_title(String job_title) {
            this.job_title = job_title;
        }

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public String getCurrent_location() {
            return current_location;
        }

        public void setCurrent_location(String current_location) {
            this.current_location = current_location;
        }

        public static class ImageList {

            @SerializedName("id")
            @Expose
            String id;


            @SerializedName("image_name")
            @Expose
            String image_name;

            public ImageList(String image_name) {
                this.image_name = image_name;
            }

            public String getId() {
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
        }
    }
}
