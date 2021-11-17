package com.in.hitch.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PurchasePlaneModel {
    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    PurchasePlaneData message;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PurchasePlaneData getMessage() {
        return message;
    }

    public void setMessage(PurchasePlaneData message) {
        this.message = message;
    }

    public class PurchasePlaneData{

        @SerializedName("status")
        @Expose
        String status;

        @SerializedName("message")
        @Expose
        String message;

        @SerializedName("url_razorpay")
        @Expose
        String url_razorpay;

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

        public String getUrl_razorpay() {
            return url_razorpay;
        }

        public void setUrl_razorpay(String url_razorpay) {
            this.url_razorpay = url_razorpay;
        }
    }
}
