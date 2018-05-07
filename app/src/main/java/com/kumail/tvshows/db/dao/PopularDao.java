package com.kumail.tvshows.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.kumail.tvshows.db.entity.PopularEntity;

import java.util.List;

@Dao
public interface PopularDao
{
	@Query("SELECT * FROM PopularShows")
	LiveData<List<PopularEntity>> loadPopularShows();

	@Query("SELECT * FROM PopularShows WHERE tmdb_id = :tmdbId")
	PopularEntity loadShow(int tmdbId);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insert(PopularEntity popularEntity);
}
