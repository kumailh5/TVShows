package com.kumail.tvshows;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kumail on 27/11/2017.
 */

public class SearchResponse
{
	private String type;
	@SerializedName("score")
	@Expose
	private Double score;
	@SerializedName("show")
	@Expose
	private Show show;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Show getShow() {
		return show;
	}

	public void setShow(Show show) {
		this.show = show;
	}
}
