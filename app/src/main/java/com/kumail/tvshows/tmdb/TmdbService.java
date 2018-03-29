package com.kumail.tvshows.tmdb;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by kumail on 13/11/2017.
 */

public interface TmdbService
{
	@GET("tv/{tv_id}/images?api_key=0145e269d6f9647893ca62fcde80871e")
	Call<ShowImagesResponse> getImages(@Path("tv_id") String tmdbId);

	@GET("tv/{tv_id}?api_key=0145e269d6f9647893ca62fcde80871e")
	Call<ShowDetailsResponse> getShowDetails(@Path("tv_id") String tmdbId);


	@GET("configuration?api_key=0145e269d6f9647893ca62fcde80871e")
	Call<ImgConfResp> getConfig();
}
