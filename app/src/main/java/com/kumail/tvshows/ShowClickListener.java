package com.kumail.tvshows;

import android.widget.ImageView;

import com.kumail.tvshows.tmdb.ShowDetailsResponse;

/**
 * Created by kumail on 16/02/2018.
 */

public interface ShowClickListener
{
	void onShowClick(int pos, ShowDetailsResponse show, ImageView shareImageView);

}
