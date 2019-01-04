package com.smatech.rahmaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegistrationModel {

    @SerializedName("user")
    @Expose
    private UserModel user;

    public RegistrationModel(UserModel user, String message, Boolean status) {
        this.user = user;
        this.message = message;
        this.status = status;
    }

    public UserModel getUser() {

        return user;
    }

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Boolean status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
