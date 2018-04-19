package com.kumail.tvshows.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.kumail.tvshows.db.entity.TrendingEntity;

import java.util.List;

@Dao
public interface TrendingDao
{
	@Query("SELECT * FROM TrendingShows")
	LiveData<List<TrendingEntity>> loadTrendingShows();

	@Query("SELECT * FROM TrendingShows WHERE tmdb_id = :tmdbId")
	TrendingEntity loadShow(int tmdbId);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insert(TrendingEntity trendingEntity);
}
