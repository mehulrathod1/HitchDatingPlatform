package com.in.hitch.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyOtpModel {

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;


    @SerializedName("data")
    @Expose
    VerifyData verifyData;

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

    public VerifyData getVerifyData() {
        return verifyData;
    }

    public void setVerifyData(VerifyData verifyData) {
        this.verifyData = verifyData;
    }

    public class VerifyData {


        @SerializedName("is_mobile_verfied")
        @Expose
        String is_mobile_verify;

        @SerializedName("is_completed_profile")
        @Expose
        String is_completed_profile;

        @SerializedName("is_membership")
        @Expose
        String is_membership;

        @SerializedName("plan_name")
        @Expose
        String plan_name;

        public String getIs_mobile_verify() {
            return is_mobile_verify;
        }

        public void setIs_mobile_verify(String is_mobile_verify) {
            this.is_mobile_verify = is_mobile_verify;
        }

        public String getIs_completed_profile() {
            return is_completed_profile;
        }

        public void setIs_completed_profile(String is_completed_profile) {
            this.is_completed_profile = is_completed_profile;
        }

        public String getIs_membership() {
            return is_membership;
        }

        public void setIs_membership(String is_membership) {
            this.is_membership = is_membership;
        }

        public String getPlan_name() {
            return plan_name;
        }

        public void setPlan_name(String plan_name) {
            this.plan_name = plan_name;
        }
    }
}
