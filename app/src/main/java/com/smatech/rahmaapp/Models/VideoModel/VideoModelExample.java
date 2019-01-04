
package com.smatech.rahmaapp.Models.VideoModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoModelExample {

    @SerializedName("video")
    @Expose
    private VideoModel video;
    @SerializedName("status")
    @Expose
    private Boolean status;

    public VideoModel getVideo() {
        return video;
    }

    public void setVideo(VideoModel video) {
        this.video = video;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
