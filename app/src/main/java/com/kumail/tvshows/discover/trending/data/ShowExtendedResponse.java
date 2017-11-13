package com.kumail.tvshows.discover.trending.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kumail on 13/11/2017.
 */

public class ShowExtendedResponse
{
	@SerializedName("title")
	@Expose
	private String title;
	@SerializedName("year")
	@Expose
	private Integer year;
	@SerializedName("ids")
	@Expose
	private Ids ids;
	@SerializedName("overview")
	@Expose
	private String overview;
	@SerializedName("first_aired")
	@Expose
	private String firstAired;
	@SerializedName("airs")
	@Expose
	private Airs airs;
	@SerializedName("runtime")
	@Expose
	private Integer runtime;
	@SerializedName("certification")
	@Expose
	private String certification;
	@SerializedName("network")
	@Expose
	private String network;
	@SerializedName("country")
	@Expose
	private String country;
	@SerializedName("trailer")
	@Expose
	private String trailer;
	@SerializedName("homepage")
	@Expose
	private String homepage;
	@SerializedName("status")
	@Expose
	private String status;
	@SerializedName("rating")
	@Expose
	private Double rating;
	@SerializedName("votes")
	@Expose
	private Integer votes;
	@SerializedName("updated_at")
	@Expose
	private String updatedAt;
	@SerializedName("language")
	@Expose
	private String language;
	@SerializedName("available_translations")
	@Expose
	private List<String> availableTranslations = null;
	@SerializedName("genres")
	@Expose
	private List<String> genres = null;
	@SerializedName("aired_episodes")
	@Expose
	private Integer airedEpisodes;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Ids getIds() {
		return ids;
	}

	public void setIds(Ids ids) {
		this.ids = ids;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getFirstAired() {
		return firstAired;
	}

	public void setFirstAired(String firstAired) {
		this.firstAired = firstAired;
	}

	public Airs getAirs() {
		return airs;
	}

	public void setAirs(Airs airs) {
		this.airs = airs;
	}

	public Integer getRuntime() {
		return runtime;
	}

	public void setRuntime(Integer runtime) {
		this.runtime = runtime;
	}

	public String getCertification() {
		return certification;
	}

	public void setCertification(String certification) {
		this.certification = certification;
	}

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getTrailer() {
		return trailer;
	}

	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

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

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public List<String> getAvailableTranslations() {
		return availableTranslations;
	}

	public void setAvailableTranslations(List<String> availableTranslations) {
		this.availableTranslations = availableTranslations;
	}

	public List<String> getGenres() {
		return genres;
	}

	public void setGenres(List<String> genres) {
		this.genres = genres;
	}

	public Integer getAiredEpisodes() {
		return airedEpisodes;
	}

	public void setAiredEpisodes(Integer airedEpisodes) {
		this.airedEpisodes = airedEpisodes;
	}
}
