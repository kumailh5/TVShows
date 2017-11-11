package com.kumail.tvshows.discover;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kumail.tvshows.R;
import com.kumail.tvshows.discover.popular.PopularFragment;
import com.kumail.tvshows.discover.trending.TrendingFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kumail on 11/11/2017.
 */

public class DiscoverFragment extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_discover, container, false);

		// Setting ViewPager for each Tabs
		ViewPager viewPager = view.findViewById(R.id.vp_discover);
		setupViewPager(viewPager);
		// Set Tabs inside Toolbar
		TabLayout tabs = view.findViewById(R.id.shows_tabs);
		tabs.setupWithViewPager(viewPager);

		setHasOptionsMenu(true);
		return view;
	}

	private void setupViewPager(ViewPager viewPager) {
		Adapter adapter = new Adapter(getChildFragmentManager());
		adapter.addFragment(new TrendingFragment(), "Trending");
		adapter.addFragment(new PopularFragment(), "Popular");
		viewPager.setAdapter(adapter);

	}

	static class Adapter extends FragmentPagerAdapter
	{
		private final List<Fragment> mFragmentList = new ArrayList<>();
		private final List<String> mFragmentTitleList = new ArrayList<>();

		public Adapter(FragmentManager manager) {
			super(manager);
		}

		@Override
		public Fragment getItem(int position) {
			return mFragmentList.get(position);
		}

		@Override
		public int getCount() {
			return mFragmentList.size();
		}

		public void addFragment(Fragment fragment, String title) {
			mFragmentList.add(fragment);
			mFragmentTitleList.add(title);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return mFragmentTitleList.get(position);
		}
	}
}
