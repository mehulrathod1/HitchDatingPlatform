package com.in.hitch.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MembershipPlaneModel {

    @SerializedName("status")
    @Expose
    String status;
    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("data")
    @Expose
    List<PlaneData> planeData = new ArrayList<>();

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

    public List<PlaneData> getPlaneData() {
        return planeData;
    }

    public void setPlaneData(List<PlaneData> planeData) {
        this.planeData = planeData;
    }

    public static class PlaneData {


        @SerializedName("plan_id")
        @Expose
        String plan_id;


        @SerializedName("plan_name")
        @Expose
        String plan_name;

        @SerializedName("duration_in_days")
        @Expose
        String duration_in_days;

        @SerializedName("amount")
        @Expose
        String amount;

        @SerializedName("unhitch_rewind_your_swipe")
        @Expose
        String unhitch_rewind_your_swipe;

        @SerializedName("travel_to_hitch_around_world")
        @Expose
        String travel_to_hitch_around_world;

        @SerializedName("unlimited_right_swipes")
        @Expose
        String unlimited_right_swipes;

        @SerializedName("no_sds")
        @Expose
        String no_sds;

        @SerializedName("hide_distance")
        @Expose
        String hide_distance;

        @SerializedName("hide_age")
        @Expose
        String hide_age;


        @SerializedName("appear_in_top_hitches")
        @Expose
        String appear_in_top_hitches;

        @SerializedName("know_in_advance_who_likes_you")
        @Expose
        String know_in_advance_who_likes_you;

        @SerializedName("direct_message_without_match")
        @Expose
        String direct_message_without_match;

        public String getPlan_id() {
            return plan_id;
        }

        public void setPlan_id(String plan_id) {
            this.plan_id = plan_id;
        }

        public String getPlan_name() {
            return plan_name;
        }

        public void setPlan_name(String plan_name) {
            this.plan_name = plan_name;
        }

        public String getDuration_in_days() {
            return duration_in_days;
        }

        public void setDuration_in_days(String duration_in_days) {
            this.duration_in_days = duration_in_days;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getUnhitch_rewind_your_swipe() {
            return unhitch_rewind_your_swipe;
        }

        public void setUnhitch_rewind_your_swipe(String unhitch_rewind_your_swipe) {
            this.unhitch_rewind_your_swipe = unhitch_rewind_your_swipe;
        }

        public String getTravel_to_hitch_around_world() {
            return travel_to_hitch_around_world;
        }

        public void setTravel_to_hitch_around_world(String travel_to_hitch_around_world) {
            this.travel_to_hitch_around_world = travel_to_hitch_around_world;
        }

        public String getUnlimited_right_swipes() {
            return unlimited_right_swipes;
        }

        public void setUnlimited_right_swipes(String unlimited_right_swipes) {
            this.unlimited_right_swipes = unlimited_right_swipes;
        }

        public String getNo_sds() {
            return no_sds;
        }

        public void setNo_sds(String no_sds) {
            this.no_sds = no_sds;
        }

        public String getHide_distance() {
            return hide_distance;
        }

        public void setHide_distance(String hide_distance) {
            this.hide_distance = hide_distance;
        }

        public String getHide_age() {
            return hide_age;
        }

        public void setHide_age(String hide_age) {
            this.hide_age = hide_age;
        }

        public String getAppear_in_top_hitches() {
            return appear_in_top_hitches;
        }

        public void setAppear_in_top_hitches(String appear_in_top_hitches) {
            this.appear_in_top_hitches = appear_in_top_hitches;
        }

        public String getKnow_in_advance_who_likes_you() {
            return know_in_advance_who_likes_you;
        }

        public void setKnow_in_advance_who_likes_you(String know_in_advance_who_likes_you) {
            this.know_in_advance_who_likes_you = know_in_advance_who_likes_you;
        }

        public String getDirect_message_without_match() {
            return direct_message_without_match;
        }

        public void setDirect_message_without_match(String direct_message_without_match) {
            this.direct_message_without_match = direct_message_without_match;
        }

        public PlaneData(String plan_id, String plan_name, String duration_in_days, String amount, String unhitch_rewind_your_swipe, String travel_to_hitch_around_world, String unlimited_right_swipes, String no_sds, String hide_distance, String hide_age, String appear_in_top_hitches, String know_in_advance_who_likes_you, String direct_message_without_match) {
            this.plan_id = plan_id;
            this.plan_name = plan_name;
            this.duration_in_days = duration_in_days;
            this.amount = amount;
            this.unhitch_rewind_your_swipe = unhitch_rewind_your_swipe;
            this.travel_to_hitch_around_world = travel_to_hitch_around_world;
            this.unlimited_right_swipes = unlimited_right_swipes;
            this.no_sds = no_sds;
            this.hide_distance = hide_distance;
            this.hide_age = hide_age;
            this.appear_in_top_hitches = appear_in_top_hitches;
            this.know_in_advance_who_likes_you = know_in_advance_who_likes_you;
            this.direct_message_without_match = direct_message_without_match;
        }
    }
}
