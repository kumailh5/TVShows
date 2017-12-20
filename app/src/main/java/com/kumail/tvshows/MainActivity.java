package com.kumail.tvshows;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.kumail.tvshows.discover.DiscoverFragment;
import com.kumail.tvshows.home.HomeFragment;
import com.kumail.tvshows.profile.ProfileFragment;
import java.util.ArrayList;
import java.util.List;

import br.com.mauker.materialsearchview.MaterialSearchView;

public class MainActivity extends AppCompatActivity
{

	MenuItem prevMenuItem;
	Toolbar toolbar;
	Adapter adapter;
	NonSwipeableViewPager viewPager;
	BottomNavigationView mBottomBar;
	MaterialSearchView searchView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setupToolBar();

		viewPager = findViewById(R.id.viewpager);
		viewPager.setOffscreenPageLimit(1);
		setupViewPager(viewPager);
		mBottomBar = findViewById(R.id.bottomBar);
		BottomNavigationViewHelper.disableShiftMode(mBottomBar);
		searchView = findViewById(R.id.search_view);
//		searchView.adjustTintAlpha(0.9f);


		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
		{
			private String[] titles =
					{
							getString(R.string.home),
							getString(R.string.discover),
							getString(R.string.profile)
					};

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
			{
			}

			@Override
			public void onPageSelected(int position)
			{
				if (prevMenuItem != null)
				{
					prevMenuItem.setChecked(false);
				} else
				{
					mBottomBar.getMenu().getItem(0).setChecked(false);
				}
				toolbar.setTitle(titles[position]);
				mBottomBar.getMenu().getItem(position).setChecked(true);
				prevMenuItem = mBottomBar.getMenu().getItem(position);
			}

			@Override
			public void onPageScrollStateChanged(int state)
			{
			}

		});
		viewPager.setOnTouchListener(new View.OnTouchListener()
		{

			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				return true;
			}


		});

		mBottomBar.setOnNavigationItemSelectedListener(
				new BottomNavigationView.OnNavigationItemSelectedListener()
				{
					@Override
					public boolean onNavigationItemSelected(@NonNull MenuItem item)
					{
						switch (item.getItemId())
						{
							case R.id.nav_home:
								viewPager.setCurrentItem(0, false);
								break;
							case R.id.nav_discover:
								viewPager.setCurrentItem(1, false);
								break;
							case R.id.nav_profile:
								viewPager.setCurrentItem(2, false);
								break;
						}
						if (searchView.isOpen()) {
							// Close the search on the back button press.
							searchView.closeSearch();
						}
						return false;
					}
				});

	}

	private void setupViewPager(ViewPager viewPager)
	{
		adapter = new Adapter(getFragmentManager());
		adapter.addFragment(new HomeFragment());
		adapter.addFragment(new DiscoverFragment());
		adapter.addFragment(new ProfileFragment());
		viewPager.setAdapter(adapter);
	}

	static class Adapter extends FragmentPagerAdapter
	{
		private final List<Fragment> mFragmentList = new ArrayList<>();

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

		public void addFragment(Fragment fragment)
		{
			mFragmentList.add(fragment);
		}

	}

	private void setupToolBar()
	{
		toolbar = findViewById(R.id.toolbar);
		toolbar.setTitle(R.string.home);
		toolbar.setTitleTextColor(getColor(R.color.colorWhite));
		setSupportActionBar(toolbar);
	}

	@Override
	public void onBackPressed() {
		if (searchView.isOpen()) {
			// Close the search on the back button press.
			searchView.closeSearch();
		} else {
			super.onBackPressed();
		}
	}
}
