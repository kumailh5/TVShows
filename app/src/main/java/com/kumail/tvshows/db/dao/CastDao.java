package com.kumail.tvshows.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.kumail.tvshows.db.entity.CastEntity;

import java.util.List;

@Dao
public interface CastDao
{
	@Query("SELECT * FROM CastTable WHERE show_id = :showId ORDER BY col_order ASC ")
	List<CastEntity> loadCast(int showId);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insert(CastEntity castEntity);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insertAll(List<CastEntity> castEntities);
}
