package com.kumail.tvshows.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "WatchedShows")
public class WatchedEntity {

    @PrimaryKey
    private Integer traktId;
    private Integer tmdbId;
    private Integer plays;
    private Integer totalEpisodes;
    private String name;
    private String backdropUrl;

    public Integer getTraktId() {
        return traktId;
    }

    public void setTraktId(Integer traktId) {
        this.traktId = traktId;
    }

    public Integer getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(Integer tmdbId) {
        this.tmdbId = tmdbId;
    }

    public Integer getPlays() {
        return plays;
    }

    public void setPlays(Integer plays) {
        this.plays = plays;
    }

    public Integer getTotalEpisodes() {
        return totalEpisodes;
    }

    public void setTotalEpisodes(Integer totalEpisodes) {
        this.totalEpisodes = totalEpisodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackdropUrl() {
        return backdropUrl;
    }

    public void setBackdropUrl(String backdropUrl) {
        this.backdropUrl = backdropUrl;
    }

    public WatchedEntity() {
    }

    public WatchedEntity(Integer traktId, Integer tmdbId, Integer plays, Integer totalEpisodes, String name, String backdropUrl) {
        this.traktId = traktId;
        this.tmdbId = tmdbId;
        this.plays = plays;
        this.totalEpisodes = totalEpisodes;
        this.name = name;
        this.backdropUrl = backdropUrl;
    }


}
