package com.kumail.tvshows.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.kumail.tvshows.db.entity.UserEntity;

@Dao
public interface UserDao {
    @Query("SELECT * FROM UserTable")
    LiveData<UserEntity> loadUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserEntity userEntity);
}
