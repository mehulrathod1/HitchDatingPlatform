package com.in.hitch.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetUserFilterModel {


    @SerializedName("status")
    @Expose
    String status;
    @SerializedName("message")
    @Expose
    String message;

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

    public List<GetFilter> getGetFilterList() {
        return getFilterList;
    }

    public void setGetFilterList(List<GetFilter> getFilterList) {
        this.getFilterList = getFilterList;
    }

    @SerializedName("data")
    @Expose
    List<GetFilter> getFilterList = new ArrayList<>();



    public class GetFilter {

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getInterested_id() {
            return interested_id;
        }

        public void setInterested_id(String interested_id) {
            this.interested_id = interested_id;
        }

        public String getInterested_name() {
            return interested_name;
        }

        public void setInterested_name(String interested_name) {
            this.interested_name = interested_name;
        }

        public String getReligion_id() {
            return religion_id;
        }

        public void setReligion_id(String religion_id) {
            this.religion_id = religion_id;
        }

        public String getReligion_name() {
            return religion_name;
        }

        public void setReligion_name(String religion_name) {
            this.religion_name = religion_name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getStart_age() {
            return start_age;
        }

        public void setStart_age(String start_age) {
            this.start_age = start_age;
        }

        public String getEnd_age() {
            return end_age;
        }

        public void setEnd_age(String end_age) {
            this.end_age = end_age;
        }

        public String getSexual_id() {
            return sexual_id;
        }

        public void setSexual_id(String sexual_id) {
            this.sexual_id = sexual_id;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getStart_distance() {
            return start_distance;
        }

        public void setStart_distance(String start_distance) {
            this.start_distance = start_distance;
        }

        public String getEnd_distance() {
            return end_distance;
        }

        public void setEnd_distance(String end_distance) {
            this.end_distance = end_distance;
        }

        @SerializedName("id")
        @Expose
        String id;

        @SerializedName("interested_id")
        @Expose
        String interested_id;

        @SerializedName("interested_name")
        @Expose
        String interested_name;

        @SerializedName("religion_id")
        @Expose
        String religion_id;

        @SerializedName("religion_name")
        @Expose
        String religion_name;

        @SerializedName("age")
        @Expose
        String age;
        @SerializedName("start_age")
        @Expose
        String start_age;

        @SerializedName("end_age")
        @Expose
        String end_age;

        @SerializedName("sexual_id")
        @Expose
        String sexual_id;

        @SerializedName("distance")
        @Expose
        String distance;

        @SerializedName("start_distance")
        @Expose
        String start_distance;

        @SerializedName("end_distance")
        @Expose
        String end_distance;


    }
}
