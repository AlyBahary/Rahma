package com.smatech.rahmaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgetPassModel {
    @SerializedName("status")
    @Expose
    private Boolean status;

    public String getMessage() {
        return message;
    }

    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }
}
