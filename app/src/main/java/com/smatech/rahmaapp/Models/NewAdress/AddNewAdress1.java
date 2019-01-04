
package com.smatech.rahmaapp.Models.NewAdress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddNewAdress1 {

    @SerializedName("Address")
    @Expose
    private AddNewAdress2 address;
    @SerializedName("id")
    @Expose
    private String id;

    public AddNewAdress1(AddNewAdress2 address, String id) {
        this.address = address;
        this.id = id;
    }

    public AddNewAdress2 getAddress() {
        return address;
    }

    public void setAddress(AddNewAdress2 address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
