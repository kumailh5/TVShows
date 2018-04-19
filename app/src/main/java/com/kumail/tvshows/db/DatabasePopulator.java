package com.kumail.tvshows.db;

import com.kumail.tvshows.tmdb.ShowDetailsResponse;

/**
 * Created by kumail on 30/03/2018.
 */

public class DatabasePopulator
{
	public static ShowDetailsResponse addShow(final AppDatabase db, ShowDetailsResponse sdr){
		ShowDetailsResponse show = new ShowDetailsResponse();
		show = sdr;
//		db.showDao().insert(sdr);
		return show;
	}
}
