
package com.smatech.rahmaapp.Models.NewAdress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddNewAdress {

    @SerializedName("address")
    @Expose
    private AddNewAdress1 address;
    @SerializedName("status")
    @Expose
    private Boolean status;

    public AddNewAdress(AddNewAdress1 address, Boolean status) {
        this.address = address;
        this.status = status;
    }

    public AddNewAdress1 getAddress() {
        return address;
    }

    public void setAddress(AddNewAdress1 address) {
        this.address = address;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
