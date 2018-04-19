package com.kumail.tvshows.trakt.data.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kumail.tvshows.trakt.data.Show;

public class WatchedResponse {

    @SerializedName("plays")
    @Expose
    private Integer plays;
    @SerializedName("last_watched_at")
    @Expose
    private String lastWatchedAt;
    @SerializedName("show")
    @Expose
    private Show show;

    public Integer getPlays() {
        return plays;
    }

    public void setPlays(Integer plays) {
        this.plays = plays;
    }

    public String getLastWatchedAt() {
        return lastWatchedAt;
    }

    public void setLastWatchedAt(String lastWatchedAt) {
        this.lastWatchedAt = lastWatchedAt;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public WatchedResponse() {
    }


}
