package com.smatech.rahmaapp.Models.AllAdresses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getAllAdressModel1 {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("block_no")
    @Expose
    private String blockNo;
    @SerializedName("floor_no")
    @Expose
    private String floorNo;
    @SerializedName("house_no")
    @Expose
    private String houseNo;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("updated")
    @Expose
    private String updated;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("organization")
    @Expose
    private String organization;

    public getAllAdressModel1(String id, String title, String address, String longitude, String latitude, String cityId, String description, String type, String blockNo, String floorNo, String houseNo, String mobile, String created, String updated, String userId, String organization) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.cityId = cityId;
        this.description = description;
        this.type = type;
        this.blockNo = blockNo;
        this.floorNo = floorNo;
        this.houseNo = houseNo;
        this.mobile = mobile;
        this.created = created;
        this.updated = updated;
        this.userId = userId;
        this.organization = organization;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBlockNo() {
        return blockNo;
    }

    public void setBlockNo(String blockNo) {
        this.blockNo = blockNo;
    }

    public String getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(String floorNo) {
        this.floorNo = floorNo;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}
