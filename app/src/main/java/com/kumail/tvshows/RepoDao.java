package com.kumail.tvshows;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by kumail on 25/03/2018.
 */

@Dao
public interface RepoDao
{
	@Query("SELECT * FROM repo")
	List<Repo> getAllRepos();

	@Insert
	void insert(Repo... repos);

	@Update
	void update(Repo... repos);

	@Delete
	void delete(Repo... repos);
}