package com.smatech.rahmaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetImagesURLModel {


    public GetImagesURLModel(ArrayList<String> images) {
        this.images = images;
    }

    @SerializedName("images")
    @Expose
    private ArrayList<String> images = null;

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

}
