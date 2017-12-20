package com.kumail.tvshows.discover.trending;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.GsonBuilder;
import com.kumail.tvshows.ApiUtils;
import com.kumail.tvshows.R;
import com.kumail.tvshows.TmdbService;
import com.kumail.tvshows.TraktService;
import com.kumail.tvshows.discover.trending.data.ShowExtendedResponse;
import com.kumail.tvshows.discover.trending.data.TrendingResponse;
import com.kumail.tvshows.tmdb.ImageConfigResponse;
import com.kumail.tvshows.tmdb.ImageUrlGen;
import com.kumail.tvshows.tmdb.ShowImagesResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kumail on 11/11/2017.
 */

public class TrendingFragment extends Fragment
{
	private RecyclerView mRecyclerView;
	private TrendingAdapter mAdapter;
	private TraktService mTraktService;
	private TmdbService mTmdbService;
	private ProgressBar progressBar;
	private List<ShowExtendedResponse> trendingShows = new ArrayList<>();
	private List<String> imgUrls = new ArrayList<>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.tab_trending, container, false);
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
		mAdapter = new TrendingAdapter(new ArrayList<ShowExtendedResponse>(),
				getActivity());
		mRecyclerView.setAdapter(mAdapter);

		loadTrending();
		return view;
	}

	public void loadTrending()
	{
		Call<List<TrendingResponse>> call = mTraktService.getTrending();

		Log.d("TrendingFragmentUrl", call.request().url().toString());

		call.enqueue(new Callback<List<TrendingResponse>>()
		{
			@Override
			public void onResponse(Call<List<TrendingResponse>> call, Response<List<TrendingResponse>> response)
			{
				if (response.isSuccessful())
				{
					List<TrendingResponse> ltar = response.body();
					loadShowDetails(ltar);

				} else
				{
					progressBar.setVisibility(View.GONE);
					Log.d("TrendingFragment", Integer.toString(response.code()));
					Log.d("TrendingFragment", new GsonBuilder().setPrettyPrinting().create().toJson(response));
				}
			}

			@Override
			public void onFailure(Call<List<TrendingResponse>> call, Throwable t)
			{
				progressBar.setVisibility(View.GONE);
				Log.d("TrendingFragmentFailure", String.valueOf(t));
			}
		});

	}

	public void loadShowDetails(final List<TrendingResponse> ltar)
	{
		for (int i = 0; i < ltar.size(); i++)
		{
			Call<ShowExtendedResponse> call = mTraktService.getShowDetails(ltar.get(i).getShow().getIds().getSlug());


			call.enqueue(new Callback<ShowExtendedResponse>()
			{
				@Override
				public void onResponse(Call<ShowExtendedResponse> call, Response<ShowExtendedResponse> response)
				{
					if (response.isSuccessful())
					{
						ShowExtendedResponse ser = response.body();
						trendingShows.add(ser);
						loadShowImages(ser);

						if (trendingShows.size() == ltar.size())
						{
							mAdapter.updateTrendingShows(trendingShows);
						}

						progressBar.setVisibility(View.GONE);
						mRecyclerView.setVisibility(View.VISIBLE);
					} else
					{
						Log.d("TrendingFragmentSD", Integer.toString(response.code()));
						Log.d("TrendingFragmentSD", new GsonBuilder().setPrettyPrinting().create().toJson(response));
					}
				}

				@Override
				public void onFailure(Call<ShowExtendedResponse> call, Throwable t)
				{
					progressBar.setVisibility(View.GONE);
					Log.d("TrendingFragmentFailSD", String.valueOf(t));
				}
			});
		}
	}

	public void loadShowImages(final ShowExtendedResponse ser)
	{
		Call<ShowImagesResponse> call = mTmdbService.getImages(String.valueOf(ser.getIds().getTmdb()));
		Log.d("TrendingFragmentUrlSI", call.request().url().toString());

		call.enqueue(new Callback<ShowImagesResponse>()
		{
			@Override
			public void onResponse(Call<ShowImagesResponse> call, Response<ShowImagesResponse> response)
			{
				if (response.isSuccessful())
				{
					ShowImagesResponse sir = response.body();
					loadImageUrl(sir);
					Log.d("TrendingFragmentSI", sir.getBackdrops().get(0).getFilePath());
				} else
				{
					Log.d("TrendingFragmentSI", Integer.toString(response.code()));
					Log.d("TrendingFragmentSI", new GsonBuilder().setPrettyPrinting().create().toJson(response));
				}

			}

			@Override
			public void onFailure(Call<ShowImagesResponse> call, Throwable t)
			{
				progressBar.setVisibility(View.GONE);
				Log.d("TrendingFragmentFailSI", String.valueOf(t));
			}
		});
	}

	public void loadImageUrl(final ShowImagesResponse sir)
	{
		Call<ImageConfigResponse> call = mTmdbService.getConfig();
		call.enqueue(new Callback<ImageConfigResponse>()
		{
			@Override
			public void onResponse(Call<ImageConfigResponse> call, Response<ImageConfigResponse> response)
			{
				if (response.isSuccessful())
				{
					String imgUrl;
					ImageConfigResponse icr = response.body();
					ImageUrlGen iug = new ImageUrlGen();
					imgUrl = iug.getUrl(sir, icr);
//					mAdapter.updateImageUrl(url);
//					mAdapter.updateTrendingShows(trendingShows);
					imgUrls.add(imgUrl);
//					for(int i =0; i< imgUrls.size(); i++){
//						Log.d("TrendingAdapterls", imgUrls.get(i));
//					}
					if (imgUrls.size() == trendingShows.size())
					{
						mAdapter.updateImageUrl(imgUrls);
					}

					Log.d("TrendingFragmentIU", imgUrl);
//					Log.d("TrendingFragmentIU", new GsonBuilder().setPrettyPrinting().create().toJson(response));
				} else
				{
					Log.d("TrendingFragmentIU", Integer.toString(response.code()));
					Log.d("TrendingFragmentIU", new GsonBuilder().setPrettyPrinting().create().toJson(response));
				}

			}

			@Override
			public void onFailure(Call<ImageConfigResponse> call, Throwable t)
			{
				progressBar.setVisibility(View.GONE);
				Log.d("TrendingFragmentFailIU", String.valueOf(t));
			}
		});
	}


}
