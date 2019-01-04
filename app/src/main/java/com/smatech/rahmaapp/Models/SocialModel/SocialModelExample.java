
package com.smatech.rahmaapp.Models.SocialModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SocialModelExample {

    @SerializedName("setting")
    @Expose
    private SocialModel setting;
    @SerializedName("status")
    @Expose
    private Boolean status;

    public SocialModel getSetting() {
        return setting;
    }

    public void setSetting(SocialModel setting) {
        this.setting = setting;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
