package com.kumail.tvshows.tmdb;

/**
 * Created by kumail on 13/11/2017.
 */

public class ImgUrlGen
{
	public String getUrl(ShowImagesResponse sir, ImgConfResp icr)
	{
		String filePath = sir.getBackdrops().get(0).getFilePath();
		String secureUrl = icr.getImages().getSecureBaseUrl();
		String fileSize = icr.getImages().getBackdropSizes().get(2);

		return secureUrl + fileSize + filePath;

	}
}
