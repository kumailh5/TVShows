package com.kumail.tvshows.discover.trending;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.kumail.tvshows.ApiUtils;
import com.kumail.tvshows.R;
import com.kumail.tvshows.db.AppDatabase;
import com.kumail.tvshows.db.ShowViewModel;
import com.kumail.tvshows.db.entity.CastEntity;
import com.kumail.tvshows.db.entity.InfoEntity;
import com.kumail.tvshows.db.entity.TrendingEntity;
import com.kumail.tvshows.discover.trending.data.TrendingResponse;
import com.kumail.tvshows.tmdb.ImageConfigResponse;
import com.kumail.tvshows.tmdb.ShowDetailsResponse;
import com.kumail.tvshows.tmdb.TmdbService;
import com.kumail.tvshows.tmdb.credits.Cast;
import com.kumail.tvshows.trakt.TraktService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by kumail on 11/11/2017.
 */

public class TrendingFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private TrendingAdapter mAdapter;
    private TraktService mTraktService;
    private TmdbService mTmdbService;
    private AppDatabase appDatabase;
    private ProgressBar progressBar;
    private List<TrendingEntity> trendingShows = new ArrayList<>();
    private ShowViewModel mViewModel;

    public TrendingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTraktService = ApiUtils.getTraktService();
        mTmdbService = ApiUtils.getTmdbService();

        appDatabase = AppDatabase.getInstance(getActivity());
        mViewModel = ViewModelProviders.of(getActivity()).get(ShowViewModel.class);
        isDBEmpty();
        loadTrending();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_trending, container, false);
        mTraktService = ApiUtils.getTraktService();
        mTmdbService = ApiUtils.getTmdbService();

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
        mAdapter = new TrendingAdapter(trendingShows, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        subscribeUiShows();

        return view;
    }

    private void isDBEmpty() {
        if (mViewModel.getTrendingShows() != null) {
            Timber.d("Database isnt empty");
//			mViewModel.getTrendingShows().observe(this, new Observer<List<TrendingEntity>>()
//			{
//				@Override
//				public void onChanged(@NonNull final List<TrendingEntity> trendingShows)
//				{
//					mAdapter.addItems(trendingShows);
//				}
//			});
        } else {
        }
//		mViewModel.getTrendingShows().observe(this, new Observer<List<TrendingEntity>>()
//		{
//			@Override
//			public void onChanged(@NonNull final List<TrendingEntity> trendingShows)
//			{
//				mAdapter.addItems(trendingShows);
//			}
//		});
    }


    public void loadTrending() {
        Call<List<TrendingResponse>> call = mTraktService.getTrending();
        call.enqueue(new Callback<List<TrendingResponse>>() {
            @Override
            public void onResponse(Call<List<TrendingResponse>> call, Response<List<TrendingResponse>> response) {
                if (response.isSuccessful()) {
                    List<TrendingResponse> ltar = response.body();
                    loadShowDetails(ltar);

                } else {
                    progressBar.setVisibility(View.GONE);
                    Timber.d(Integer.toString(response.code()));
//                    Timber.d(new GsonBuilder().setPrettyPrinting().create().toJson(response));
                }
            }

            @Override
            public void onFailure(Call<List<TrendingResponse>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Timber.d(t);
            }
        });

    }

    public void loadShowDetails(final List<TrendingResponse> ltar) {
        for (int i = 0; i < ltar.size(); i++) {
            Call<ShowDetailsResponse> call = mTmdbService.getShowDetails(String.valueOf(ltar.get(i).getShow().getIds().getTmdb()));
//            Timber.d("UrlSD %s", call.request().url().toString());
            call.enqueue(new Callback<ShowDetailsResponse>() {
                @Override
                public void onResponse(Call<ShowDetailsResponse> call, Response<ShowDetailsResponse> response) {
                    if (response.isSuccessful()) {
                        ShowDetailsResponse sdr = response.body();
                        loadImgUrl(sdr);
                    } else {
                        Timber.tag("SI").d(Integer.toString(response.code()));
//                        Timber.tag("SI").d(new GsonBuilder().setPrettyPrinting().create().toJson(response));
                    }
                }

                @Override
                public void onFailure(Call<ShowDetailsResponse> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Timber.tag("FailSI").d(String.valueOf(t));
                }
            });
        }

    }

    public void loadImgUrl(final ShowDetailsResponse sdr) {
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

                    TrendingEntity te = new TrendingEntity(
                            sdr.getTmdbId(),
                            sdr.getName(),
                            backdropUrl,
                            posterUrl,
                            sdr.getFirstAirDate(),
                            sdr.getVoteAverage(),
                            sdr.getOverview());

                    InfoEntity ie = new InfoEntity(
                            sdr.getTmdbId(),
                            sdr.getName(),
                            posterUrl,
                            sdr.getFirstAirDate(),
                            sdr.getVoteAverage(),
                            sdr.getOverview());

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

                    progressBar.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    AsyncTask.execute(() -> appDatabase.trendingDao().insert(te));
                    AsyncTask.execute(() -> appDatabase.infoDao().insert(ie));
                    AsyncTask.execute(() -> appDatabase.castDao().insertAll(lce));

                } else {
                    Timber.tag("IU").d(Integer.toString(response.code()));
//                    Timber.tag("IU").d(new GsonBuilder().setPrettyPrinting().create().toJson(response));
                }

            }

            @Override
            public void onFailure(Call<ImageConfigResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Timber.tag("FailIU").d(String.valueOf(t));
            }
        });
    }

    private void subscribeUiShows() {
        mViewModel.getTrendingShows().observe(this, new Observer<List<TrendingEntity>>() {
            @Override
            public void onChanged(@NonNull final List<TrendingEntity> trendingShows) {
                mAdapter.addItems(trendingShows);
            }
        });

    }

//	public void onShowClick(int pos, ShowDetailsResponse show, ImageView shareImageView){
//		Fragment expShowFrag = ExpShowFrag.newInstance(show, ViewCompat.getTransitionName(shareImageView));
//		getFragmentManager()
//				.beginTransaction()
//				.addSharedElement(shareImageView, ViewCompat.getTransitionName(shareImageView))
//				.addToBackStack(TAG)
//				.replace(R.id.content, expShowFrag)
//				.commit();
//	}

}
