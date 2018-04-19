package com.kumail.tvshows.trakt.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HiddenSeason {

    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("ids")
    @Expose
    private Ids ids;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Ids getIds() {
        return ids;
    }

    public void setIds(Ids ids) {
        this.ids = ids;
    }

}
