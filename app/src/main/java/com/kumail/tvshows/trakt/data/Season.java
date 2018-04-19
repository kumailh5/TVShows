package com.kumail.tvshows.trakt.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Season {

    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("aired")
    @Expose
    private Integer aired;
    @SerializedName("completed")
    @Expose
    private Integer completed;
    @SerializedName("episodes")
    @Expose
    private List<Episode> episodes = null;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getAired() {
        return aired;
    }

    public void setAired(Integer aired) {
        this.aired = aired;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

}
