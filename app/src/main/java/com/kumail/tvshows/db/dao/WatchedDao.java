package com.kumail.tvshows.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.kumail.tvshows.db.entity.WatchedEntity;

import java.util.List;

@Dao
public interface WatchedDao {

    @Query("SELECT * FROM WatchedShows")
    LiveData<List<WatchedEntity>> loadWatchedShows();

//    @Query("SELECT * FROM WatchedShows WHERE tmdbId = :id")
//    WatchedEntity loadShow(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WatchedEntity watchedEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<WatchedEntity> watchedEntities);

}
