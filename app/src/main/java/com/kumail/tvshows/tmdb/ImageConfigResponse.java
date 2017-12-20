package com.kumail.tvshows.tmdb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kumail on 13/11/2017.
 */

public class ImageConfigResponse
{
	@SerializedName("images")
	@Expose
	private Images images;
	@SerializedName("change_keys")
	@Expose
	private List<String> changeKeys = null;

	public Images getImages() {
		return images;
	}

	public void setImages(Images images) {
		this.images = images;
	}

	public List<String> getChangeKeys() {
		return changeKeys;
	}

	public void setChangeKeys(List<String> changeKeys) {
		this.changeKeys = changeKeys;
	}
}
