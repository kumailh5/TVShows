package com.kumail.tvshows.trakt.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EpisodeDetailedResponse {

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
    @SerializedName("number_abs")
    @Expose
    private Object numberAbs;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("first_aired")
    @Expose
    private String firstAired;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("rating")
    @Expose
    private Double rating;
    @SerializedName("votes")
    @Expose
    private Integer votes;
    @SerializedName("comment_count")
    @Expose
    private Integer commentCount;
    @SerializedName("available_translations")
    @Expose
    private List<String> availableTranslations = null;
    @SerializedName("runtime")
    @Expose
    private Integer runtime;

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

    public Object getNumberAbs() {
        return numberAbs;
    }

    public void setNumberAbs(Object numberAbs) {
        this.numberAbs = numberAbs;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getFirstAired() {
        return firstAired;
    }

    public void setFirstAired(String firstAired) {
        this.firstAired = firstAired;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public List<String> getAvailableTranslations() {
        return availableTranslations;
    }

    public void setAvailableTranslations(List<String> availableTranslations) {
        this.availableTranslations = availableTranslations;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

}