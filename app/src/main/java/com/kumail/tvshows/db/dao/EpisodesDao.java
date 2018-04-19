package com.kumail.tvshows.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.kumail.tvshows.db.entity.EpisodesEntity;

import java.util.List;

@Dao
public interface EpisodesDao {
    @Query("SELECT * FROM EpisodesTable WHERE tmdb_id = :tmdbId")
    LiveData<List<EpisodesEntity>> loadEpisodes(int tmdbId);

    @Query("SELECT * FROM EpisodesTable WHERE tmdb_id = :tmdbId")
    List<EpisodesEntity> loadEpisode(int tmdbId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EpisodesEntity episodesEntity);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<EpisodesEntity> episodesEntities);

}
