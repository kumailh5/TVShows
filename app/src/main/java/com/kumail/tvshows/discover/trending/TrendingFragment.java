package com.kumail.tvshows.discover.trending;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.GsonBuilder;
import com.kumail.tvshows.ApiUtils;
import com.kumail.tvshows.R;
import com.kumail.tvshows.TraktService;
import com.kumail.tvshows.discover.trending.data.ShowExtendedResponse;
import com.kumail.tvshows.discover.trending.data.TrendingResponse;

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
	private TraktService mService;
	private ProgressBar progressBar;
	private List<ShowExtendedResponse> trendingShows = new ArrayList<>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.tab_trending, container, false);
		mService = ApiUtils.getTraktService();

		progressBar = view.findViewById(R.id.progress_bar);
		progressBar.setVisibility(View.VISIBLE);
		mRecyclerView = view.findViewById(R.id.rv);
		mRecyclerView.setVisibility(View.GONE);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		mRecyclerView.setHasFixedSize(true);
		mAdapter = new TrendingAdapter(new ArrayList<ShowExtendedResponse>(),
				R.layout.item_trending_card);
		mRecyclerView.setAdapter(mAdapter);

		setHasOptionsMenu(true);
		loadTrending();
		return view;
	}

	public void loadTrending()
	{
		Call<List<TrendingResponse>> call = mService.getTrending();

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
				progressBar.setVisibility(View.VISIBLE);
				Log.d("TrendingFragmentFailure", String.valueOf(t));
			}
		});

	}

	public void loadShowDetails(final List<TrendingResponse> ltar)
	{
		for (int i = 0; i < ltar.size(); i++)
		{
			Call<ShowExtendedResponse> call = mService.getShowDetails(ltar.get(i).getShow().getIds().getSlug());

			call.enqueue(new Callback<ShowExtendedResponse>()
			{
				@Override
				public void onResponse(Call<ShowExtendedResponse> call, Response<ShowExtendedResponse> response)
				{
					if (response.isSuccessful())
					{
						ShowExtendedResponse ser = response.body();
						trendingShows.add(ser);
						if (trendingShows.size() == ltar.size())
						{
							mAdapter.updateTrendingShows(trendingShows);
							progressBar.setVisibility(View.GONE);
							mRecyclerView.setVisibility(View.VISIBLE);
						}
					} else
					{
						Log.d("TrendingFragmentSD", Integer.toString(response.code()));
						Log.d("TrendingFragmentSD", new GsonBuilder().setPrettyPrinting().create().toJson(response));
					}

				}

				@Override
				public void onFailure(Call<ShowExtendedResponse> call, Throwable t)
				{
					Log.d("TrendingFragmentFailSD", String.valueOf(t));
				}
			});
		}

	}
}
