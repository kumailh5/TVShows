package com.kumail.tvshows.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.kumail.tvshows.db.entity.EpisodesEntity;
import com.kumail.tvshows.db.entity.PopularEntity;
import com.kumail.tvshows.db.entity.ShowEntity;
import com.kumail.tvshows.db.entity.TrendingEntity;
import com.kumail.tvshows.db.entity.UserEntity;
import com.kumail.tvshows.db.entity.WatchedEntity;
import com.kumail.tvshows.trakt.auth.AccessTokenResponse;

import java.util.List;

/**
 * Created by kumail on 29/03/2018.
 */

public class ShowViewModel extends AndroidViewModel {
    public final LiveData<List<ShowEntity>> shows;
    public final LiveData<List<TrendingEntity>> trendingShows;
    public final LiveData<List<PopularEntity>> popularShows;
    public final LiveData<UserEntity> user;
    public final LiveData<AccessTokenResponse> accessToken;
    public final LiveData<List<WatchedEntity>> watchedShows;
    public final LiveData<List<EpisodesEntity>> episodes;

    private AppDatabase appDatabase;

    public ShowViewModel(Application application) {
        super(application);

        appDatabase = AppDatabase
                .getInstance(getApplication());

        trendingShows = appDatabase
                .trendingDao()
                .loadTrendingShows();

        popularShows = appDatabase
                .popularDao()
                .loadPopularShows();

        user = appDatabase
                .userDao()
                .loadUser();

        accessToken = appDatabase
                .accessTokenDao()
                .loadAccessToken();

        watchedShows = appDatabase
                .watchedDao()
                .loadWatchedShows();

        episodes = appDatabase
                .episodesDao()
                .loadEpisodes(0);

        shows = appDatabase
                .showDao()
                .loadShows();
    }

    public LiveData<List<ShowEntity>> getShows() {
        return shows;
    }

    public LiveData<List<TrendingEntity>> getTrendingShows() {
        return trendingShows;
    }

    public LiveData<List<PopularEntity>> getPopularShows() {
        return popularShows;
    }

    public LiveData<UserEntity> getUser() {
        return user;
    }

    public LiveData<AccessTokenResponse> getAccessToken() {
        return accessToken;
    }

    public LiveData<List<WatchedEntity>> getWatchedShows() {
        return watchedShows;
    }

    public LiveData<List<EpisodesEntity>> getEpisodes() {
        return episodes;
    }


}

