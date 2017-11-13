package com.kumail.tvshows;

import com.kumail.tvshows.discover.trending.data.RatingsApiResponse;
import com.kumail.tvshows.discover.trending.data.ShowExtendedResponse;
import com.kumail.tvshows.discover.trending.data.TrendingResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by kumail on 12/11/2017.
 */

public interface TraktService
{
	@GET("shows/trending")
	Call<List<TrendingResponse>> getTrending();

	@GET("shows/{slug}/ratings")
	Call<RatingsApiResponse> getRatings(@Path("slug") String slug);

	@GET("shows/{slug}?extended=full")
	Call<ShowExtendedResponse> getShowDetails(@Path("slug") String slug);


//	https://api.trakt.tv/shows/game-of-thrones/ratings
//	@GET("users/{username}")
//
//
//	Call<User> getUser(@Path("username") String username);

}
