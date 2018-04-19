package com.kumail.tvshows.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "CastTable")
public class CastEntity
{
	@PrimaryKey
	@ColumnInfo(name = "id")
	private int id;
	private String character;
	private String name;
	@ColumnInfo(name = "col_order")
	private int order;
	private String profileUrl;
	@ColumnInfo(name = "show_id")
	private int showId;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getCharacter()
	{
		return character;
	}

	public void setCharacter(String character)
	{
		this.character = character;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Integer getOrder()
	{
		return order;
	}

	public void setOrder(Integer order)
	{
		this.order = order;
	}

	public String getProfileUrl()
	{
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl)
	{
		this.profileUrl = profileUrl;
	}

	public int getShowId()
	{
		return showId;
	}

	public void setShowId(int showId)
	{
		this.showId = showId;
	}

	public CastEntity()
	{
	}

	public CastEntity(int id,
					  String character,
					  String name,
					  int order,
					  String profileUrl,
					  int showId)
	{
		this.id = id;
		this.character = character;
		this.name = name;
		this.order = order;
		this.profileUrl = profileUrl;
		this.showId = showId;
	}


}
