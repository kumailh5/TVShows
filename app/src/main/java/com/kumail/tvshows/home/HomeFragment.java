package com.kumail.tvshows.home;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.kumail.tvshows.ApiUtils;
import com.kumail.tvshows.R;
import com.kumail.tvshows.db.AppDatabase;
import com.kumail.tvshows.db.ShowViewModel;
import com.kumail.tvshows.db.entity.CastEntity;
import com.kumail.tvshows.db.entity.EpisodesEntity;
import com.kumail.tvshows.db.entity.InfoEntity;
import com.kumail.tvshows.db.entity.ShowEntity;
import com.kumail.tvshows.db.entity.UserEntity;
import com.kumail.tvshows.db.entity.WatchedEntity;
import com.kumail.tvshows.discover.trending.data.TrendingResponse;
import com.kumail.tvshows.tmdb.ImageConfigResponse;
import com.kumail.tvshows.tmdb.ShowDetailsResponse;
import com.kumail.tvshows.tmdb.TmdbService;
import com.kumail.tvshows.tmdb.credits.Cast;
import com.kumail.tvshows.trakt.TraktService;
import com.kumail.tvshows.trakt.auth.AccessTokenResponse;
import com.kumail.tvshows.trakt.auth.ConnectTraktActivity;
import com.kumail.tvshows.trakt.data.Episode;
import com.kumail.tvshows.trakt.data.Season;
import com.kumail.tvshows.trakt.data.Show;
import com.kumail.tvshows.trakt.data.WatchedDetailedResponse;
import com.kumail.tvshows.trakt.data.user.WatchedResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.Entity;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.MediaType;


/**
 * Created by kumail on 11/11/2017.
 */

public class HomeFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private HomeAdapter mAdapter;
    private List<ShowEntity> shows = new ArrayList<>();
    private List<WatchedEntity> watchedShows = new ArrayList<>();
    private ShowViewModel mViewModel;
    private AppDatabase appDatabase;
    private TraktService mTraktService;
    private TraktService mTraktServiceOAuth;
    private TmdbService mTmdbService;
    private ProgressBar progressBar;

//    private UserEntity user;

    ArrayList<Show> list = new ArrayList<>();

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTraktService = ApiUtils.getTraktService();
        mTmdbService = ApiUtils.getTmdbService();

        appDatabase = AppDatabase.getInstance(getActivity());
//        AsyncTask.execute(() -> user = appDatabase.userDao().loadUser());


//        if(user != null) {
//
//            Timber.d("onCreate Not null");
//            loadWatched();
//        }

