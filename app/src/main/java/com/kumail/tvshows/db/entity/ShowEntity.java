package com.kumail.tvshows.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by kumail on 29/03/2018.
 */

@Entity(tableName = "Shows")
public class ShowEntity
{
	@PrimaryKey
	private int id;
	private String name;
	private String backdropPath;
	private Double voteAverage;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getBackdropPath()
	{
		return backdropPath;
	}

	public void setBackdropPath(String backdropPath)
	{
		this.backdropPath = backdropPath;
	}

	public Double getVoteAverage()
	{
		return voteAverage;
	}

	public void setVoteAverage(Double voteAverage)
	{
		this.voteAverage = voteAverage;
	}

	public ShowEntity()
	{
	}

	public ShowEntity(int id, String name, String backdropPath, Double voteAverage)
	{
		this.id = id;
		this.name = name;
		this.backdropPath = backdropPath;
		this.voteAverage = voteAverage;
	}
}
