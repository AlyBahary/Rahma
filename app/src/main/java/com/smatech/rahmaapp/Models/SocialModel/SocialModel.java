
package com.smatech.rahmaapp.Models.SocialModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SocialModel {

    @SerializedName("id")
    @Expose
    private String id;
  @SerializedName("bantext_ar")
    @Expose
    private String bantext_ar;

    public String getBantext_ar() {
        return bantext_ar;
    }

    public void setBantext_ar(String bantext_ar) {
        this.bantext_ar = bantext_ar;
    }

    public String getBantext() {
        return bantext;
    }

    public void setBantext(String bantext) {
        this.bantext = bantext;
    }

    @SerializedName("bantext")
    @Expose
    private String bantext;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("how_to_use")
    @Expose
    private String howToUse;
    @SerializedName("facebook")
    @Expose
    private String facebook;
    @SerializedName("slide_text")
    @Expose
    private String slideText;
    @SerializedName("updated")
    @Expose
    private String updated;
    @SerializedName("about_ar")
    @Expose
    private String aboutAr;
    @SerializedName("how_to_use_ar")
    @Expose
    private String howToUseAr;
    @SerializedName("slide_text_ar")
    @Expose
    private String slideTextAr;
    @SerializedName("twitter")
    @Expose
    private String twitter;
    @SerializedName("snapchat")
    @Expose
    private String snapchat;
    @SerializedName("instagram")
    @Expose
    private String instagram;
    @SerializedName("whatsapp")
    @Expose
    private String whatsapp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getHowToUse() {
        return howToUse;
    }

    public void setHowToUse(String howToUse) {
        this.howToUse = howToUse;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getSlideText() {
        return slideText;
    }

    public void setSlideText(String slideText) {
        this.slideText = slideText;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getAboutAr() {
        return aboutAr;
    }

    public void setAboutAr(String aboutAr) {
        this.aboutAr = aboutAr;
    }

    public String getHowToUseAr() {
        return howToUseAr;
    }

    public void setHowToUseAr(String howToUseAr) {
        this.howToUseAr = howToUseAr;
    }

    public String getSlideTextAr() {
        return slideTextAr;
    }

    public void setSlideTextAr(String slideTextAr) {
        this.slideTextAr = slideTextAr;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getSnapchat() {
        return snapchat;
    }

    public void setSnapchat(String snapchat) {
        this.snapchat = snapchat;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

}
