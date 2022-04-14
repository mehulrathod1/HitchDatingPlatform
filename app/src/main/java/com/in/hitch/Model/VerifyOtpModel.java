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
    }
}
