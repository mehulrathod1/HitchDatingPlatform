package com.in.hitch.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ProfileCardModel {

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("data")
    @Expose
    List<ProfileCard> profileCardList = new ArrayList<>();

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

    public List<ProfileCard> getProfileCardList() {
        return profileCardList;
    }

    public void setProfileCardList(List<ProfileCard> profileCardList) {
        this.profileCardList = profileCardList;
    }

    public static class ProfileCard {

        @SerializedName("id")
        @Expose
        String id;


        @SerializedName("user_name")
        @Expose
        String user_name;


        @SerializedName("profile")
        @Expose
        String profile;


        @SerializedName("age")
        @Expose
        String age;


        @SerializedName("job_title")
        @Expose
        String job_title;

        @SerializedName("liked")
        @Expose
        String liked;


        @SerializedName("favorite")
        @Expose
        String favorite;

        @SerializedName("superlike")
        @Expose
        String superLike;


        public ProfileCard(String id, String user_name, String profile, String age, String job_title, String liked, String favorite, String superLike) {
            this.id = id;
            this.user_name = user_name;
            this.profile = profile;
            this.age = age;
            this.job_title = job_title;
            this.liked = liked;
            this.favorite = favorite;
            this.superLike = superLike;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getJob_title() {
            return job_title;
        }

        public void setJob_title(String job_title) {
            this.job_title = job_title;
        }

        public String getLiked() {
            return liked;
        }

        public void setLiked(String liked) {
            this.liked = liked;
        }

        public String getFavorite() {
            return favorite;
        }

        public void setFavorite(String favorite) {
            this.favorite = favorite;
        }

        public String getSuperLike() {
            return superLike;
        }

        public void setSuperLike(String superLike) {
            this.superLike = superLike;
        }
    }
}
