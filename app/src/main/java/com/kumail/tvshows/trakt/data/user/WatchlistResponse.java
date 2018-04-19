package com.kumail.tvshows.trakt.data.user;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kumail.tvshows.trakt.data.Show;

public class WatchlistResponse {

    @SerializedName("rank")
    @Expose
    private Integer rank;
    @SerializedName("listed_at")
    @Expose
    private String listedAt;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("show")
    @Expose
    private Show show;

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getListedAt() {
        return listedAt;
    }

    public void setListedAt(String listedAt) {
        this.listedAt = listedAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

}