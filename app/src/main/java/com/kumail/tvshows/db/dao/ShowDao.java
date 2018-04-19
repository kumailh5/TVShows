package com.kumail.tvshows.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.kumail.tvshows.db.entity.ShowEntity;

import java.util.List;

/**
 * Created by kumail on 29/03/2018.
 */

@Dao
public interface ShowDao
{
	@Query("SELECT * FROM shows")
	LiveData<List<ShowEntity>> loadShows();

//	@Query("SELECT * FROM shows")
//	LiveData<List<ShowDetailsResponse>> loadShows();

//	@Query("SELECT * FROM comments where productId = :productId")
//	List<ShowEntity> loadCommentsSync(int productId);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insertAll(List<ShowEntity> shows);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insert(ShowEntity show);

//	@Insert(onConflict = OnConflictStrategy.REPLACE)
//	void insert(ShowDetailsResponse show);
}
