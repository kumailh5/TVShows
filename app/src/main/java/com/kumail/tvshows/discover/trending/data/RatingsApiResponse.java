package com.kumail.tvshows.discover.trending.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kumail on 12/11/2017.
 */

public class RatingsApiResponse
{
	@SerializedName("rating")
	@Expose
	private Double rating;
	@SerializedName("votes")
	@Expose
	private Integer votes;
	@SerializedName("distribution")
	@Expose
	private Distribution distribution;

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Integer getVotes() {
		return votes;
	}

	public void setVotes(Integer votes) {
		this.votes = votes;
	}

	public Distribution getDistribution() {
		return distribution;
	}

	public void setDistribution(Distribution distribution) {
		this.distribution = distribution;
	}
}
