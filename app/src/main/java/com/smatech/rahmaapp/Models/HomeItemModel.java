package com.smatech.rahmaapp.Models;




public class HomeItemModel {
    private int imageView;
    private String textView;

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public String getTextView() {
        return textView;
    }

    public void setTextView(String textView) {
        this.textView = textView;
    }

    public HomeItemModel(int imageView, String textView) {

        this.imageView = imageView;
        this.textView = textView;
    }
}
