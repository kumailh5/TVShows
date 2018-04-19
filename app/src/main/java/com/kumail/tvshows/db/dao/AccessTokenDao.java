package com.kumail.tvshows.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.kumail.tvshows.trakt.auth.AccessTokenResponse;

@Dao
public interface AccessTokenDao {

    @Query("SELECT * FROM AccessToken")
    LiveData<AccessTokenResponse> loadAccessToken();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AccessTokenResponse accessTokenResponse);
}
