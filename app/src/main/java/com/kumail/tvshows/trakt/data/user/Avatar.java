package com.kumail.tvshows.trakt.data.user;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Avatar {

    @SerializedName("full")
    @Expose
    private String full;

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

}
