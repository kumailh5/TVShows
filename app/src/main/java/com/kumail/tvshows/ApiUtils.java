package com.kumail.tvshows;

import com.kumail.tvshows.tmdb.TmdbService;
import com.kumail.tvshows.trakt.TraktService;

/**
 * Created by kumail on 12/11/2017.
 */

public class ApiUtils
{

	private static final String BASE_URL_TRAKT = "https://api.trakt.tv/";
	private static final String BASE_URL_TMDB = "https://api.themoviedb.org/3/";

	public static TraktService getTraktService()
	{
		return RetrofitClient.getTraktClient(BASE_URL_TRAKT).create(TraktService.class);
	}

	public static TraktService getOAuthTraktService(String accessToken)
	{
		return RetrofitClient.getOAuthTraktClient(BASE_URL_TRAKT, accessToken).create(TraktService.class);
	}

	public static TmdbService getTmdbService()
	{
		return RetrofitClient.getTmdbClient(BASE_URL_TMDB).create(TmdbService.class);
	}

}
