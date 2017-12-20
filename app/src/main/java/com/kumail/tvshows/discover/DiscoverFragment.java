package com.kumail.tvshows.discover;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.GsonBuilder;
import com.kumail.tvshows.ApiUtils;
import com.kumail.tvshows.R;
import com.kumail.tvshows.SearchResponse;
import com.kumail.tvshows.TraktService;
import com.kumail.tvshows.discover.popular.PopularFragment;
import com.kumail.tvshows.discover.trending.TrendingFragment;

import java.util.ArrayList;
import java.util.List;

import br.com.mauker.materialsearchview.MaterialSearchView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kumail on 11/11/2017.
 */

public class DiscoverFragment extends Fragment
{
	MaterialSearchView searchView;
	private TraktService mTraktService;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_discover, container, false);
		mTraktService = ApiUtils.getTraktService();

		// Setting ViewPager for each Tabs
		ViewPager viewPager = view.findViewById(R.id.vp_discover);
		setupViewPager(viewPager);
		// Set Tabs inside Toolbar
		TabLayout tabs = view.findViewById(R.id.shows_tabs);
		tabs.setupWithViewPager(viewPager);

		searchView = getActivity().findViewById(R.id.search_view);
		searchView.clearHistory();
		searchView.clearAll();
		searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener()
		{
			@Override
			public boolean onQueryTextSubmit(String query)
			{
				showResults(query);
				searchView.clearFocus();
				return true;
			}

			@Override
			public boolean onQueryTextChange(String newText)
			{
				Log.d("DiscoverQuery", newText);
				showResults(newText);
				return true;
			}
		});

		setHasOptionsMenu(true);
		return view;
	}

	private void setupViewPager(ViewPager viewPager)
	{
		Adapter adapter = new Adapter(getChildFragmentManager());
		adapter.addFragment(new TrendingFragment(), "Trending");
		adapter.addFragment(new PopularFragment(), "Popular");
		viewPager.setAdapter(adapter);
	}

	static class Adapter extends FragmentPagerAdapter
	{
		private final List<Fragment> mFragmentList = new ArrayList<>();
		private final List<String> mFragmentTitleList = new ArrayList<>();

		public Adapter(FragmentManager manager)
		{
			super(manager);
		}

		@Override
		public Fragment getItem(int position)
		{
			return mFragmentList.get(position);
		}

		@Override
		public int getCount()
		{
			return mFragmentList.size();
		}

		public void addFragment(Fragment fragment, String title)
		{
			mFragmentList.add(fragment);
			mFragmentTitleList.add(title);
		}

		@Override
		public CharSequence getPageTitle(int position)
		{
			return mFragmentTitleList.get(position);
		}
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu)
	{
		super.onPrepareOptionsMenu(menu);
		menu.clear();
		getActivity().getMenuInflater().inflate(R.menu.menu_tb_discover, menu);
//		MenuItem search = menu.findItem(R.id.action_search);
//		SearchView searchView = (SearchView) search.getActionView();
//		search(searchView);
		// Get the SearchView and set the searchable configuration
//		SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
//		SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
//		// Assumes current activity is the searchable activity
//		searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
//		searchView.setMaxWidth(Integer.MAX_VALUE);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{

		switch (item.getItemId())
		{
			case R.id.action_search:
				searchView.openSearch();
				return true;

			case R.id.action_filter:

				return true;
		}

		return super.onOptionsItemSelected(item);
	}


	private void search(SearchView searchView)
	{

		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
		{
			@Override
			public boolean onQueryTextSubmit(String query)
			{

				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText)
			{


				return true;
			}
		});
	}

	private void showResults(final String query)
	{
		Call<List<SearchResponse>> call = mTraktService.getSearchResults(query);
		Log.d("DiscoverFragmentUrl", call.request().url().toString());

		call.enqueue(new Callback<List<SearchResponse>>()
		{
			@Override
			public void onResponse(retrofit2.Call<List<SearchResponse>> call, Response<List<SearchResponse>> response)
			{
				if (response.isSuccessful())
				{
					final List<SearchResponse> lsr = response.body();
				  	Log.d("DiscoverFragment", new GsonBuilder().setPrettyPrinting().create().toJson(lsr));
					if (lsr != null)
					{
						for (int i = 0; i < lsr.size(); i++)
						{
							searchView.addSuggestion(lsr.get(i).getShow().getTitle(),
									String.valueOf(lsr.get(i).getShow().getYear()));

						}
					} else {
						Log.d("DiscoverFragment", "search resp null");
					}

				} else
				{
//					progressBar.setVisibility(View.GONE);
					Log.d("DiscoverFragment", Integer.toString(response.code()));
					Log.d("DiscoverFragment", new GsonBuilder().setPrettyPrinting().create().toJson(response));
				}
			}

			@Override
			public void onFailure(retrofit2.Call<List<SearchResponse>> call, Throwable t)
			{
//				progressBar.setVisibility(View.GONE);
				Log.d("DiscoverFragmentFailure", String.valueOf(t));
			}
		});
	}

}
