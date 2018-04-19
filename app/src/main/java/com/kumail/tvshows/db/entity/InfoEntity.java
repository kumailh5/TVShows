package com.kumail.tvshows.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "InfoTable")
public class InfoEntity {
    @PrimaryKey
    @ColumnInfo(name = "tmdb_id")
    private int tmdbId;
    private String name;
    private String posterUrl;
    private String firstAirDate;
    private Double voteAverage;
    private String overview;

    public int getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(int tmdbId) {
        this.tmdbId = tmdbId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public InfoEntity() {
    }

    public InfoEntity(int tmdbId, String name, String posterUrl, String firstAirDate, Double voteAverage, String overview) {
        this.tmdbId = tmdbId;
        this.name = name;
        this.posterUrl = posterUrl;
        this.firstAirDate = firstAirDate;
        this.voteAverage = voteAverage;
        this.overview = overview;
    }
}
