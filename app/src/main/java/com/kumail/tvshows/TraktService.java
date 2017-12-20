package com.kumail.tvshows;

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

	@GET("shows/{slug}?extended=full")
	Call<ShowExtendedResponse> getShowDetails(@Path("slug") String slug);

	@GET("search/show")
	Call<List<SearchResponse>> getSearchResults(@Query("query") String query);

}
