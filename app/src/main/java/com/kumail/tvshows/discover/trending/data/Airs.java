package com.kumail.tvshows.discover.trending.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kumail on 13/11/2017.
 */

public class Airs
{
	@SerializedName("day")
	@Expose
	private String day;
	@SerializedName("time")
	@Expose
	private String time;
	@SerializedName("timezone")
	@Expose
	private String timezone;

	public String getDay()
	{
		return day;
	}

	public void setDay(String day)
	{
		this.day = day;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public String getTimezone()
	{
		return timezone;
	}

	public void setTimezone(String timezone)
	{
		this.timezone = timezone;
	}
}
