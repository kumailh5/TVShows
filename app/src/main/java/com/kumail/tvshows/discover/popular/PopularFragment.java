package com.kumail.tvshows.discover.popular;

import android.os.Bundle;
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
import com.kumail.tvshows.discover.popular.data.PopularResponse;
import com.kumail.tvshows.discover.trending.data.ShowExtendedResponse;
import com.kumail.tvshows.tmdb.ImageConfigResponse;
import com.kumail.tvshows.tmdb.ShowDetailsResponse;
import com.kumail.tvshows.tmdb.TmdbService;
import com.kumail.tvshows.trakt.TraktService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by kumail on 11/11/2017.
 */

public class PopularFragment extends Fragment
{
	private RecyclerView mRecyclerView;
	private PopularAdapter mAdapter;
	private TraktService mTraktService;
	private TmdbService mTmdbService;
	private ProgressBar progressBar;
	private List<ShowDetailsResponse> popularShows = new ArrayList<>();
	private List<String> imgUrls = new ArrayList<>();
	private Map<ShowExtendedResponse, String> map = new HashMap<>();


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.tab_popular, container, false);
		mTraktService = ApiUtils.getTraktService();
		mTmdbService = ApiUtils.getTmdbService();

		progressBar = view.findViewById(R.id.progress_bar);
		mRecyclerView = view.findViewById(R.id.rv);
		if (mAdapter == null)
		{
			progressBar.setVisibility(View.VISIBLE);
			mRecyclerView.setVisibility(View.GONE);

		} else
		{
			progressBar.setVisibility(View.INVISIBLE);
		}
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setAdapter(mAdapter);

		loadTrending();
		return view;
	}

	public void loadTrending()
	{
		Call<List<PopularResponse>> call = mTraktService.getPopular();
		call.enqueue(new Callback<List<PopularResponse>>()
		{
			@Override
			public void onResponse(Call<List<PopularResponse>> call, Response<List<PopularResponse>> response)
			{
				if (response.isSuccessful())
				{
					List<PopularResponse> lpr = response.body();
					loadShowDetails(lpr);

				} else
				{
					progressBar.setVisibility(View.GONE);
					Timber.d(Integer.toString(response.code()));
//					Log.d("PopularFragment", new GsonBuilder().setPrettyPrinting().create().toJson(response));
				}
			}

			@Override
			public void onFailure(Call<List<PopularResponse>> call, Throwable t)
			{
				progressBar.setVisibility(View.GONE);
				Timber.d(String.valueOf(t));
			}
		});
	}

	public void loadShowDetails(final List<PopularResponse> lpr)
	{
		for (int i = 0; i < lpr.size(); i++)
		{
			Call<ShowDetailsResponse> call = mTmdbService.getShowDetails(String.valueOf(lpr.get(i).getIds().getTmdb()));
//			Log.d("PopularFragmentUrlSD", call.request().url().toString());
			call.enqueue(new Callback<ShowDetailsResponse>()
			{
				@Override
				public void onResponse(Call<ShowDetailsResponse> call, Response<ShowDetailsResponse> response)
				{
					if (response.isSuccessful())
					{
						ShowDetailsResponse sdr = response.body();
						loadImgUrl(sdr);
					} else
					{
                        Timber.d(Integer.toString(response.code()));
//						Log.d("PopularFragmentSD", new GsonBuilder().setPrettyPrinting().create().toJson(response));
					}
				}

				@Override
				public void onFailure(Call<ShowDetailsResponse> call, Throwable t)
				{
					progressBar.setVisibility(View.GONE);
                    Timber.d(String.valueOf(t));
				}
			});
		}

	}

	public void loadImgUrl(ShowDetailsResponse sdrs)
	{
		final ShowDetailsResponse sd = sdrs;

		Call<ImageConfigResponse> call = mTmdbService.getConfig();
//		Log.d("PopularFragmentIC", call.request().url().toString());

		call.enqueue(new Callback<ImageConfigResponse>()
		{
			@Override
			public void onResponse(Call<ImageConfigResponse> call, Response<ImageConfigResponse> response)
			{
				if (response.isSuccessful())
				{
					ImageConfigResponse icr = response.body();
					String imgUrl = icr.getImages().getSecureBaseUrl()
							+ icr.getImages().getBackdropSizes().get(2)
							+ sd.getBackdropPath();
					popularShows.add(sd);
					imgUrls.add(imgUrl);
					mAdapter = new PopularAdapter(popularShows, imgUrls, getActivity());
					mRecyclerView.setAdapter(mAdapter);
					progressBar.setVisibility(View.GONE);
					mRecyclerView.setVisibility(View.VISIBLE);

//                    Timber.d(imgUrl);
				} else
				{
                    Timber.d(Integer.toString(response.code()));
//					Log.d("PopularFragmentIU", new GsonBuilder().setPrettyPrinting().create().toJson(response));
				}

			}

			@Override
			public void onFailure(Call<ImageConfigResponse> call, Throwable t)
			{
                Timber.d(String.valueOf(t));
			}
		});
	}
}
