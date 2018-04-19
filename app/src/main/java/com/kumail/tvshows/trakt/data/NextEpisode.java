package com.kumail.tvshows.trakt.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NextEpisode {

    @SerializedName("season")
    @Expose
    private Integer season;
    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("ids")
    @Expose
    private Ids ids;

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Ids getIds() {
        return ids;
    }

    public void setIds(Ids ids) {
        this.ids = ids;
    }

}
