package com.kumail.tvshows.tmdb;

/**
 * Created by kumail on 13/11/2017.
 */

public class ImageUrlGen
{
	private String filePath;
	private String secureUrl;
	private String fileSize;

	public String getUrl(ShowImagesResponse sir, ImageConfigResponse icr)
	{
		filePath = sir.getBackdrops().get(0).getFilePath();
		secureUrl = icr.getImages().getSecureBaseUrl();
		fileSize = icr.getImages().getBackdropSizes().get(2);

		return secureUrl + fileSize + filePath;

	}
//
//	public String getUrl(String secureUrl, String fileSize, String filePath)
//	{
//		return url;
//	}
}
