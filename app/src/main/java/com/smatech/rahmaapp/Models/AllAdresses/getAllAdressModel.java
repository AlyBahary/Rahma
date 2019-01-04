package com.smatech.rahmaapp.Models.AllAdresses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class getAllAdressModel {
    @SerializedName("addresses")
    @Expose
    private ArrayList<getAllAdressModel1> addresses = null;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("count")
    @Expose
    private Integer count;

    public getAllAdressModel(ArrayList<getAllAdressModel1> addresses, Boolean status, Integer count) {
        this.addresses = addresses;
        this.status = status;
        this.count = count;
    }

    public ArrayList<getAllAdressModel1> getAddresses() {
        return addresses;
    }

    public void setAddresses(ArrayList<getAllAdressModel1> addresses) {
        this.addresses = addresses;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
