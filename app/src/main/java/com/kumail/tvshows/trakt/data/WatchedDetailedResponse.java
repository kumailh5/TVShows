package com.kumail.tvshows.trakt.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WatchedDetailedResponse {

    @SerializedName("aired")
    @Expose
    private Integer aired;
    @SerializedName("completed")
    @Expose
    private Integer completed;
    @SerializedName("last_watched_at")
    @Expose
    private String lastWatchedAt;
    @SerializedName("seasons")
    @Expose
    private List<Season> seasons = null;
    @SerializedName("hidden_seasons")
    @Expose
    private List<HiddenSeason> hiddenSeasons = null;
    @SerializedName("next_episode")
    @Expose
    private NextEpisode nextEpisode;
    @SerializedName("last_episode")
    @Expose
    private LastEpisode lastEpisode;

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

    public String getLastWatchedAt() {
        return lastWatchedAt;
    }

    public void setLastWatchedAt(String lastWatchedAt) {
        this.lastWatchedAt = lastWatchedAt;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    public List<HiddenSeason> getHiddenSeasons() {
        return hiddenSeasons;
    }

    public void setHiddenSeasons(List<HiddenSeason> hiddenSeasons) {
        this.hiddenSeasons = hiddenSeasons;
    }

    public NextEpisode getNextEpisode() {
        return nextEpisode;
    }

    public void setNextEpisode(NextEpisode nextEpisode) {
        this.nextEpisode = nextEpisode;
    }

    public LastEpisode getLastEpisode() {
        return lastEpisode;
    }

    public void setLastEpisode(LastEpisode lastEpisode) {
        this.lastEpisode = lastEpisode;
    }

}