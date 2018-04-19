package com.kumail.tvshows;

import android.app.Application;

import timber.log.Timber;

public class TimberApp extends Application
{
	@Override public void onCreate() {
		super.onCreate();

		if (BuildConfig.DEBUG) {
			Timber.plant(new Timber.DebugTree());
		}
//		else {
//			Timber.plant(new CrashReportingTree());
//		}
	}
}

