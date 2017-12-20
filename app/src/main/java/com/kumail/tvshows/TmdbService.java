package com.kumail.tvshows;

import com.kumail.tvshows.tmdb.ImageConfigResponse;
import com.kumail.tvshows.tmdb.ShowImagesResponse;

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

	@GET("configuration?api_key=0145e269d6f9647893ca62fcde80871e")
	Call<ImageConfigResponse> getConfig();
}