//        loadTrending();
        mViewModel = ViewModelProviders.of(getActivity()).get(ShowViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Show s = new Show();
        list.add(s);

        progressBar = view.findViewById(R.id.progress_bar);
        mRecyclerView = view.findViewById(R.id.rv);
        if (mAdapter == null) {
            progressBar.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);

        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new HomeAdapter(watchedShows, getContext());
        mRecyclerView.setAdapter(mAdapter);
        setTraktServiceOAuth();
        subscribeUiUser();
        subscribeUiShows();

        setHasOptionsMenu(true);
        return view;
    }

    private void setTraktServiceOAuth() {
        mViewModel.getAccessToken().observe(this, new Observer<AccessTokenResponse>() {
            @Override
            public void onChanged(@NonNull final AccessTokenResponse accessToken) {

                if (accessToken != null) {
                    Timber.d("trakt Not null");
                    mTraktServiceOAuth = ApiUtils.getOAuthTraktService(accessToken.getAccessToken());

                }

            }
        });

    }

    private void subscribeUiUser() {
        mViewModel.getUser().observe(this, new Observer<UserEntity>() {
            @Override
            public void onChanged(@NonNull final UserEntity user) {
                if (user != null) {
                    Timber.d("subscribe Not null");
                    loadWatched(user);
                }


            }
        });
    }

    private void loadWatched(UserEntity user) {
        Call<List<WatchedResponse>> call = mTraktService.getWatched(user.getUsername());
        call.enqueue(new Callback<List<WatchedResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<WatchedResponse>> call, Response<List<WatchedResponse>> response) {
                if (response.isSuccessful()) {
                    List<WatchedResponse> lwr = response.body();
                    loadWatchedDetailed(lwr);
//                    loadShowDetails(lwr);
//                    Timber.d(new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                } else {
                    Timber.tag("W").d(Integer.toString(response.code()));
//                    Timber.tag("W").d(new GsonBuilder().setPrettyPrinting().create().toJson(response));
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<WatchedResponse>> call, Throwable t) {

            }
        });
    }

    private void loadWatchedDetailed(final List<WatchedResponse> lwr) {
        for (int i = 0; i < lwr.size(); i++) {
            WatchedResponse wr = lwr.get(i);
//            Timber.d(lwr.get(i).getShow().getIds().getSlug());
            Call<WatchedDetailedResponse> call = mTraktServiceOAuth.getWatchedDetailed(wr.getShow().getIds().getSlug());
//            Timber.d(call.request().url().toString());

            call.enqueue(new Callback<WatchedDetailedResponse>() {
                @Override
                public void onResponse(@NonNull Call<WatchedDetailedResponse> call, Response<WatchedDetailedResponse> response) {
//                    Timber.d(String.valueOf(response.code()));
//                    Timber.d(new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));

                    if (response.isSuccessful()) {
//                        Timber.tag("WD").d(new GsonBuilder().setPrettyPrinting().create().toJson(response));

                        WatchedDetailedResponse wdr = response.body();
                        loadShowDetails(wr, wdr);
                    } else {
                        Timber.tag("WD").d(Integer.toString(response.code()));
//                        Timber.tag("WD").d(new GsonBuilder().setPrettyPrinting().create().toJson(response));
                    }

                }

                @Override
                public void onFailure(Call<WatchedDetailedResponse> call, Throwable t) {

                }
            });


        }

    }

    public void loadShowDetails(final WatchedResponse wr, final WatchedDetailedResponse wdr) {
        Call<ShowDetailsResponse> call = mTmdbService.getShowDetails(String.valueOf(wr.getShow().getIds().getTmdb()));
//            Timber.d("UrlSD " + call.request().url().toString());
        call.enqueue(new Callback<ShowDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<ShowDetailsResponse> call, Response<ShowDetailsResponse> response) {
                if (response.isSuccessful()) {
                    ShowDetailsResponse sdr = response.body();
                    loadImgUrl(sdr, wdr, wr);
                } else {
                    Timber.tag("SD").d(Integer.toString(response.code()));
//                    Timber.tag("SD").d(new GsonBuilder().setPrettyPrinting().create().toJson(response));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ShowDetailsResponse> call, Throwable t) {
//                    progressBar.setVisibility(View.GONE);
                Timber.d(String.valueOf(t));
            }
        });

    }

    public void loadImgUrl(final ShowDetailsResponse sdr, final WatchedDetailedResponse wdr, WatchedResponse wr) {
        Call<ImageConfigResponse> call = mTmdbService.getConfig();
//		Log.d("TrendingFragmentIC", call.request().url().toString());
        call.enqueue(new Callback<ImageConfigResponse>() {
            @Override
            public void onResponse(Call<ImageConfigResponse> call, Response<ImageConfigResponse> response) {
                if (response.isSuccessful()) {
                    ImageConfigResponse icr = response.body();
                    String backdropUrl = icr.getImages().getSecureBaseUrl()
                            + icr.getImages().getBackdropSizes().get(2)
                            + sdr.getBackdropPath();

                    String posterUrl = icr.getImages().getSecureBaseUrl()
                            + icr.getImages().getPosterSizes().get(5)
                            + sdr.getPosterPath();

                    WatchedEntity we = new WatchedEntity(
                            wr.getShow().getIds().getTrakt(),
                            wr.getShow().getIds().getTmdb(),
                            wr.getPlays(),
                            wdr.getAired(),
                            wr.getShow().getTitle(),
                            backdropUrl);

                    AsyncTask.execute(() -> appDatabase.watchedDao().insert(we));

                    InfoEntity ie = new InfoEntity(
                            sdr.getTmdbId(),
                            sdr.getName(),
                            posterUrl,
                            sdr.getFirstAirDate(),
                            sdr.getVoteAverage(),
                            sdr.getOverview());

                    AsyncTask.execute(() -> appDatabase.infoDao().insert(ie));

                    List<CastEntity> lce = new ArrayList<>();

                    for (int i = 0; i < sdr.getCredits().getCast().size(); i++) {
                        Cast c = sdr.getCredits().getCast().get(i);
                        String profileUrl = icr.getImages().getSecureBaseUrl()
                                + icr.getImages().getProfileSizes().get(2)
                                + c.getProfilePath();
                        CastEntity ce = new CastEntity(
                                c.getId(),
                                c.getCharacter(),
                                c.getName(),
                                c.getOrder(),
                                profileUrl,
                                sdr.getTmdbId());
                        lce.add(ce);
                    }

                    AsyncTask.execute(() -> appDatabase.castDao().insertAll(lce));

                    List<Season> ls = wdr.getSeasons();
                    loadEpisodes(wr, ls);

                    progressBar.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    Timber.tag("IU").d(Integer.toString(response.code()));
//                    Timber.tag("IU").d(new GsonBuilder().setPrettyPrinting().create().toJson(response));
                }
            }

            @Override
            public void onFailure(Call<ImageConfigResponse> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
                Timber.tag("FailIU").d(String.valueOf(t));
            }
        });
    }

    @SuppressLint({"DefaultLocale", "StringFormatInTimber"})
    private void loadEpisodes(WatchedResponse wr, List<Season> ls) {
        List<EpisodesEntity> lee = new ArrayList<>();
        for (int i = 0; i < ls.size(); i++) {
            EpisodesEntity se = new EpisodesEntity(
                    String.format("%d%d", wr.getShow().getIds().getTmdb(), i),
                    wr.getShow().getIds().getTmdb(),
                    wr.getShow().getIds().getSlug(),
                    ls.get(i).getNumber(),
                    ls.get(i).getNumber(),
                    0,
                    false,
                    ls.get(i).getAired(),
                    ls.get(i).getCompleted(),
                    null,
                    null);

            lee.add(se);
            List<Episode> le = ls.get(i).getEpisodes();
            for (int j = 0; j < le.size(); j++) {
                EpisodesEntity ee = new EpisodesEntity(
                        String.format("%d%d%d", wr.getShow().getIds().getTmdb(), i, j),
                        wr.getShow().getIds().getTmdb(),
                        wr.getShow().getIds().getSlug(),
                        ls.get(i).getNumber(),
                        le.get(j).getNumber(),
                        1,
                        le.get(j).getCompleted(),
                        0,
                        0,
                        null,
                        null);
                lee.add(ee);
            }
        }

        AsyncTask.execute(() -> appDatabase.episodesDao().insertAll(lee));
    }

    public void loadShowDetail(final List<TrendingResponse> ltar) {
        for (int i = 0; i < ltar.size(); i++) {
            Call<ShowDetailsResponse> call = mTmdbService.getShowDetails(String.valueOf(ltar.get(i).getShow().getIds().getTmdb()));
//            Timber.d(String.format("UrlSD, %s", call.request().url().toString()));
            call.enqueue(new Callback<ShowDetailsResponse>() {
                @Override
                public void onResponse(Call<ShowDetailsResponse> call, Response<ShowDetailsResponse> response) {
                    if (response.isSuccessful()) {
                        ShowDetailsResponse sdr = response.body();
                        ShowEntity se = new ShowEntity(sdr.getTmdbId(), sdr.getName(), sdr.getBackdropPath(), sdr.getVoteAverage());
                        AsyncTask.execute(() -> appDatabase.showDao().insert(se));
//						populateDb(sdr);

                    } else {
                        Timber.d("SD, %s", Integer.toString(response.code()));
//                        Timber.d("SD, %s", new GsonBuilder().setPrettyPrinting().create().toJson(response));
                    }
                }

                @Override
                public void onFailure(Call<ShowDetailsResponse> call, Throwable t) {
                    Timber.d(t);
                }
            });
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_toolbar_home, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) search.getActionView();
        search(searchView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_sign_in:
                Intent i = new Intent(getContext(), ConnectTraktActivity.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }

    private void subscribeUiShows() {
        mViewModel.getWatchedShows().observe(this, new Observer<List<WatchedEntity>>() {
            @Override
            public void onChanged(@NonNull final List<WatchedEntity> watchedShows) {
                if (watchedShows != null) {
                    Timber.d("Watched shows isnt null");
                    mAdapter.addItems(watchedShows);
                }
            }
        });
    }
}
