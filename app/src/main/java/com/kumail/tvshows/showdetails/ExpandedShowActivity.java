package com.kumail.tvshows.showdetails;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.kumail.tvshows.BottomNavViewHelper;
import com.kumail.tvshows.NonSwipeableViewPager;
import com.kumail.tvshows.R;
import com.kumail.tvshows.showdetails.cast.CastFragment;
import com.kumail.tvshows.showdetails.episodes.EpisodesFragment;
import com.kumail.tvshows.tmdb.TmdbService;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by kumail on 16/02/2018.
 */

public class ExpandedShowActivity extends AppCompatActivity {
    Toolbar toolbar;
    MenuItem prevMenuItem;
    Adapter adapter;
    NonSwipeableViewPager viewPager;
    BottomNavigationView mBottomBar;
    private TmdbService mTmdbService;


    String showTitle;
    String posterUrl;
    String imgUrl;
    int tmdbId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanded_show);
        setupToolBar();
//		supportPostponeEnterTransition();


        viewPager = findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(1);
        setupViewPager(viewPager);
        mBottomBar = findViewById(R.id.bottom_bar);
        BottomNavViewHelper.disableShiftMode(mBottomBar);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private String[] titles =
                    {
                            getString(R.string.info),
                            getString(R.string.cast),
                            getString(R.string.episodes)

                    };

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    mBottomBar.getMenu().getItem(0).setChecked(false);
                }
                toolbar.setTitle(titles[position]);
                mBottomBar.getMenu().getItem(position).setChecked(true);
                prevMenuItem = mBottomBar.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });
        viewPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }


        });

        mBottomBar.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.nav_info:
                                viewPager.setCurrentItem(0, false);
                                break;
                            case R.id.nav_cast:
                                viewPager.setCurrentItem(1, false);
                                break;
                            case R.id.nav_episodes:
                                viewPager.setCurrentItem(2, false);
                                break;
                        }
                        return false;
                    }
                });


        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        } else {
            showTitle = extras.getString("EXTRA_SHOW_TITLE");
            tmdbId = extras.getInt("EXTRA_SHOW_TMDB_ID");
            posterUrl = extras.getString("EXTRA_POSTER_URL");
//			imgUrl = extras.getString("EXTRA_IMG_URL");
        }

//		loadImgUrl(posterUrl);

    }

    private void setupToolBar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.info);
        toolbar.setTitleTextColor(getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new InfoFragment());
        adapter.addFragment(new CastFragment());
        adapter.addFragment(new EpisodesFragment());
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();

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

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }

    }

    public String getShowTitle() {
        return showTitle;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public int getTmdbId() {
        return tmdbId;
    }

}
