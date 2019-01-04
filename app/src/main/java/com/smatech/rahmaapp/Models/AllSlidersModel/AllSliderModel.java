package com.smatech.rahmaapp.Models.AllSlidersModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AllSliderModel {

    @SerializedName("sliders")
    @Expose
    private ArrayList<SliderModel> sliders = null;
    @SerializedName("status")
    @Expose
    private Boolean status;

    public ArrayList<SliderModel> getSliders() {
        return sliders;
    }

    public void setSliders(ArrayList<SliderModel> sliders) {
        this.sliders = sliders;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
