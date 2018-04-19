package com.kumail.tvshows.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "EpisodesTable")
public class EpisodesEntity {
    @PrimaryKey
    @NonNull
    private String id;
    @ColumnInfo(name = "tmdb_id")
    private int tmdbId;
    private String slug;
    private int season;
    private int number;
    private int type;
    private boolean episodeCompleted;
    private int aired;
    private int completed;
    private String title;
    private String overview;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTmdbId() {
        return tmdbId;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setTmdbId(int tmdbId) {
        this.tmdbId = tmdbId;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean getEpisodeCompleted() {
        return episodeCompleted;
    }

    public void setEpisodeCompleted(boolean episodeCompleted) {
        this.episodeCompleted = episodeCompleted;
    }

    public int getAired() {
        return aired;
    }

    public void setAired(int aired) {
        this.aired = aired;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public EpisodesEntity(String id, int tmdbId, String slug, int season, int number, int type, boolean episodeCompleted, int aired, int completed, String title, String overview) {
        this.id = id;
        this.tmdbId = tmdbId;
        this.slug = slug;
        this.season = season;
        this.number = number;
        this.type = type;
        this.episodeCompleted = episodeCompleted;
        this.aired = aired;
        this.completed = completed;
        this.title = title;
        this.overview = overview;
    }


}
