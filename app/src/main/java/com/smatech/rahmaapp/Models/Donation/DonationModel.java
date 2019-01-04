package com.smatech.rahmaapp.Models.Donation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DonationModel {

    @SerializedName("donations")
    @Expose
    private ArrayList<DonationItmemModel> donations = null;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("count")
    @Expose
    private Integer count;

    public ArrayList<DonationItmemModel> getDonations() {
        return donations;
    }

    public void setDonations(ArrayList<DonationItmemModel> donations) {
        this.donations = donations;
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
