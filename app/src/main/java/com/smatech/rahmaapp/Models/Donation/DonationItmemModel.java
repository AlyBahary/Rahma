package com.smatech.rahmaapp.Models.Donation;

import com.smatech.rahmaapp.Models.AllAdresses.getAllAdressModel1;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DonationItmemModel {


    public DonationItmemModel(String id, String description, String fromId, String type, String image, String addressId, String status, String toId, String category, String created, String updated, String from, getAllAdressModel1 address) {

        this.id = id;
        this.description = description;
        this.fromId = fromId;
        this.type = type;
        this.image = image;
        this.addressId = addressId;
        this.status = status;
        this.toId = toId;
        this.category = category;
        this.created = created;
        this.updated = updated;
        this.from = from;
        this.address = address;
    }

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("from_id")
    @Expose
    private String fromId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("address_id")
    @Expose
    private String addressId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("to_id")
    @Expose
    private String toId;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("updated")
    @Expose
    private String updated;
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("address")
    @Expose
    private getAllAdressModel1 address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public getAllAdressModel1 getAddress() {
        return address;
    }

    public void setAddress(getAllAdressModel1 address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
