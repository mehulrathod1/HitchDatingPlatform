package com.in.hitch.Model;
import androidx.lifecycle.LifecycleService;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TransactionModel {


    @SerializedName("status")
    @Expose
    String status;
    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("data")
    @Expose
    List<TransactionModel.TransactionHistory> paymentHistoryData = new ArrayList<>();

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

    public List<TransactionHistory> getPaymentHistoryData() {
        return paymentHistoryData;
    }

    public void setPaymentHistoryData(List<TransactionHistory> paymentHistoryData) {
        this.paymentHistoryData = paymentHistoryData;
    }

    public static class TransactionHistory{

        @SerializedName("transaction_id")
        @Expose
        String transaction_id;
        @SerializedName("description")
        @Expose
        String description;
        @SerializedName("amount")
        @Expose
        String amount;
        @SerializedName("date")
        @Expose
        String date;

        public String getTransaction_id() {
            return transaction_id;
        }

        public void setTransaction_id(String transaction_id) {
            this.transaction_id = transaction_id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public TransactionHistory(String transaction_id, String description, String amount, String date) {
            this.transaction_id = transaction_id;
            this.description = description;
            this.amount = amount;
            this.date = date;
        }
    }


}
