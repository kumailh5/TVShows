package com.kumail.tvshows.discover.popular;

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
import com.kumail.tvshows.db.entity.PopularEntity;
import com.kumail.tvshows.discover.popular.data.PopularResponse;
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

public class PopularFragment extends Fragment {
	private RecyclerView mRecyclerView;
	private PopularAdapter mAdapter;
	private TraktService mTraktService;
	private TmdbService mTmdbService;
	private AppDatabase appDatabase;
	private ProgressBar progressBar;
	private List<PopularEntity> popularShows = new ArrayList<>();
	private ShowViewModel mViewModel;

	public PopularFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mTraktService = ApiUtils.getTraktService();
		mTmdbService = ApiUtils.getTmdbService();

		appDatabase = AppDatabase.getInstance(getActivity());
		mViewModel = ViewModelProviders.of(getActivity()).get(ShowViewModel.class);
		isDBEmpty();
		loadPopular();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_popular, container, false);
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
		mAdapter = new PopularAdapter(popularShows, getActivity());
		mRecyclerView.setAdapter(mAdapter);

		subscribeUiShows();

		return view;
	}

	private void isDBEmpty() {
		if (mViewModel.getPopularShows() != null) {
			Timber.d("Database isnt empty");
		}
	}


	public void loadPopular() {
		Call<List<PopularResponse>> call = mTraktService.getPopular();
		call.enqueue(new Callback<List<PopularResponse>>() {
			@Override
			public void onResponse(Call<List<PopularResponse>> call, Response<List<PopularResponse>> response) {
				if (response.isSuccessful()) {
					List<PopularResponse> lpar = response.body();
					loadShowDetails(lpar);

				} else {
					progressBar.setVisibility(View.GONE);
					Timber.d(Integer.toString(response.code()));
//                    Timber.d(new GsonBuilder().setPrettyPrinting().create().toJson(response));
				}
			}

			@Override
			public void onFailure(Call<List<PopularResponse>> call, Throwable t) {
				progressBar.setVisibility(View.GONE);
				Timber.d(t);
			}
		});

	}

	public void loadShowDetails(final List<PopularResponse> lpar) {
		for (int i = 0; i < lpar.size(); i++) {
			Call<ShowDetailsResponse> call = mTmdbService.getShowDetails(String.valueOf(lpar.get(i).getIds().getTmdb()));
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

					PopularEntity pe = new PopularEntity(
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
					AsyncTask.execute(() -> appDatabase.popularDao().insert(pe));
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
		mViewModel.getPopularShows().observe(this, new Observer<List<PopularEntity>>() {
			@Override
			public void onChanged(@NonNull final List<PopularEntity> popularShows) {
				mAdapter.addItems(popularShows);
			}
		});

	}

}
