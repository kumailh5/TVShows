package com.kumail.tvshows.discover.trending.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kumail.tvshows.trakt.data.Show;

/**
 * Created by kumail on 12/11/2017.
 */

public class TrendingResponse
{
	@SerializedName("watchers")
	@Expose
	private Integer watchers;
	@SerializedName("show")
	@Expose
	private Show show;

	public Integer getWatchers() {
		return watchers;
	}

	public void setWatchers(Integer watchers) {
		this.watchers = watchers;
	}

	public Show getShow() {
		return show;
	}

	public void setShow(Show show) {
		this.show = show;
	}
}
