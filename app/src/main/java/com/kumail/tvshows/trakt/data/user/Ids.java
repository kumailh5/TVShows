package com.kumail.tvshows.trakt.data.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ids {

    @SerializedName("slug")
    @Expose
    private String slug;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

}
