package com.kumail.tvshows.tmdb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kumail.tvshows.tmdb.Backdrop;
import com.kumail.tvshows.tmdb.Poster;

import java.util.List;

/**
 * Created by kumail on 13/11/2017.
 */

public class ShowImagesResponse
{
	@SerializedName("backdrops")
	@Expose
	private List<Backdrop> backdrops = null;
	@SerializedName("id")
	@Expose
	private Integer id;
	@SerializedName("posters")
	@Expose
	private List<Poster> posters = null;

	public List<Backdrop> getBackdrops() {
		return backdrops;
	}

	public void setBackdrops(List<Backdrop> backdrops) {
		this.backdrops = backdrops;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Poster> getPosters() {
		return posters;
	}

	public void setPosters(List<Poster> posters) {
		this.posters = posters;
	}

}
