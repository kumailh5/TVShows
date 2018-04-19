package com.kumail.tvshows.trakt.data.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Connections {

    @SerializedName("facebook")
    @Expose
    private Boolean facebook;
    @SerializedName("twitter")
    @Expose
    private Boolean twitter;
    @SerializedName("google")
    @Expose
    private Boolean google;
    @SerializedName("tumblr")
    @Expose
    private Boolean tumblr;
    @SerializedName("medium")
    @Expose
    private Boolean medium;
    @SerializedName("slack")
    @Expose
    private Boolean slack;

    public Boolean getFacebook() {
        return facebook;
    }

    public void setFacebook(Boolean facebook) {
        this.facebook = facebook;
    }

    public Boolean getTwitter() {
        return twitter;
    }

    public void setTwitter(Boolean twitter) {
        this.twitter = twitter;
    }

    public Boolean getGoogle() {
        return google;
    }

    public void setGoogle(Boolean google) {
        this.google = google;
    }

    public Boolean getTumblr() {
        return tumblr;
    }

    public void setTumblr(Boolean tumblr) {
        this.tumblr = tumblr;
    }

    public Boolean getMedium() {
        return medium;
    }

    public void setMedium(Boolean medium) {
        this.medium = medium;
    }

    public Boolean getSlack() {
        return slack;
    }

    public void setSlack(Boolean slack) {
        this.slack = slack;
    }

}
