package com.kumail.tvshows.trakt.auth;

import com.google.gson.Gson;
import com.kumail.tvshows.trakt.TraktService;

import retrofit.converter.GsonConverter;
import retrofit.RestAdapter;

/**
 * Factory class to generate new TraktTvApiService instances.
 */
public class TraktTvApiFactory {

    // Constants

    private static final String API_ENDPOINT = "https://api-v2launch.trakt.tv";

    // Public Api

    /**
     * Creates a new preconfigured TraktTvApiService instance.
     */
    public static TraktService newApiService() {

        // Set up rest adapter
        RestAdapter.Builder restAdapterBuilder = new RestAdapter.Builder()
                .setConverter(new GsonConverter(new Gson()))
                .setRequestInterceptor(request -> {
                    request.addHeader("Content-type", "application/json");
                    request.addHeader("trakt-api-version", "2");
                    request.addHeader("trakt-api-key", TraktKeys.CLIENT_ID);
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(API_ENDPOINT);

        // Build raw api service
        return restAdapterBuilder.build().create(TraktService.class);
    }


}
