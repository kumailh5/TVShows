package com.kumail.tvshows.trakt.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Episode {

    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("completed")
    @Expose
    private Boolean completed;
    @SerializedName("last_watched_at")
    @Expose
    private Object lastWatchedAt;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Object getLastWatchedAt() {
        return lastWatchedAt;
    }

    public void setLastWatchedAt(Object lastWatchedAt) {
        this.lastWatchedAt = lastWatchedAt;
    }

}

