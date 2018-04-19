package com.kumail.tvshows.tmdb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kumail.tvshows.tmdb.credits.Cast;
import com.kumail.tvshows.tmdb.credits.Crew;

import java.util.List;

public class Credits {
	@SerializedName("cast")
	@Expose
	private List<Cast> cast = null;
	@SerializedName("crew")
	@Expose
	private List<Crew> crew = null;
	@SerializedName("id")
	@Expose
	private Integer id;

	public List<Cast> getCast() {
		return cast;
	}

	public void setCast(List<Cast> cast) {
		this.cast = cast;
	}

	public List<Crew> getCrew() {
		return crew;
	}

	public void setCrew(List<Crew> crew) {
		this.crew = crew;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
