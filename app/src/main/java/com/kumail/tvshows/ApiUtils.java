package com.kumail.tvshows;

/**
 * Created by kumail on 12/11/2017.
 */

public class ApiUtils {

	public static final String BASE_URL_TRAKT = "https://api.trakt.tv/";

	public static TraktService getTraktService() {
		return RetrofitClient.getClient(BASE_URL_TRAKT).create(TraktService.class);
	}
}
