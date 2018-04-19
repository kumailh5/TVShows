package com.kumail.tvshows.trakt;

import com.kumail.tvshows.SearchResponse;
import com.kumail.tvshows.discover.popular.data.PopularResponse;
import com.kumail.tvshows.discover.trending.data.ShowExtendedResponse;
import com.kumail.tvshows.discover.trending.data.TrendingResponse;
import com.kumail.tvshows.trakt.auth.AccessTokenRequestBody;
import com.kumail.tvshows.trakt.auth.RefreshTokenRequestBody;
import com.kumail.tvshows.trakt.auth.RevokeAccessTokenBody;
import com.kumail.tvshows.trakt.data.EpisodeDetailedResponse;
import com.kumail.tvshows.trakt.data.WatchedDetailedResponse;
import com.kumail.tvshows.trakt.data.user.UserSettingsResponse;
import com.kumail.tvshows.trakt.data.user.WatchedResponse;
import com.kumail.tvshows.trakt.data.user.WatchlistResponse;

import java.util.List;

import de.rheinfabrik.heimdall.OAuth2AccessToken;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by kumail on 12/11/2017.
 */

public interface TraktService {
    @GET("shows/trending")
    Call<List<TrendingResponse>> getTrending();

    @GET("shows/popular")
    Call<List<PopularResponse>> getPopular();

    @GET("shows/{slug}?extended=full")
    Call<ShowExtendedResponse> getShowDetails(@Path("slug") String slug);

    @GET("search/show")
    Call<List<SearchResponse>> getSearchResults(@Query("query") String query);

    @GET("users/settings")
    Call<UserSettingsResponse> getUserSettings();

    @GET("users/{username}/watchlist/shows")
    Call<List<WatchlistResponse>> getWatchlist(@Path("username") String username);

    @GET("shows/{id}/progress/watched")
    Call<WatchedDetailedResponse> getWatchedDetailed(@Path("id") String showId);

    @GET("shows/{slug}/seasons/{season}/episodes/{episode}?extended=full")
    Call<EpisodeDetailedResponse> getEpisodeDetailed(@Path("slug") String slug,
                                                     @Path("season") int seasonNumber,
                                                     @Path("episode") int episodeNumber
                                                     );


    @GET("users/{username}/watched/shows?extended=noseasons")
    Call<List<WatchedResponse>> getWatched(@Path("username") String username);

    //Authentication calls
    @POST("/oauth/token")
    Observable<OAuth2AccessToken> grantNewAccessToken(@Body AccessTokenRequestBody body);

    @POST("/oauth/token")
    Observable<OAuth2AccessToken> refreshAccessToken(@Body RefreshTokenRequestBody body);

    @POST("/oauth/revoke")
    Observable<Void> revokeAccessToken(@Body RevokeAccessTokenBody body);

}
