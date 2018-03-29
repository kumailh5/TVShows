package com.kumail.tvshows;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by kumail on 25/03/2018.
 */



@Entity
public class Repo {
	@PrimaryKey
	public final int id;
	public final String name;
	public final String url;

	public Repo(int id, String name, String url) {
		this.id = id;
		this.name = name;
		this.url = url;
	}
}
