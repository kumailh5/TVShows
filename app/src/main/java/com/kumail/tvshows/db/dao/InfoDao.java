package com.kumail.tvshows.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.kumail.tvshows.db.entity.InfoEntity;

@Dao
public interface InfoDao {
    @Query("SELECT * FROM InfoTable WHERE tmdb_id = :tmdbId")
    InfoEntity loadShow(int tmdbId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(InfoEntity infoEntity);
}
